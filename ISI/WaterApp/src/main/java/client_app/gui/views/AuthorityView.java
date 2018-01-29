package client_app.gui.views;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import com.esri.toolkit.overlays.*;
import com.esri.toolkit.overlays.DrawingOverlay.DrawingMode;
import com.esri.core.geometry.*;
import com.esri.core.geometry.Point;
import com.esri.core.map.Feature;
import com.esri.core.map.Graphic;
import com.esri.core.portal.Portal;
import com.esri.core.portal.WebMap;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.map.*;
import com.esri.map.MapOptions.MapType;

import client_app.appl.dao.IncidentDao;
import client_app.appl.dao.PictureDao;
import client_app.appl.dao.WaterSensorDao;
import client_app.appl.domain.builders.WaterSensorBuilder;
import client_app.appl.domain.entities.Incident;
import client_app.appl.domain.entities.Picture;
import client_app.appl.domain.entities.User;
import client_app.appl.domain.entities.WaterSensor;
import client_app.picture.Icon;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class AuthorityView extends View {
	private static final long serialVersionUID = 1L;
	private static AuthorityView instance;

	//private static final Portal portal = new Portal("https://www.arcgis.com", null);
	//private static final String itemID = "064327e68cc34e1792b7c7ea8b551c3c";

	private State state;
	private boolean createGraphicsForWaterSensors = false;
	private boolean createGraphicsForIncidents = false;

	private JMap map;
	private Envelope romaniaEnvelope;
	private GraphicsLayer waterSensorsLayer;
	private GraphicsLayer incidentsLayer;
	private DrawingOverlay drawingOverlay;
	private HitTestOverlay hitTestWaterSensorsOverlay;
	private HitTestOverlay hitTestIncidentsOverlay;
	private SpatialReference wgs84 = SpatialReference.create(4326);

	private String ADD_SENSOR_BUTTON_TEXT = "Add sensor";
	private String SHOW_SENSORS_BUTTON_TEXT = "Show sensors";
	private String HIDE_SENSORS_BUTTON_TEXT = "Hide sensors";
	private String DELETE_SENSOR_BUTTON_TEXT = "Delete sensor";
	private String SHOW_INCIDENTS_BUTTON_TEXT = "Show incidents";
	private String HIDE_INCIDENTS_BUTTON_TEXT = "Hide incidents";
	private PictureMarkerSymbol greenSensorSymbol;
	private PictureMarkerSymbol redSensorSymbol;
	private PictureMarkerSymbol greenIncidentSymbol;

	private WaterSensorDao waterSensorDao;
	private List<WaterSensor> waterSensors;
	private Map<Integer, WaterSensor> waterSensorsGraphics;

	private IncidentDao incidentDao;
	private List<Incident> incidents;
	private Map<Integer, Incident> incidentsGraphics;

	private User activeAuthority;

	private PictureDao pictureDao;
	private JLabel authorityPictureLabel;

	private AuthorityView() {
		viewType = ViewType.AUTHORITY_VIEW;
		width = Integer.MAX_VALUE;
		height = Integer.MAX_VALUE;

		setLayout(new BorderLayout());

		greenSensorSymbol = new PictureMarkerSymbol(
			ImageLoader.getInstance().getImageFromDB(Icon.GREEN_SENSOR_ICON.getIconName())
		);

		redSensorSymbol = new PictureMarkerSymbol(
			ImageLoader.getInstance().getImageFromDB(Icon.RED_SENSOR_ICON.getIconName())
		);

		greenIncidentSymbol = new PictureMarkerSymbol(
			ImageLoader.getInstance().getImageFromDB(Icon.GREEN_INCIDENT_ICON.getIconName())
		);

		waterSensorDao = WaterSensorDao.getInstance();
		incidentDao = IncidentDao.getInstance();

		MapOptions mapOptions = new MapOptions(MapType.TOPO);
		map = new JMap(mapOptions);

	    /*WebMap webMap = null;
	    try {
	      webMap = WebMap.newInstance(itemID, portal);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	    map.loadWebMap(webMap);*/

		romaniaEnvelope = new Envelope(1999959.670494509, 5390793.841261995, 3573725.6232362194, 6155722.667043943);
		map.setExtent(romaniaEnvelope);

		waterSensorsLayer = new GraphicsLayer();
		map.getLayers().add(waterSensorsLayer);

		incidentsLayer = new GraphicsLayer();
		map.getLayers().add(incidentsLayer);

		drawingOverlay = new DrawingOverlay();
		map.addMapOverlay(drawingOverlay);

		hitTestWaterSensorsOverlay = new HitTestOverlay(waterSensorsLayer);
		hitTestWaterSensorsOverlay.addHitTestListener(new GraphicSelectedListener(waterSensorsLayer));
		map.addMapOverlay(hitTestWaterSensorsOverlay);

		hitTestIncidentsOverlay = new HitTestOverlay(incidentsLayer);
		hitTestIncidentsOverlay.addHitTestListener(new GraphicSelectedListener(incidentsLayer));
		map.addMapOverlay(hitTestIncidentsOverlay);

		addMapEventListener();

		pictureDao = PictureDao.getInstance();
		authorityPictureLabel = new JLabel();

		JLayeredPane contentPane = new JLayeredPane();
		contentPane.setLayout(new BorderLayout());
	    contentPane.setVisible(true);
	    contentPane.add(map);
	    contentPane.add(createToolBar(), BorderLayout.NORTH);

	    add(contentPane);
	}

	public static AuthorityView getInstance() {
		if (instance == null) {
			instance = new AuthorityView();
		}
		
		return instance;
	}

	/**
	 * Adauga senzor pe harta cunoscand deja coordonatele sale in spatial reference,
	 * dupa care obtine coordonatele geografice (latitudine, longitudine) ale punctului adaugat,
	 * pentru a putea fi salvat in baza de date.
	 */
	private int addWaterSensorBySpatialRef(Geometry point, PictureMarkerSymbol symbol) {
		Graphic pointGraphic = new Graphic(point, symbol);
	    return waterSensorsLayer.addGraphic(pointGraphic);
	}

	/**
	 * Adauga senzor pe harta pornind de la coordonate geografice (latitudine, longitudine).
	 * Intoarce id-ul unic (dpdv al layer-ului grafic) al punctului adaugat.
	 */
	private int addWaterSensorByGeoCoords(Geometry point, PictureMarkerSymbol symbol) {
		Point pointProj = (Point) GeometryEngine.project(point, wgs84, map.getSpatialReference());

	    Graphic pointGraphic = new Graphic(pointProj, symbol);
	    return waterSensorsLayer.addGraphic(pointGraphic);
	}

	/**
	 * Adauga incident pe harta pornind de la coordonate geografice (latitudine, longitudine).
	 * Intoarce id-ul unic (dpdv al layer-ului grafic) al punctului adaugat.
	 */
	private int addIncidentByGeoCoords(Geometry point, PictureMarkerSymbol symbol) {
		Point pointProj = (Point) GeometryEngine.project(point, wgs84, map.getSpatialReference());

	    Graphic pointGraphic = new Graphic(pointProj, symbol);
	    return incidentsLayer.addGraphic(pointGraphic);
	}

	public void disposeMapOnClose() {
		map.dispose();
	}

	private void addMapEventListener() {
		drawingOverlay.addDrawingCompleteListener(new DrawingCompleteListener() {
			@Override
	        public void drawingCompleted(DrawingCompleteEvent event) {
				if (state != State.ADD) {
					return;
				}

				Integer graphicId;
				Graphic graphic = (Graphic) drawingOverlay.getAndClearFeature();
				Point pointProj = (Point) GeometryEngine.project(graphic.getGeometry(), map.getSpatialReference(), wgs84);

				WaterSensorBuilder waterSensorBuilder = new WaterSensorBuilder();
				WaterSensor waterSensor;
				List<WaterSensor> matchingWaterSensors;

				waterSensor = waterSensorBuilder
					.withLatitude(pointProj.getX())
					.withLongitude(pointProj.getY())
					.withAuthorityId(activeAuthority.getId())
					.build();

				notifyAllObservers(ViewType.WATER_SENSOR_ADD_MODAL, waterSensorBuilder);

				matchingWaterSensors = waterSensorDao.findMatchingEntities(waterSensor);

				// din modala nu s-a salvat senzorul
				if (matchingWaterSensors.isEmpty()) {
					changeState(State.DISPLAY_MAP);
					return;
				}

				waterSensor = matchingWaterSensors.get(0);
				waterSensors.add(waterSensor);

				graphicId = addWaterSensorBySpatialRef(graphic.getGeometry(), greenSensorSymbol);
				waterSensorsGraphics.put(graphicId, waterSensor);

				changeState(State.DISPLAY_MAP);
			}
		});
	}

	private Component createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setLayout(new MigLayout("al center center"));

	    JButton toogleSensorsButton = new JButton(SHOW_SENSORS_BUTTON_TEXT);
	    toogleSensorsButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.VIEW_ICON.getIconName(), 25, 25)
		));

	    JButton toogleIncidentsButton = new JButton(SHOW_INCIDENTS_BUTTON_TEXT);
	    toogleIncidentsButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.VIEW_ICON.getIconName(), 25, 25)
		));

		JButton addSensorButton = new JButton(ADD_SENSOR_BUTTON_TEXT);
		addSensorButton.setHorizontalAlignment(SwingConstants.LEFT);
		addSensorButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.GREEN_SENSOR_ICON.getIconName(), 25, 25)
		));
		addSensorButton.setEnabled(false);

		JButton deleteSensorButton = new JButton(DELETE_SENSOR_BUTTON_TEXT);
		deleteSensorButton.setHorizontalAlignment(SwingConstants.LEFT);
		deleteSensorButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.DELETE_ICON.getIconName(), 25, 25)
		));
		deleteSensorButton.setEnabled(false);

		JButton chartsButton = new JButton("Charts");
		chartsButton.setHorizontalAlignment(SwingConstants.LEFT);
		chartsButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.CHART_ICON.getIconName(), 25, 25)
		));
		chartsButton.setEnabled(false);

	    JButton logoutButton = new JButton("Logout");
	    logoutButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.LOGOUT_ICON.getIconName(), 25, 25)
		));

	    toolBar.add(toogleSensorsButton);
	    toolBar.add(toogleIncidentsButton);
	    toolBar.add(Box.createHorizontalGlue(), "pushx, growx");
	    toolBar.add(addSensorButton);
	    toolBar.add(deleteSensorButton);
	    toolBar.add(chartsButton);
	    toolBar.add(Box.createHorizontalGlue(), "pushx, growx");
	    toolBar.add(authorityPictureLabel);
	    toolBar.add(logoutButton);

	    toogleSensorsButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		if (toogleSensorsButton.getText().equals(SHOW_SENSORS_BUTTON_TEXT)) {
	    			toogleSensorsButton.setText(HIDE_SENSORS_BUTTON_TEXT);
	    			showWaterSensors();
	    			addSensorButton.setEnabled(true);
	    			deleteSensorButton.setEnabled(true);
	    			chartsButton.setEnabled(true);
	    			changeState(State.DISPLAY_MAP);
	    		} else if (toogleSensorsButton.getText().equals(HIDE_SENSORS_BUTTON_TEXT)) {
	    			toogleSensorsButton.setText(SHOW_SENSORS_BUTTON_TEXT);
	    			hideWaterSensors();
	    			addSensorButton.setEnabled(false);
	    			deleteSensorButton.setEnabled(false);
	    			chartsButton.setEnabled(false);
	    			changeState(State.NONE);
	    		}
	    	}
		});

	    toogleIncidentsButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		if (toogleIncidentsButton.getText().equals(SHOW_INCIDENTS_BUTTON_TEXT)) {
	    			toogleIncidentsButton.setText(HIDE_INCIDENTS_BUTTON_TEXT);
	    			showIncidents();
	    			changeState(State.DISPLAY_MAP);
	    		} else if (toogleIncidentsButton.getText().equals(HIDE_INCIDENTS_BUTTON_TEXT)) {
	    			toogleIncidentsButton.setText(SHOW_INCIDENTS_BUTTON_TEXT);
	    			hideIncidents();
	    			changeState(State.NONE);
	    		}
	    	}
		});

	    addSensorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeState(State.ADD);
			}
		});

	    deleteSensorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeState(State.DELETE);
			}
		});

	    chartsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeState(State.DISPLAY_CHART);
			}
		});

	    logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(
					null,
					"Are you sure you want to logout?",
					"Warning",
					dialogButton
				);
			
				if (dialogResult != JOptionPane.YES_OPTION) {
					return;
				}
				notifyAllObservers(ViewType.LOGIN_VIEW, null);
			}
		});

		return toolBar;
	}

	private void createSensorsGraphics() {
		PictureMarkerSymbol sensorSymbol;
		Integer graphicId;

		hideWaterSensors();

		for (WaterSensor waterSensor : waterSensors) {
			if (waterSensor.getAuthorityId() == activeAuthority.getId()) {
				sensorSymbol = greenSensorSymbol;
			} else {
				sensorSymbol = redSensorSymbol;
			}

			graphicId = addWaterSensorByGeoCoords(new Point(waterSensor.getLatitude(), waterSensor.getLongitude()), sensorSymbol); 
			waterSensorsGraphics.put(graphicId, waterSensor);
		}
		
		createGraphicsForWaterSensors = false;
	}

	private void createIncidentsGraphics() {
		Integer graphicId;

		hideIncidents();

		for (Incident incident : incidents) {
			graphicId = addIncidentByGeoCoords(new Point(incident.getLatitude(), incident.getLongitude()), greenIncidentSymbol); 
			incidentsGraphics.put(graphicId, incident);
		}

		createGraphicsForWaterSensors = false;
	}

	private void showWaterSensors() {
		if (createGraphicsForWaterSensors) {
			createSensorsGraphics();
		}

		waterSensorsLayer.setVisible(true);
	}

	private void hideWaterSensors() {
		waterSensorsLayer.setVisible(false);
	}

	private void showIncidents() {
		if (createGraphicsForIncidents) {
			createIncidentsGraphics();
		}

		incidentsLayer.setVisible(true);
	}

	private void hideIncidents() {
		incidentsLayer.setVisible(false);
	}

	private void performWaterSensorDelete(Integer waterSensorGraphicId, WaterSensor waterSensor) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(
			null,
			"Are you sure you want to delete selected water sensor?",
			"Warning",
			dialogButton
		);

		if (dialogResult != JOptionPane.YES_OPTION) {
			return;
		}

		if (waterSensor.getAuthorityId() != activeAuthority.getId()) {
	        JOptionPane.showMessageDialog(
                null,
                "You don't have the right to delete this water sensor as it was placed by another authority!",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
			return;
		}

		waterSensorDao.delete(waterSensor);
		waterSensorsLayer.removeGraphic(waterSensorGraphicId);
		waterSensorsGraphics.remove(waterSensorGraphicId);
		waterSensors.remove(waterSensor);

        JOptionPane.showMessageDialog(
            null,
            "Water sensor deleted successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
	}

	private void changeState(State state) {
		this.state = state;

		if (state == State.ADD) {
			drawingOverlay.setUp(DrawingMode.POINT, greenSensorSymbol, null);
		} else {
			drawingOverlay.setUp(DrawingMode.NONE, greenSensorSymbol, null);
		}
	}

	@Override
	public void populateView(Object arg) {
		changeState(State.NONE);
		createGraphicsForWaterSensors = true;
		createGraphicsForIncidents = true;
		waterSensorsGraphics = new HashMap<>();
		incidentsGraphics = new HashMap<>();

		ViewCleaner.getInstance().clearView(instance);
		hideWaterSensors();
		hideIncidents();

		waterSensors = waterSensorDao.findAll();
		incidents = incidentDao.findAll();
		map.setExtent(romaniaEnvelope);

		if (arg instanceof User) {
			activeAuthority = (User) arg;

			if (activeAuthority.getPictureId() == null) {
				authorityPictureLabel.setIcon(new ImageIcon(
					ImageLoader.getInstance().getScaledImageFromDB(Icon.DEFAULT_PROFILE_ICON.getIconName(), 25, 25)
				));
			} else {
				Picture picture = pictureDao.findById(activeAuthority.getPictureId());
				authorityPictureLabel.setIcon(new ImageIcon(
					ImageLoader.getInstance().getScaledImageFromDB(picture.getName(), 25, 25)
				));
			}
		}
	}

	class GraphicSelectedListener implements HitTestListener {
		private GraphicsLayer graphicsLayer;

		public GraphicSelectedListener(GraphicsLayer graphicsLayer) {
			super();
			this.graphicsLayer = graphicsLayer;
		}

		@Override
	    public void featureHit(HitTestEvent event) {
			List<Feature> hitGraphics = event.getOverlay().getHitFeatures();

			if (hitGraphics.isEmpty()) {
				return;
			}

			if (graphicsLayer == waterSensorsLayer) {
				WaterSensor waterSensor = waterSensorsGraphics.get((int) hitGraphics.get(0).getId());

				if (state == State.DISPLAY_MAP) {
					notifyAllObservers(ViewType.WATER_SENSOR_DISPLAY_MODAL, waterSensor);
					changeState(State.DISPLAY_MAP);
				} else if (state == State.DISPLAY_CHART) {
					notifyAllObservers(ViewType.CHARTS_MODAL, waterSensor);
					changeState(State.DISPLAY_MAP);
				} else if (state == State.DELETE) {
					performWaterSensorDelete((int) hitGraphics.get(0).getId(), waterSensor);
					changeState(State.DISPLAY_MAP);
				}
			} else if (graphicsLayer == incidentsLayer) {
				Incident incident = incidentsGraphics.get((int) hitGraphics.get(0).getId());

				if (state == State.DISPLAY_MAP) {
					notifyAllObservers(ViewType.INCIDENT_DISPLAY_MODAL, incident);
					changeState(State.DISPLAY_MAP);
				}
			}
		}
	}

	enum State {
		ADD, DISPLAY_MAP, DISPLAY_CHART, DELETE, NONE;
	}
}
