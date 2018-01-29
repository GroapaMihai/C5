package client_app.main;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;

import client_app.gui.interfaces.Observer;
import client_app.gui.views.ChartsModal;
import client_app.gui.views.IncidentAddModal;
import client_app.gui.views.IncidentDisplayModal;
import client_app.gui.views.View;
import client_app.gui.views.ViewType;
import client_app.gui.views.WaterSensorAddModal;
import client_app.gui.views.WaterSensorDisplayModal;

public class WaterMonitoringForm extends JDialog implements Observer {
	private static final long serialVersionUID = 1L;
	private static WaterMonitoringForm instance = null;

    private JPanel basePanel;
    private CardLayout cardLayout;

    private WaterSensorAddModal waterSensorAddModal;
    private WaterSensorDisplayModal waterSensorDisplayModal;
    private IncidentAddModal incidentAddModal;
    private IncidentDisplayModal incidentDisplayModal;
    private ChartsModal chartsModal;
    
	private WaterMonitoringForm() {
        cardLayout = new CardLayout();
        basePanel = new JPanel();
        basePanel.setLayout(cardLayout);
        
        waterSensorAddModal = WaterSensorAddModal.getInstance();
        waterSensorAddModal.attachObserver(this);
        basePanel.add(waterSensorAddModal, waterSensorAddModal.getViewName());

        waterSensorDisplayModal = WaterSensorDisplayModal.getInstance();
        waterSensorDisplayModal.attachObserver(this);
        basePanel.add(waterSensorDisplayModal, waterSensorDisplayModal.getViewName());

        incidentAddModal = IncidentAddModal.getInstance();
        incidentAddModal.attachObserver(this);
        basePanel.add(incidentAddModal, incidentAddModal.getViewName());

        incidentDisplayModal = IncidentDisplayModal.getInstance();
        incidentDisplayModal.attachObserver(this);
        basePanel.add(incidentDisplayModal, incidentDisplayModal.getViewName());

        chartsModal = ChartsModal.getInstance();
        chartsModal.attachObserver(this);
        basePanel.add(chartsModal, chartsModal.getViewName());

		setModal(true);
        add(basePanel);
	}

	public static WaterMonitoringForm getInstance() {
		if (instance == null) {
			instance = new WaterMonitoringForm();
		}
		
		return instance;
	}

    private void displayView(View view) {
    	Dimension viewDimension = view.getViewDimension();

    	cardLayout.show(basePanel, view.getViewName());
    	setTitle(view.getViewName());
    	setMinimumSize(viewDimension);
    	setSize(viewDimension);
    	setLocationRelativeTo(null);
		setVisible(true);
    }

	@Override
	public void update(ViewType viewType, Object arg) {
		View view = null;

		switch (viewType) {
			case WATER_SENSOR_ADD_MODAL: view = waterSensorAddModal; break;
			case WATER_SENSOR_DISPLAY_MODAL: view = waterSensorDisplayModal; break;
			case INCIDENT_ADD_MODAL: view = incidentAddModal; break;
			case INCIDENT_DISPLAY_MODAL: view = incidentDisplayModal; break;
			case CHARTS_MODAL: view = chartsModal; break;
			case HIDE: setVisible(false); dispose(); break;
			default: view = null; break;
		}

		if (view != null) {
	        view.populateView(arg);
	        displayView(view);
		}
	}
}
