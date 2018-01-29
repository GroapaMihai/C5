package client_app.gui.views;

import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import client_app.appl.dao.PictureDao;
import client_app.appl.dao.SampleDao;
import client_app.appl.dao.UserDao;
import client_app.appl.domain.entities.Picture;
import client_app.appl.domain.entities.Sample;
import client_app.appl.domain.entities.User;
import client_app.appl.domain.entities.WaterSensor;

import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextField;

import client_app.gui.components.Fonts;
import client_app.gui.components.JNumberTextField;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class WaterSensorDisplayModal extends View {
	private static final long serialVersionUID = 1L;
	private static WaterSensorDisplayModal instance = null;
	private final int authorityPictureWidth = 150;
	private final int authorityPictureHeight = 150;

	private WaterSensor waterSensor;

	private JPanel scrollPanel;
	private JScrollPane scrollPane;

	private JLabel sensorDetailsLabel;
	private JLabel latitudeLabel;
	private JLabel longitudeLabel;
	private JLabel turbidityLabel;
	private JLabel phLabel;
	private JLabel salinityLabel;
	private JLabel dissolvedOxygenLabel;
	private JLabel EColiLabel;
	private JLabel enterococcusBacteriaLabel;
	private JLabel lastSampleTimeLabel;
	private JLabel sampleRateLabel;

	private JLabel authorityDetailsLabel;
	private JLabel authorityFullNameLabel;
	private JLabel authorityEmailLabel;
	private JLabel authorityPictureLabel;

	private JNumberTextField latitudeField;
	private JNumberTextField longitudeField;
	private JNumberTextField turbidityField;
	private JNumberTextField phField;
	private JNumberTextField salinityField;
	private JNumberTextField dissolvedOxygenField;
	private JNumberTextField EColiField;
	private JNumberTextField enterococcusBacteriaField;
	private JTextField lastSampleTimeField;
	private JSpinner sampleRateSpinner;

	private JTextField authorityFullNameField;
	private JTextField authorityEmailField;
	
	private WaterSensorDisplayModal() {
		viewType = ViewType.WATER_SENSOR_DISPLAY_MODAL;
		width = 500;
		height = 450;

		setLayout(new MigLayout("insets 20, align 50% 50%"));

		initGUIElements();
		addGUIElements();
	}

	public static WaterSensorDisplayModal getInstance() {
		if (instance == null) {
			instance = new WaterSensorDisplayModal();
		}
		
		return instance;
	}

	private void initGUIElements() {
		scrollPanel = new JPanel();
		scrollPanel.setLayout(new MigLayout("insets 20, align 50% 50%"));

		scrollPane = new JScrollPane(
			null,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);

		sensorDetailsLabel = new JLabel("Water sensor details");
		sensorDetailsLabel.setFont(Fonts.TAHOMA_BOLD_18.getFont());
		
		latitudeLabel = new JLabel("Latitude");
		latitudeLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		latitudeField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		latitudeField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		latitudeField.setPrecision(20);
		latitudeField.setEditable(false);

		longitudeLabel = new JLabel("Longitude");
		longitudeLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		longitudeField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		longitudeField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		longitudeField.setPrecision(20);
		longitudeField.setEditable(false);

		turbidityLabel = new JLabel("Turbidity");
		turbidityLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		turbidityField = new JNumberTextField(2, JNumberTextField.DECIMAL);
		turbidityField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		turbidityField.setAllowNegative(false);
		turbidityField.setPrecision(0);
		turbidityField.setEditable(false);

		phLabel = new JLabel("PH level");
		phLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		phField = new JNumberTextField(4, JNumberTextField.DECIMAL);
		phField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		phField.setAllowNegative(false);
		phField.setPrecision(2);
		phField.setEditable(false);

		salinityLabel = new JLabel("Salinity");
		salinityLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		salinityField = new JNumberTextField(4, JNumberTextField.DECIMAL);
		salinityField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		salinityField.setAllowNegative(false);
		salinityField.setPrecision(0);
		salinityField.setEditable(false);

		dissolvedOxygenLabel = new JLabel("Dissolved oxygen");
		dissolvedOxygenLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		dissolvedOxygenField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		dissolvedOxygenField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		dissolvedOxygenField.setAllowNegative(false);
		dissolvedOxygenField.setPrecision(2);
		dissolvedOxygenField.setEditable(false);
		
		EColiLabel = new JLabel("E coli");
		EColiLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		EColiField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		EColiField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		EColiField.setAllowNegative(false);
		EColiField.setPrecision(0);
		EColiField.setEditable(false);

		enterococcusBacteriaLabel = new JLabel("Enterococcus Bacteria");
		enterococcusBacteriaLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		enterococcusBacteriaField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		enterococcusBacteriaField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		enterococcusBacteriaField.setAllowNegative(false);
		enterococcusBacteriaField.setPrecision(0);
		enterococcusBacteriaField.setEditable(false);

		lastSampleTimeLabel = new JLabel("Last sample time");
		lastSampleTimeLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		lastSampleTimeField = new JTextField(18);
		lastSampleTimeField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		lastSampleTimeField.setEditable(false);

		sampleRateLabel = new JLabel("Sample rate");
		sampleRateLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		java.util.Date utilDate = new java.util.Date(Calendar.getInstance().getTime().getTime());
		SpinnerDateModel sm = new SpinnerDateModel(utilDate, null, null, Calendar.HOUR_OF_DAY);
 
		sampleRateSpinner = new JSpinner(sm);
		DateEditor de = new DateEditor(sampleRateSpinner, "HH:mm:ss");

		sampleRateSpinner.setEditor(de);
		sampleRateSpinner.setEnabled(false);

		authorityDetailsLabel = new JLabel("Authority details");
		authorityDetailsLabel.setFont(Fonts.TAHOMA_BOLD_18.getFont());

		authorityFullNameLabel = new JLabel("Full name");
		authorityFullNameLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		authorityFullNameField = new JTextField(18);
		authorityFullNameField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		authorityFullNameField.setEditable(false);

		authorityEmailLabel = new JLabel("Email");
		authorityEmailLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		authorityEmailField = new JTextField(18);
		authorityEmailField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		authorityEmailField.setEditable(false);
		
		authorityPictureLabel = new JLabel();
	}

	private void addGUIElements() {
		scrollPanel.add(sensorDetailsLabel, "gapbottom 20px, gapleft 25px, wrap");

		scrollPanel.add(latitudeLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(latitudeField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(longitudeLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(longitudeField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(turbidityLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(turbidityField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(phLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(phField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(salinityLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(salinityField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(dissolvedOxygenLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(dissolvedOxygenField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(EColiLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(EColiField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(enterococcusBacteriaLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(enterococcusBacteriaField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(lastSampleTimeLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(lastSampleTimeField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(sampleRateLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(sampleRateSpinner, "gapbottom 30px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(authorityDetailsLabel, "gapbottom 20px, gapleft 25px, wrap");

		scrollPanel.add(authorityFullNameLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(authorityFullNameField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");
		
		scrollPanel.add(authorityEmailLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(authorityEmailField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(authorityPictureLabel, "spanx2, center, gapbottom 25px, gapleft 25px, wrap");

		scrollPane.setViewportView(scrollPanel);
        add(scrollPane, "push, grow");
    }

	@Override
	public void populateView(Object arg) {
		UserDao userDao = UserDao.getInstance();
		User user = null;
		PictureDao pictureDao = PictureDao.getInstance();
		Picture picture = null;
		SampleDao sampleDao = SampleDao.getInstance();
		Sample lastSample = null;
		
		if (arg instanceof WaterSensor) {
			waterSensor = (WaterSensor) arg;
			user = userDao.findById(waterSensor.getAuthorityId());
			picture = pictureDao.findById(user.getPictureId());
			lastSample = sampleDao.getLastSampleOfWaterSensor(waterSensor);
		} else {
			return;
		}

		latitudeField.setDouble(waterSensor.getLatitude());
		longitudeField.setDouble(waterSensor.getLongitude());
		turbidityField.setInt(lastSample.getTurbidity());
		phField.setDouble(lastSample.getPh());
		salinityField.setInt(lastSample.getSalinity());
		dissolvedOxygenField.setDouble(lastSample.getDissolvedOxygen());
		EColiField.setInt(lastSample.getEColi());
		enterococcusBacteriaField.setInt(lastSample.getEnterococcusBacteria());
		lastSampleTimeField.setText(lastSample.getSampleTime().toString());
		sampleRateSpinner.getModel().setValue(waterSensor.getSampleRate());

		authorityFullNameField.setText(user.getLastName().toUpperCase() + " " + user.getFirstName());
		authorityEmailField.setText(user.getEmail());

		authorityPictureLabel.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(picture.getName(), authorityPictureWidth, authorityPictureHeight)
		));
	}
}
