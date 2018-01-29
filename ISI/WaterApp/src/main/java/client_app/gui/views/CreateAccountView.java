package client_app.gui.views;

import net.miginfocom.swing.MigLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;

import client_app.api.annotations.Clear;
import client_app.appl.dao.AccountTypeDao;
import client_app.appl.dao.PictureDao;
import client_app.appl.dao.UserDao;
import client_app.appl.domain.builders.AccountTypeBuilder;
import client_app.appl.domain.builders.PictureBuilder;
import client_app.appl.domain.builders.UserBuilder;
import client_app.appl.domain.entities.AccountType;
import client_app.appl.domain.entities.Picture;
import client_app.appl.domain.entities.User;
import client_app.authentication.PasswordHash;
import client_app.authentication.PasswordStatus;
import client_app.authentication.PasswordValidator;
import client_app.gui.components.DateLabelFormatter;
import client_app.gui.components.Fonts;
import client_app.gui.components.RandomStringGenerator;
import client_app.picture.Icon;
import client_app.picture.ImageBrowseFrame;
import client_app.picture.ImageLoader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Random;

public class CreateAccountView extends View {
	private static final long serialVersionUID = 1L;
	private static CreateAccountView instance = null;
	private final int profilePictureWidth = 150;
	private final int profilePictureHeight = 150;

	private UserDao userDao;
	private AccountTypeDao accountTypeDao;
	private PictureDao pictureDao;

    private JLabel pictureLabel;
    private ImageBrowseFrame imageBrowseFrame;
    private JButton browseButton;

    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel birthDateLabel;
    private JLabel emailLabel;
    private JLabel accountTypeLabel;
    private JLabel passwordLabel;
    private JLabel retypePasswordLabel;

    @Clear
    private JTextField firstNameField;

    @Clear
    private JTextField lastNameField;

    @Clear(defaultValue = "1980-01-01")
    private JDatePickerImpl birthDatePicker;
    
    @Clear
    private JTextField emailField;
    
    @Clear
    private JComboBox<String> accountTypeBox;
    
    @Clear
    private JPasswordField passwordField;
    
    @Clear
    private JPasswordField retypePasswordField;

    private JButton backButton;
    private JButton submitButton;
    private JButton clearButton;

    private CreateAccountView() {
    	viewType = ViewType.CREATE_ACCOUNT_VIEW;
    	width = 650;
    	height = 650;
    	
        setLayout(new MigLayout("al center center"));

        userDao = UserDao.getInstance();
        accountTypeDao = AccountTypeDao.getInstance();
        pictureDao = PictureDao.getInstance();

        initGUIElements();
        addGUIElements();
        addListeners();
    }

    public static CreateAccountView getInstance() {
        if (instance == null) {
        	instance = new CreateAccountView();
        }

        return instance;
    }

    private void initGUIElements() {
        pictureLabel = new JLabel();
        imageBrowseFrame = new ImageBrowseFrame(this);

        browseButton = new JButton("Browse");
        browseButton.setIcon(new ImageIcon(
    		ImageLoader.getInstance().getScaledImageFromDB(Icon.BROWSE_ICON.getIconName(), 25, 25))
		);

        firstNameLabel = new JLabel("First name");
        firstNameLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());
        
		firstNameField = new JTextField(18);
		firstNameField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());

        lastNameLabel = new JLabel("Last name");
        lastNameLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		lastNameField = new JTextField(18);
		lastNameField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		
        birthDateLabel = new JLabel("Birthdate");
        birthDateLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

        UtilDateModel model = new UtilDateModel();
		model.setSelected(true);
        Properties p = new Properties();
        p.put("text.year", "Year");
        p.put("text.month", "Month");
        p.put("text.today", "Today");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		birthDatePicker = new JDatePickerImpl(datePanel, DateLabelFormatter.getInstance());
		
        emailLabel = new JLabel("Email");
        emailLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		emailField = new JTextField(18);
		emailField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());

        accountTypeLabel = new JLabel("Account type");
        accountTypeLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

        accountTypeBox = new JComboBox<String>();
        accountTypeBox.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
        
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		passwordField = new JPasswordField(18);
		passwordField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		
        retypePasswordLabel = new JLabel("Retype password");
        retypePasswordLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		retypePasswordField = new JPasswordField(18);
		retypePasswordField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		
        backButton = new JButton("Back");
        backButton.setIcon(new ImageIcon(
    		ImageLoader.getInstance().getScaledImageFromDB(Icon.BACK_ICON.getIconName(), 25, 25))
		);

        submitButton = new JButton("Submit");
        submitButton.setIcon(new ImageIcon(
    		ImageLoader.getInstance().getScaledImageFromDB(Icon.SUBMIT_ICON.getIconName(), 25, 25))
		);

        clearButton = new JButton("Clear");
        clearButton.setIcon(new ImageIcon(
    		ImageLoader.getInstance().getScaledImageFromDB(Icon.CLEAR_ICON.getIconName(), 25, 25))
		);
    }

    private void addGUIElements() {
        add(pictureLabel, "gapbottom 40px, gapleft 75px, gaptop 40px");
        add(browseButton, "gapbottom 40px, bottom, wrap");

        add(firstNameLabel, "gapbottom 10px, gapleft 75px");
        add(firstNameField, "gapbottom 10px, gapright 75px, pushx, growx, wrap");

        add(lastNameLabel, "gapbottom 10px, gapleft 75px");
        add(lastNameField, "gapbottom 10px, gapright 75px, pushx, growx, wrap");

        add(birthDateLabel, "gapbottom 10px, gapleft 75px");
        add(birthDatePicker, "gapbottom 10px, gapright 75px, pushx, growx, wrap");

        add(emailLabel, "gapbottom 10px, gapleft 75px");
        add(emailField, "gapbottom 10px, gapright 75px, pushx, growx, wrap");

        add(accountTypeLabel, "gapbottom 10px, gapleft 75px");
        add(accountTypeBox, "gapbottom 10px, gapright 75px, pushx, growx, wrap");
        
        add(passwordLabel, "gapbottom 10px, gapleft 75px");
        add(passwordField, "gapbottom 10px, gapright 75px, pushx, growx, wrap");

        add(retypePasswordLabel, "gapbottom 20px, gapleft 75px");
        add(retypePasswordField, "gapbottom 20px, gapright 75px, pushx, growx, wrap");

        add(backButton, "gapbottom 40px, gapleft 75px");

        add(Box.createHorizontalGlue(), "split 5, pushx, growx");
        add(submitButton);
        add(Box.createHorizontalGlue(), "pushx, growx");
        add(clearButton);
        add(Box.createHorizontalGlue(), "gapright 75px, pushx, growx, wrap");
    }

    private void addListeners() {
        browseActionListener();
        backButtonListener();
        saveButtonListener();
        clearButtonListener();
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
                        				profilePictureWidth,
                        				profilePictureHeight
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

    private void backButtonListener() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	imageBrowseFrame.clearFilePath();
            	notifyAllObservers(ViewType.LOGIN_VIEW, null);
            }
        });
    }

    private void saveButtonListener() {
        submitButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
            	File imageFile = null;
            	UserBuilder userBuilder = new UserBuilder();
            	User user;

            	AccountTypeBuilder accountTypeBuilder = new AccountTypeBuilder();
            	AccountType accountType;
            	List<AccountType> matchingAccountTypes;

            	PictureBuilder pictureBuilder = new PictureBuilder();
            	Picture picture = null;

		    	PasswordStatus passwordStatus = PasswordValidator.getInstance()
	    			.validPassword(passwordField.getText(), retypePasswordField.getText()
				);

		    	if(passwordStatus != PasswordStatus.VALID) {
					JOptionPane.showMessageDialog(
						null, 
						passwordStatus.getMessage(),
    					"Error",
    					JOptionPane.ERROR_MESSAGE
					);
					return;
		    	} 

		    	accountType = accountTypeBuilder
		    		.withType(accountTypeBox.getSelectedItem().toString())
		    		.build();
		    	matchingAccountTypes = accountTypeDao.findMatchingEntities(accountType);

		    	if (matchingAccountTypes.isEmpty()) {
		    		return;
		    	}

		    	accountType = matchingAccountTypes.get(0);

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
                    	userBuilder = userBuilder.withPictureId(picture.getId());
                    }
                }

                try {
					userBuilder = userBuilder.
						withBirthdate(DateLabelFormatter.getInstance().extractDateFromDatePicker(birthDatePicker)
					);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

                user = userBuilder
        			.withFirstName(firstNameField.getText())
        			.withLastName(lastNameField.getText())
        			.withEmail(emailField.getText())
        			.withAccountCreationDate(new Timestamp(System.currentTimeMillis()))
        			.withLastReceivedEmailTime(new Timestamp(System.currentTimeMillis()))
        			.withAccountTypeId(accountType.getId())
        			.withPassword(
    					PasswordHash.getInstance().
    					get_SHA_512_SecurePassword(passwordField.getText())
					).
        			build();

                userDao.insert(user);

                JOptionPane.showMessageDialog(
                    null,
                    "User named " + user.getLastName().toUpperCase() +
                    	" " + user.getFirstName() + " created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

                imageBrowseFrame.clearFilePath();
				notifyAllObservers(ViewType.LOGIN_VIEW, null);
            }
        });
    }

    private void clearButtonListener() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	populateView(null);
            }
        });
    }

	@Override
	public void populateView(Object arg) {
		imageBrowseFrame.clearFilePath();
		ViewCleaner.getInstance().clearView(instance);

		pictureLabel.setIcon(new ImageIcon(
    		ImageLoader.getInstance().getScaledImageFromDB(Icon.DEFAULT_PROFILE_ICON.getIconName(), profilePictureWidth, profilePictureHeight))
		);

		List<AccountType> accountTypes = accountTypeDao.findAll();

		for (AccountType accountType : accountTypes) {
			accountTypeBox.addItem(accountType.getType());
		}
	}
}