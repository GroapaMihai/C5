package client_app.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.sql.Timestamp;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import client_app.api.annotations.Clear;
import client_app.appl.dao.SampleDao;
import client_app.appl.dao.WaterSensorDao;
import client_app.appl.domain.builders.SampleBuilder;
import client_app.appl.domain.builders.WaterSensorBuilder;
import client_app.appl.domain.entities.Sample;
import client_app.appl.domain.entities.WaterSensor;
import client_app.gui.components.Fonts;
import client_app.gui.components.JNumberTextField;
import client_app.picture.Icon;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class WaterSensorAddModal extends View {
	private static final long serialVersionUID = 1L;
	private static WaterSensorAddModal instance = null;

	private JLabel turbidityLabel;
	private JLabel phLabel;
	private JLabel salinityLabel;
	private JLabel dissolvedOxygenLabel;
	private JLabel EColiLabel;
	private JLabel enterococcusBacteriaLabel;
	private JLabel sampleRateLabel;

	@Clear(defaultValue = "25")
	private JNumberTextField turbidityField;

	@Clear(defaultValue = "7.00")
	private JNumberTextField phField;

	@Clear(defaultValue = "500")
	private JNumberTextField salinityField;

	@Clear(defaultValue = "5.00")
	private JNumberTextField dissolvedOxygenField;

	@Clear(defaultValue = "50")
	private JNumberTextField EColiField;

	@Clear(defaultValue = "30")
	private JNumberTextField enterococcusBacteriaField;

	@Clear(defaultValue = "1980-01-01 00:00:00")
	private JSpinner sampleRateSpinner;

	private WaterSensorDao waterSensorDao;
	private WaterSensorBuilder waterSensorBuilder;
	private SampleDao sampleDao;
	private SampleBuilder sampleBuilder;

	private JButton saveAndCloseButton;

	private WaterSensorAddModal() {
		viewType = ViewType.WATER_SENSOR_ADD_MODAL;
		width = 500;
		height = 450;

		setLayout(new MigLayout("insets 20, align 50% 50%"));

		initGUIElements();
		addGUIElements();
		addListeners();
	}

	public static WaterSensorAddModal getInstance() {
		if (instance == null) {
			instance = new WaterSensorAddModal();
		}
		
		return instance;
	}

	private void initGUIElements() {
		turbidityLabel = new JLabel("Turbidity");
		turbidityLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		turbidityField = new JNumberTextField(2, JNumberTextField.DECIMAL);
		turbidityField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		turbidityField.setAllowNegative(false);
		turbidityField.setPrecision(0);

		phLabel = new JLabel("PH level");
		phLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		phField = new JNumberTextField(4, JNumberTextField.DECIMAL);
		phField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		phField.setAllowNegative(false);
		phField.setPrecision(2);

		salinityLabel = new JLabel("Salinity");
		salinityLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		salinityField = new JNumberTextField(4, JNumberTextField.DECIMAL);
		salinityField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		salinityField.setAllowNegative(false);
		salinityField.setPrecision(0);

		dissolvedOxygenLabel = new JLabel("Dissolved oxygen");
		dissolvedOxygenLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		dissolvedOxygenField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		dissolvedOxygenField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		dissolvedOxygenField.setAllowNegative(false);
		dissolvedOxygenField.setPrecision(2);

		EColiLabel = new JLabel("E coli");
		EColiLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		EColiField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		EColiField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		EColiField.setAllowNegative(false);
		EColiField.setPrecision(0);

		enterococcusBacteriaLabel = new JLabel("Enterococcus Bacteria");
		enterococcusBacteriaLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		enterococcusBacteriaField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		enterococcusBacteriaField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		enterococcusBacteriaField.setAllowNegative(false);
		enterococcusBacteriaField.setPrecision(0);

		sampleRateLabel = new JLabel("Sample rate");
		sampleRateLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		java.util.Date utilDate = new java.util.Date(Calendar.getInstance().getTime().getTime());
		SpinnerDateModel sm = new SpinnerDateModel(utilDate, null, null, Calendar.HOUR_OF_DAY);
 
		sampleRateSpinner = new JSpinner(sm);
		DateEditor de = new DateEditor(sampleRateSpinner, "HH:mm:ss");

		sampleRateSpinner.setEditor(de);

		saveAndCloseButton = new JButton("Save and Close");
		saveAndCloseButton.setHorizontalAlignment(SwingConstants.LEFT);
		saveAndCloseButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.SAVE_ICON.getIconName(), 25, 25)
		));
	}

	private void addGUIElements() {
        add(turbidityLabel, "gapbottom 10px, gapleft 25px");
        add(turbidityField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

        add(phLabel, "gapbottom 10px, gapleft 25px");
        add(phField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

        add(salinityLabel, "gapbottom 10px, gapleft 25px");
        add(salinityField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

        add(dissolvedOxygenLabel, "gapbottom 10px, gapleft 25px");
        add(dissolvedOxygenField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

        add(EColiLabel, "gapbottom 10px, gapleft 25px");
        add(EColiField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

        add(enterococcusBacteriaLabel, "gapbottom 10px, gapleft 25px");
        add(enterococcusBacteriaField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

        add(sampleRateLabel, "gapbottom 10px, gapleft 25px");
        add(sampleRateSpinner, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

        add(saveAndCloseButton, "gapbottom 10px, gapleft 25px, spanx2, center");
    }

	private void addListeners() {
		saveAndCloseButtonListener();
	}

	private void saveAndCloseButtonListener() {
		saveAndCloseButton.addActionListener(new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
		    	if (waterSensorBuilder == null) {
		    		return;
		    	}

		    	waterSensorDao = WaterSensorDao.getInstance();
		    	WaterSensor waterSensor;
		    	sampleDao = SampleDao.getInstance();
		    	sampleBuilder = new SampleBuilder();
		    	Sample sample;
		    	java.util.Date sampleRate = (java.util.Date) sampleRateSpinner.getModel().getValue();

		    	waterSensor = waterSensorBuilder
		    			.withSampleRate(new Timestamp(sampleRate.getTime()))
		    			.build();

		    	waterSensorBuilder = null;
		    	waterSensor = waterSensorDao.insert(waterSensor);

		    	sample = sampleBuilder
		    		.withTurbidity(turbidityField.getInt())
		    		.withPh(phField.getDouble())
	    			.withSalinity(salinityField.getInt())
	    			.withDissolvedOxygen(dissolvedOxygenField.getDouble())
	    			.withEColi(EColiField.getInt())
	    			.withEnterococcusBacteria(enterococcusBacteriaField.getInt())
	    			.withSampleTime(new Timestamp(System.currentTimeMillis()))
	    			.withWaterSensorId(waterSensor.getId())
	    			.build();
		    	sampleDao.insert(sample);

	    		JOptionPane.showMessageDialog(
    				null,
    				"Water sensor saved successfully!",
					"Success", 
					JOptionPane.INFORMATION_MESSAGE
				);

	    		notifyAllObservers(ViewType.HIDE, null);
		    }
		});
	}

	@Override
	public void populateView(Object arg) {
		ViewCleaner.getInstance().clearView(this);
		
		if (arg instanceof WaterSensorBuilder) {
			waterSensorBuilder = (WaterSensorBuilder) arg;
		}
	}
}
