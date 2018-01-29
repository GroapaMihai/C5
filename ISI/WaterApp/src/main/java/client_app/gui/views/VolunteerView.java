package client_app.gui.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Feature;
import com.esri.core.map.Graphic;
import com.esri.core.portal.Portal;
import com.esri.core.portal.WebMap;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.MapOptions;
import com.esri.map.MapOptions.MapType;
import com.esri.toolkit.overlays.DrawingCompleteEvent;
import com.esri.toolkit.overlays.DrawingCompleteListener;
import com.esri.toolkit.overlays.DrawingOverlay;
import com.esri.toolkit.overlays.HitTestEvent;
import com.esri.toolkit.overlays.HitTestListener;
import com.esri.toolkit.overlays.HitTestOverlay;

import com.esri.toolkit.overlays.DrawingOverlay.DrawingMode;

import client_app.appl.dao.IncidentDao;
import client_app.appl.dao.PictureDao;
import client_app.appl.domain.builders.IncidentBuilder;
import client_app.appl.domain.entities.Incident;
import client_app.appl.domain.entities.Picture;
import client_app.appl.domain.entities.User;
import client_app.picture.Icon;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class VolunteerView extends View {
	private static final long serialVersionUID = 1L;
	private static VolunteerView instance = null;

	//private static final Portal portal = new Portal("https://www.arcgis.com", null);
	//private static final String itemID = "064327e68cc34e1792b7c7ea8b551c3c";

	private State state;
	private boolean createGraphicsForIncidents = false;

	private JMap map;
	private Envelope romaniaEnvelope;
	private GraphicsLayer graphicsLayer;
	private DrawingOverlay drawingOverlay;
	private HitTestOverlay hitTestOverlay;
	private SpatialReference wgs84 = SpatialReference.create(4326);

	private String ADD_INCIDENT_BUTTON_TEXT = "Add incident";
	private String SHOW_INCIDENTS_BUTTON_TEXT = "Show incidents";
	private String HIDE_INCIDENTS_BUTTON_TEXT = "Hide incidents";
	private String DELETE_INCIDENT_BUTTON_TEXT = "Delete incident";
	private PictureMarkerSymbol greenIncidentSymbol;
	private PictureMarkerSymbol redIncidentSymbol;

	private IncidentDao incidentDao;
	private List<Incident> incidents;
	private Map<Integer, Incident> incidentsGraphics;
	
	private User activeVolunteer;

	private PictureDao pictureDao;
	private JLabel volunteerPictureLabel;
	
	private VolunteerView() {
		viewType = ViewType.VOLUNTEER_VIEW;
		width = Integer.MAX_VALUE;
		height = Integer.MAX_VALUE;
		
		setLayout(new BorderLayout());
		
		greenIncidentSymbol = new PictureMarkerSymbol(
			ImageLoader.getInstance().getImageFromDB(Icon.GREEN_INCIDENT_ICON.getIconName())
		);
			
		redIncidentSymbol = new PictureMarkerSymbol(
			ImageLoader.getInstance().getImageFromDB(Icon.RED_INCIDENT_ICON.getIconName())
		);
		
		incidentDao = IncidentDao.getInstance();
		incidents = incidentDao.findAll();
		
		map = new JMap();
		
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

		ArcGISTiledMapServiceLayer tiledLayer = new ArcGISTiledMapServiceLayer(
			"http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer"
		);
		map.getLayers().add(tiledLayer);
		map.setExtent(romaniaEnvelope);

		graphicsLayer = new GraphicsLayer();
		map.getLayers().add(graphicsLayer);

		drawingOverlay = new DrawingOverlay();
		map.addMapOverlay(drawingOverlay);

		hitTestOverlay = new HitTestOverlay(graphicsLayer);
		hitTestOverlay.addHitTestListener(new GraphicSelectedListener());
		map.addMapOverlay(hitTestOverlay);

		addMapEventListener();

		pictureDao = PictureDao.getInstance();
		volunteerPictureLabel = new JLabel();

		JLayeredPane contentPane = new JLayeredPane();
		contentPane.setLayout(new BorderLayout());
	    contentPane.setVisible(true);
	    contentPane.add(map);
	    contentPane.add(createToolBar(), BorderLayout.NORTH);

	    add(contentPane);
	}
	
	public static VolunteerView getInstance() {
		if (instance == null) {
			instance = new VolunteerView();
		}
		
		return instance;
	}

	/**
	 * Adauga punct pe harta cunoscand deja coordonatele sale in spatial reference,
	 * dupa care obtine coordonatele geografice (latitudine, longitudine) ale punctului adaugat,
	 * pentru a putea fi salvat in baza de date.
	 */
	private int addPointBySpatialRef(Geometry point, PictureMarkerSymbol symbol) {
		Graphic pointGraphic = new Graphic(point, symbol);
	    return graphicsLayer.addGraphic(pointGraphic);
	}

	/**
	 * Adauga punct pe harta pornind de la coordonate geografice (latitudine, longitudine).
	 * Intoarce id-ul unic (dpdv al layer-ului grafic) al punctului adaugat.
	 */
	private int addPointByGeoCoords(Geometry point, PictureMarkerSymbol symbol) {
		Point pointProj = (Point) GeometryEngine.project(point, wgs84, map.getSpatialReference());

	    Graphic pointGraphic = new Graphic(pointProj, symbol);
	    return graphicsLayer.addGraphic(pointGraphic);
	}

	public void disposeMapOnClose() {
		map.dispose();
	}

	private void addMapEventListener() {
		drawingOverlay.addDrawingCompleteListener(new DrawingCompleteListener() {
			@Override
	        public void drawingCompleted(DrawingCompleteEvent event) {
				if (drawingOverlay.getDrawingMode() == DrawingMode.NONE) {
					return;
				}

				Integer graphicId;
				Graphic graphic = (Graphic) drawingOverlay.getAndClearFeature();
				Point pointProj = (Point) GeometryEngine.project(graphic.getGeometry(), map.getSpatialReference(), wgs84);

				IncidentBuilder incidentBuilder = new IncidentBuilder();
				Incident incident;
				List<Incident> matchingIncidents;

				incident = incidentBuilder
					.withLatitude(pointProj.getX())
					.withLongitude(pointProj.getY())
					.withVolunteerId(activeVolunteer.getId())
					.build();

				notifyAllObservers(ViewType.INCIDENT_ADD_MODAL, incidentBuilder);

				matchingIncidents = incidentDao.findMatchingEntities(incident);

				// din modala nu s-a salvat incidentul
				if (matchingIncidents.isEmpty()) {
					changeState(State.DISPLAY);
					return;
				}

				incident = matchingIncidents.get(0);
				incidents.add(incident);

				graphicId = addPointBySpatialRef(graphic.getGeometry(), greenIncidentSymbol);
				incidentsGraphics.put(graphicId, incident);

				changeState(State.DISPLAY);
			}
		});
	}

	private Component createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setLayout(new MigLayout("al center center"));

	    JButton toogleIncidentsButton = new JButton(SHOW_INCIDENTS_BUTTON_TEXT);
	    toogleIncidentsButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.VIEW_ICON.getIconName(), 25, 25)
		));

		JButton addIncidentButton = new JButton(ADD_INCIDENT_BUTTON_TEXT);
		addIncidentButton.setHorizontalAlignment(SwingConstants.LEFT);
		addIncidentButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.GREEN_INCIDENT_ICON.getIconName(), 25, 25)
		));
		addIncidentButton.setEnabled(false);

		JButton deleteIncidentButton = new JButton(DELETE_INCIDENT_BUTTON_TEXT);
		deleteIncidentButton.setHorizontalAlignment(SwingConstants.LEFT);
		deleteIncidentButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.DELETE_ICON.getIconName(), 25, 25)
		));
		deleteIncidentButton.setEnabled(false);

	    JButton logoutButton = new JButton("Logout");
	    logoutButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.LOGOUT_ICON.getIconName(), 25, 25)
		));

	    toolBar.add(toogleIncidentsButton);
	    toolBar.add(addIncidentButton);
	    toolBar.add(deleteIncidentButton);
	    toolBar.add(Box.createHorizontalGlue(), "pushx, growx");
	    toolBar.add(volunteerPictureLabel);
	    toolBar.add(logoutButton);

	    toogleIncidentsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (toogleIncidentsButton.getText().equals(SHOW_INCIDENTS_BUTTON_TEXT)) {
					toogleIncidentsButton.setText(HIDE_INCIDENTS_BUTTON_TEXT);
					showIncidentsGraphics();
					addIncidentButton.setEnabled(true);
					deleteIncidentButton.setEnabled(true);
					changeState(State.DISPLAY);
				} else if (toogleIncidentsButton.getText().equals(HIDE_INCIDENTS_BUTTON_TEXT)) {
					toogleIncidentsButton.setText(SHOW_INCIDENTS_BUTTON_TEXT);
					hideIncidentsGraphics();
					addIncidentButton.setEnabled(false);
					deleteIncidentButton.setEnabled(false);
					changeState(State.NONE);
				}
			}
	    });

	    addIncidentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeState(State.ADD);
			}
		});

	    deleteIncidentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeState(State.DELETE);
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

	private void createIncidentsGraphics() {
		PictureMarkerSymbol incidentSymbol;
		Integer graphicId;

		for (Incident incident : incidents) {
			if (incident.getVolunteerId() == activeVolunteer.getId()) {
				incidentSymbol = greenIncidentSymbol;
			} else {
				incidentSymbol = redIncidentSymbol;
			}

			graphicId = addPointByGeoCoords(new Point(incident.getLatitude(), incident.getLongitude()), incidentSymbol);
			incidentsGraphics.put(graphicId, incident);
		}
		
		createGraphicsForIncidents = false;
	}
	
	private void showIncidentsGraphics() {
		if (createGraphicsForIncidents) {
			createIncidentsGraphics();
		}

		graphicsLayer.setVisible(true);
	}

	private void hideIncidentsGraphics() {
		graphicsLayer.setVisible(false);
	}

	private void performIncidentDelete(Integer incidentGraphicId, Incident incident) {
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(
			null,
			"Are you sure you want to delete selected incident?",
			"Warning",
			dialogButton
		);

		if (dialogResult != JOptionPane.YES_OPTION) {
			return;
		}

		if (incident.getVolunteerId() != activeVolunteer.getId()) {
	        JOptionPane.showMessageDialog(
                null,
                "You don't have the right to delete this incident as it was reported by another volunteer!",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
			return;
		}
		
		Picture picture = pictureDao.findById(incident.getCapture());	

		incidentDao.delete(incident);
		graphicsLayer.removeGraphic(incidentGraphicId);
		incidentsGraphics.remove(incidentGraphicId);
		incidents.remove(incident);

		pictureDao.delete(picture);

        JOptionPane.showMessageDialog(
            null,
            "Incident deleted successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
	}

	@Override
	public void populateView(Object arg) {
		changeState(State.NONE);
		createGraphicsForIncidents = true;
		incidentsGraphics = new HashMap<>();

		ViewCleaner.getInstance().clearView(instance);
		hideIncidentsGraphics();

		incidents = incidentDao.findAll();
		map.setExtent(romaniaEnvelope);

		if (arg instanceof User) {
			activeVolunteer = (User) arg;
			
			if (activeVolunteer.getPictureId() == null) {
				volunteerPictureLabel.setIcon(new ImageIcon(
					ImageLoader.getInstance().getScaledImageFromDB(Icon.DEFAULT_PROFILE_ICON.getIconName(), 25, 25)
				));
			} else {
				Picture picture = pictureDao.findById(activeVolunteer.getPictureId());
				volunteerPictureLabel.setIcon(new ImageIcon(
					ImageLoader.getInstance().getScaledImageFromDB(picture.getName(), 25, 25)
				));
			}
		}
	}

	private void changeState(State state) {
		this.state = state;
		
		if (state == State.ADD) {
			drawingOverlay.setUp(DrawingMode.POINT, greenIncidentSymbol, null);
		} else {
			drawingOverlay.setUp(DrawingMode.NONE, greenIncidentSymbol, null);
		}
	}

	class GraphicSelectedListener implements HitTestListener {
		@Override
	    public void featureHit(HitTestEvent event) {
			List<Feature> hitGraphics = event.getOverlay().getHitFeatures();
			Incident incident;

			if (hitGraphics.isEmpty()) {
				return;
			}

			incident = incidentsGraphics.get((int) hitGraphics.get(0).getId());

			if (state == State.DISPLAY) {
				notifyAllObservers(ViewType.INCIDENT_DISPLAY_MODAL, incident);
				changeState(State.DISPLAY);
			} else if (state == State.DELETE) {
				performIncidentDelete((int) hitGraphics.get(0).getId(), incident);
				changeState(State.DISPLAY);
			}
		}
	}

	enum State {
		ADD, DISPLAY, DELETE, NONE;
	}
}
