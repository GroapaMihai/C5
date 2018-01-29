package client_app.gui.views;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client_app.appl.dao.IncidentTypeDao;
import client_app.appl.dao.PictureDao;
import client_app.appl.dao.SeverityLevelDao;
import client_app.appl.dao.UserDao;
import client_app.appl.domain.entities.Incident;
import client_app.appl.domain.entities.IncidentType;
import client_app.appl.domain.entities.Picture;
import client_app.appl.domain.entities.SeverityLevel;
import client_app.appl.domain.entities.User;
import client_app.gui.components.Fonts;
import client_app.gui.components.JNumberTextField;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class IncidentDisplayModal extends View {
	private static final long serialVersionUID = 1L;
	private static IncidentDisplayModal instance = null;
	private final int pictureWidth = 150;
	private final int pictureHeight = 150;

	private Incident incident;
	
	private JPanel scrollPanel;
	private JScrollPane scrollPane;
	
	private JLabel incidentDetailsLabel;
	private JLabel latitudeLabel;
	private JLabel longitudeLabel;
	private JLabel incidentTypeLabel;
	private JLabel severityLevelLabel;
	private JLabel affectedSurfaceLabel;
	private JLabel descriptionLabel;

	private JLabel volunteerFullNameLabel;
	private JLabel volunteerEmailLabel;
	private JLabel volunteerPictureLabel;

	private JNumberTextField latitudeField;
	private JNumberTextField longitudeField;
	private JTextField incidentTypeField;
	private JTextField severityLevelField;
	private JNumberTextField affectedSurfaceField;
	private JLabel pictureLabel;
    private JTextArea descriptionArea;
	private JScrollPane descriptionScrollPane;
	
	private JLabel volunteerDetailsLabel;
	private JTextField volunteerFullNameField;
	private JTextField volunteerEmailField;

	private IncidentDisplayModal() {
		viewType = ViewType.INCIDENT_DISPLAY_MODAL;
		width = 500;
		height = 450;

		setLayout(new MigLayout("insets 20, align 50% 50%"));

		initGUIElements();
		addGUIElements();
	}

	public static IncidentDisplayModal getInstance() {
		if (instance == null) {
			instance = new IncidentDisplayModal();
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

		incidentDetailsLabel = new JLabel("Incident details");
		incidentDetailsLabel.setFont(Fonts.TAHOMA_BOLD_18.getFont());

		incidentTypeLabel = new JLabel("Incident type");
		incidentTypeLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		incidentTypeField = new JTextField(18);
		incidentTypeField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		incidentTypeField.setEditable(false);
		
		severityLevelLabel = new JLabel("Severity level");
		severityLevelLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());
		
		severityLevelField = new JTextField(18);
		severityLevelField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		severityLevelField.setEditable(false);
		
		affectedSurfaceLabel = new JLabel("Affected surface");
		affectedSurfaceLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		affectedSurfaceField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		affectedSurfaceField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		affectedSurfaceField.setEditable(false);
		affectedSurfaceField.setAllowNegative(false);
		affectedSurfaceField.setPrecision(2);

		descriptionLabel = new JLabel("Incident description");
		descriptionLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		descriptionArea = new JTextArea(5, 20);
		descriptionArea.setEditable(false);
		descriptionScrollPane = new JScrollPane(descriptionArea);

		pictureLabel = new JLabel();

		volunteerDetailsLabel = new JLabel("Volunteer details");
		volunteerDetailsLabel.setFont(Fonts.TAHOMA_BOLD_18.getFont());
		
		volunteerFullNameLabel = new JLabel("Full name");
		volunteerFullNameLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());
	
		volunteerFullNameField = new JTextField(18);
		volunteerFullNameField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		volunteerFullNameField.setEditable(false);
		
		volunteerEmailLabel = new JLabel("Email");
		volunteerEmailLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());
		
		volunteerEmailField = new JTextField(18);
		volunteerEmailField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		volunteerEmailField.setEditable(false);

		volunteerPictureLabel = new JLabel();
	}

	private void addGUIElements() {
		scrollPanel.add(incidentDetailsLabel, "gapbottom 20px, gapleft 25px, wrap");

		scrollPanel.add(latitudeLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(latitudeField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(longitudeLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(longitudeField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(incidentTypeLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(incidentTypeField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(severityLevelLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(severityLevelField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(affectedSurfaceLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(affectedSurfaceField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(pictureLabel, "spanx2, center, gapbottom 10px, gapleft 25px, wrap");

		scrollPanel.add(descriptionLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(descriptionScrollPane, "gapbottom 30px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(volunteerDetailsLabel, "gapbottom 20px, gapleft 25px, wrap");

		scrollPanel.add(volunteerFullNameLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(volunteerFullNameField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");
		
		scrollPanel.add(volunteerEmailLabel, "gapbottom 10px, gapleft 25px");
		scrollPanel.add(volunteerEmailField, "gapbottom 10px, gapright 25px, pushx, growx, wrap");

		scrollPanel.add(volunteerPictureLabel, "spanx2, center, gapbottom 25px, gapleft 25px, wrap");

		scrollPane.setViewportView(scrollPanel);
        add(scrollPane, "push, grow");
	}

	@Override
	public void populateView(Object arg) {
		UserDao userDao = UserDao.getInstance();
		User user = null;
		PictureDao pictureDao = PictureDao.getInstance();
		Picture incidentCapture = null;
		Picture volunteerPicture = null;
		IncidentTypeDao incidentTypeDao = IncidentTypeDao.getInstance();
		IncidentType incidentType = null;
		SeverityLevelDao severityLevelDao = SeverityLevelDao.getInstance();
		SeverityLevel severityLevel = null;

		if (arg instanceof Incident) {
			incident = (Incident) arg;
			incidentType = incidentTypeDao.findById(incident.getIncidentTypeId());
			severityLevel = severityLevelDao.findById(incident.getSeverityLevelId());
			incidentCapture = pictureDao.findById(incident.getCapture());
			user = userDao.findById(incident.getVolunteerId());
			volunteerPicture = pictureDao.findById(user.getPictureId());
		} else {
			return;
		}

		latitudeField.setDouble(incident.getLatitude());
		longitudeField.setDouble(incident.getLongitude());
		incidentTypeField.setText(incidentType.getType());
		severityLevelField.setText(severityLevel.getSeverityLevel());
		affectedSurfaceField.setDouble(incident.getAffectedSurface());
		pictureLabel.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(incidentCapture.getName(), pictureWidth, pictureHeight)
		));
		descriptionArea.setText(incident.getDescription());

		volunteerFullNameField.setText(user.getLastName().toUpperCase() + " " + user.getFirstName());
		volunteerEmailField.setText(user.getEmail());

		volunteerPictureLabel.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(volunteerPicture.getName(), pictureWidth, pictureHeight)
		));
	}
}
