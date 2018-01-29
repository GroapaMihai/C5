package client_app.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import client_app.api.annotations.Clear;
import client_app.appl.dao.IncidentDao;
import client_app.appl.dao.IncidentTypeDao;
import client_app.appl.dao.PictureDao;
import client_app.appl.dao.SeverityLevelDao;
import client_app.appl.domain.builders.IncidentBuilder;
import client_app.appl.domain.builders.IncidentTypeBuilder;
import client_app.appl.domain.builders.PictureBuilder;
import client_app.appl.domain.builders.SeverityLevelBuilder;
import client_app.appl.domain.entities.Incident;
import client_app.appl.domain.entities.IncidentType;
import client_app.appl.domain.entities.Picture;
import client_app.appl.domain.entities.SeverityLevel;
import client_app.gui.components.Fonts;
import client_app.gui.components.JNumberTextField;
import client_app.gui.components.RandomStringGenerator;
import client_app.picture.Icon;
import client_app.picture.ImageBrowseFrame;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class IncidentAddModal extends View {
	private static final long serialVersionUID = 1L;
	private static IncidentAddModal instance = null;
	private final int pictureWidth = 150;
	private final int pictureHeight = 150;

	private JPanel scrollPanel;
	private JScrollPane scrollPane;

	private JLabel incidentTypeLabel;
	private JLabel severityLevelLabel;
	private JLabel affectedSurfaceLabel;
	private JLabel descriptionLabel;

    @Clear
    private JComboBox<String> incidentTypeBox;

    @Clear
    private JComboBox<String> severityLevelBox;

    @Clear(defaultValue = "0")
	private JNumberTextField affectedSurfaceField;

    private JLabel pictureLabel;
    private JLabel captureLabel;
    private ImageBrowseFrame imageBrowseFrame;
    private JButton browseButton;

    @Clear(defaultValue = "")
    private JTextArea descriptionArea;
	private JScrollPane descriptionScrollPane;

    private IncidentTypeDao incidentTypeDao;
    private SeverityLevelDao severityLevelDao;
    private IncidentDao incidentDao;
	private PictureDao pictureDao;

	private IncidentBuilder incidentBuilder;

	private JButton saveAndCloseButton;

	private IncidentAddModal() {
		viewType = ViewType.INCIDENT_ADD_MODAL;
		width = 550;
		height = 450;

		setLayout(new MigLayout("insets 20, align 50% 50%"));

		incidentTypeDao = IncidentTypeDao.getInstance();
		severityLevelDao = SeverityLevelDao.getInstance();
		incidentDao = IncidentDao.getInstance();
		pictureDao = PictureDao.getInstance();
		
		initGUIElements();
		addGUIElements();
		addListeners();
	}
	
	public static IncidentAddModal getInstance() {
		if (instance == null) {
			instance = new IncidentAddModal();
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

		incidentTypeLabel = new JLabel("Incident type");
		incidentTypeLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		incidentTypeBox = new JComboBox<String>();
		incidentTypeBox.setFont(Fonts.TAHOMA_PLAIN_16.getFont());

		severityLevelLabel = new JLabel("Severity level");
		severityLevelLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		severityLevelBox = new JComboBox<String>();
		severityLevelBox.setFont(Fonts.TAHOMA_PLAIN_16.getFont());

		affectedSurfaceLabel = new JLabel("Affected surface");
		affectedSurfaceLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		affectedSurfaceField = new JNumberTextField(3, JNumberTextField.DECIMAL);
		affectedSurfaceField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		affectedSurfaceField.setAllowNegative(false);
		affectedSurfaceField.setPrecision(2);

        pictureLabel = new JLabel();
        captureLabel = new JLabel("Incident capture");
        captureLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());
        imageBrowseFrame = new ImageBrowseFrame(this);

        browseButton = new JButton("Browse");
        browseButton.setIcon(new ImageIcon(
    		ImageLoader.getInstance().getScaledImageFromDB(Icon.BROWSE_ICON.getIconName(), 25, 25))
		);

		descriptionLabel = new JLabel("Incident description");
		descriptionLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());
		
		descriptionArea = new JTextArea(5, 20);
		descriptionScrollPane = new JScrollPane(descriptionArea);

		saveAndCloseButton = new JButton("Save and Close");
		saveAndCloseButton.setHorizontalAlignment(SwingConstants.LEFT);
		saveAndCloseButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.SAVE_ICON.getIconName(), 25, 25)
		));
	}

	private void addGUIElements() {
		scrollPanel.add(incidentTypeLabel, "gapbottom 10px, gapleft 10px");
		scrollPanel.add(incidentTypeBox, "gapbottom 10px, gapright 10px, pushx, growx, wrap");

		scrollPanel.add(severityLevelLabel, "gapbottom 10px, gapleft 10px");
		scrollPanel.add(severityLevelBox, "gapbottom 10px, gapright 10px, pushx, growx, wrap");

		scrollPanel.add(affectedSurfaceLabel, "gapbottom 10px, gapleft 10px");
		scrollPanel.add(affectedSurfaceField, "gapbottom 10px, gapright 10px, pushx, growx, wrap");

		scrollPanel.add(pictureLabel, "gapleft 10px, wrap");

		scrollPanel.add(captureLabel, "gapbottom 10px, gapleft 10px");
		scrollPanel.add(browseButton, "gapbottom 10px, bottom, wrap");

		scrollPanel.add(descriptionLabel, "gapbottom 10px, gapleft 10px");
		scrollPanel.add(descriptionScrollPane, "gapbottom 10px, gapright 10px, pushx, growx, wrap");

		scrollPanel.add(saveAndCloseButton, "gapbottom 10px, gapleft 10px, spanx2, center");
 
		scrollPane.setViewportView(scrollPanel);
        add(scrollPane, "push, grow");
	}

	private void addListeners() {
		browseActionListener();
		saveAndCloseButtonListener();
	}
	
	private void browseActionListener() {
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        imageBrowseFrame.browse();
                        if(imageBrowseFrame.selectedFileIsImage()) {
                            pictureLabel.setIcon(
                            	new ImageIcon(
                        			ImageLoader.getInstance().getScaledImageFromLocal(
                        				imageBrowseFrame.getFilePath(),
                        				pictureWidth,
                        				pictureHeight
                					)
                                )
                            );
                        } else {
                            imageBrowseFrame.clearFilePath();
                        }
                    }
                });
            }
        });
    }

	private void saveAndCloseButtonListener() {
		saveAndCloseButton.addActionListener(new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
		    	if (incidentBuilder == null) {
		    		return;
		    	}

		    	File imageFile = null;
		    	
		    	incidentDao = IncidentDao.getInstance();
		    	Incident incident;

		    	IncidentTypeBuilder incidentTypeBuilder = new IncidentTypeBuilder();
		    	IncidentType incidentType;
		    	List<IncidentType> matchingIncidentTypes;
		    	
		    	SeverityLevelBuilder severityLevelBuilder = new SeverityLevelBuilder();
		    	SeverityLevel severityLevel;
		    	List<SeverityLevel> matchingSeverityLevels;

            	PictureBuilder pictureBuilder = new PictureBuilder();
            	Picture picture = null;

		    	incidentType = incidentTypeBuilder
	    			.withType(incidentTypeBox.getSelectedItem().toString())
	    			.build();

		    	matchingIncidentTypes = incidentTypeDao.findMatchingEntities(incidentType);

		    	if (matchingIncidentTypes.isEmpty()) {
		    		return;
		    	}
		    	
		    	incidentType = matchingIncidentTypes.get(0);
		    	
		    	severityLevel = severityLevelBuilder
	    			.withSeverityLevel(severityLevelBox.getSelectedItem().toString())
	    			.build();
		    	
		    	matchingSeverityLevels = severityLevelDao.findMatchingEntities(severityLevel);
		    	
		    	if (matchingSeverityLevels.isEmpty()) {
		    		return;
		    	}

		    	severityLevel = matchingSeverityLevels.get(0);

		    	if (imageBrowseFrame.selectedFileIsImage()) {
		    		if (imageBrowseFrame.getFilePath() != null) {
                        imageFile = new File(imageBrowseFrame.getFilePath());
                    }
                }

                if (imageFile != null) {
                    Random random = new Random();
                    String randomString = RandomStringGenerator.generateRandomStringOfLength(random.nextInt(50));

                    picture = pictureBuilder
                		.withName(randomString + '_' + imageFile.getName())
                		.withPicture(ImageLoader.getInstance().getBlobOfFile(imageFile))
                		.withUploadDate(new Timestamp(System.currentTimeMillis()))
                		.build();

                    picture = pictureDao.insert(picture);

                    if (picture != null) {
                    	incidentBuilder = incidentBuilder.withPictureId(picture.getId());
                    }
                }

		    	incident = incidentBuilder
	    			.withReportingDate(new Timestamp(System.currentTimeMillis()))
	    			.withAffectedSurface(affectedSurfaceField.getDouble())
	    			.withDescription(descriptionArea.getText())
	    			.withIncidentTypeId(incidentType.getId())
	    			.withSeverityLevelId(severityLevel.getId())
	    			.build();

		    	incidentBuilder = null;
		    	incidentDao.insert(incident);

	    		JOptionPane.showMessageDialog(
    				null,
    				"Incident saved successfully!",
					"Success", 
					JOptionPane.INFORMATION_MESSAGE
				);

	    		notifyAllObservers(ViewType.HIDE, null);
		    }
		});
	}

	@Override
	public void populateView(Object arg) {
		imageBrowseFrame.clearFilePath();
		ViewCleaner.getInstance().clearView(instance);

		pictureLabel.setIcon(
			new ImageIcon(ImageLoader.getInstance().getScaledImageFromDB(
				Icon.DEFAULT_INCIDENT_ICON.getIconName(),
				pictureWidth,
				pictureHeight)
			)
		);

		if (arg instanceof IncidentBuilder) {
			incidentBuilder = (IncidentBuilder) arg;
		}

		List<IncidentType> incidentTypes = incidentTypeDao.findAll();
		List<SeverityLevel> severityLevels = severityLevelDao.findAll();

		for (IncidentType incidentType : incidentTypes) {
			incidentTypeBox.addItem(incidentType.getType());
		}

		for (SeverityLevel severityLevel : severityLevels) {
			severityLevelBox.addItem(severityLevel.getSeverityLevel());
		}
	}
}
