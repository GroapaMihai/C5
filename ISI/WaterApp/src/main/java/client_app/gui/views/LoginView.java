package client_app.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import client_app.api.annotations.Clear;
import client_app.appl.dao.AccountTypeDao;
import client_app.appl.dao.UserDao;
import client_app.appl.domain.builders.UserBuilder;
import client_app.appl.domain.entities.AccountType;
import client_app.appl.domain.entities.User;
import client_app.authentication.PasswordHash;
import client_app.gui.components.AccountTypeEnum;
import client_app.gui.components.Fonts;
import client_app.picture.Icon;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class LoginView extends View {
	private static final long serialVersionUID = 1L;
	private static LoginView instance;

	private UserDao userDao;
	private AccountTypeDao accountTypeDao;

	private JPanel inputFieldsPanel;
	private JLabel titleLabel;
	private JLabel lockedAccountLabel;
	private JLabel messageLabel;
	private JLabel emailLabel;
	private JLabel passwordLabel;

	@Clear
	private JTextField emailField;

	@Clear
	private JPasswordField passwordField;

	private JButton changePasswordButton;
	private JButton loginButton;
	private JButton clearButton;
	private JButton createAccountButton;
	
	private LoginView() {
		viewType = ViewType.LOGIN_VIEW;
		width = 625;
		height = 450;

		setLayout(new MigLayout("insets 20, align 50% 50%"));

		userDao = UserDao.getInstance();
		accountTypeDao = AccountTypeDao.getInstance();

		initGUIElements();
		addGUIElements();
		addListeners();
	}
	
	public static LoginView getInstance() {
		if (instance == null) {
			instance = new LoginView();
		}
		
		return instance;
	}

	private void initGUIElements() {
		titleLabel = new JLabel("Water Monitoring Application", SwingConstants.CENTER);
		titleLabel.setFont(Fonts.SERIF_BOLD_26.getFont());

		lockedAccountLabel = new JLabel();
		lockedAccountLabel.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.LOCKED_ACCOUNT_ICON.getIconName(), 175, 175)
		));

		messageLabel = new JLabel("Enter your email and password:");
		messageLabel.setFont(Fonts.TAHOMA_BOLD_18.getFont());

		emailLabel = new JLabel("Email       ");
		emailLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		emailField = new JTextField(18);
		emailField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());

		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		passwordField = new JPasswordField(18);
		passwordField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());
		
		changePasswordButton = new JButton("Change password");
		changePasswordButton.setFont(Fonts.TAHOMA_BOLD_16.getFont());
		
		loginButton = new JButton("Login");
		loginButton.setHorizontalAlignment(SwingConstants.LEFT);
		loginButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.CONFIRM_ICON.getIconName(), 25, 25)
		));

		clearButton = new JButton("Clear");
		clearButton.setHorizontalAlignment(SwingConstants.LEFT);
		clearButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.CLEAR_ICON.getIconName(), 25, 25)
		));
		
		createAccountButton = new JButton("Not a member? Join now for free.");
		createAccountButton.setFont(Fonts.TAHOMA_ITALIC_16.getFont());
		
		inputFieldsPanel = new JPanel();
		inputFieldsPanel.setLayout(new MigLayout());
	}

	private void addGUIElements() {
		add(titleLabel, "gaptop 20px, gapbottom 15px, spanx3, center, wrap");
		add(Box.createVerticalGlue(), "pushy, growy, wrap");
		
		inputFieldsPanel.add(messageLabel, "center, wrap");
		inputFieldsPanel.add(Box.createVerticalStrut(20), "wrap");
		inputFieldsPanel.add(emailLabel, "split3");
		inputFieldsPanel.add(Box.createHorizontalStrut(10));
		inputFieldsPanel.add(emailField, "pushx, growx, wrap");
		inputFieldsPanel.add(Box.createVerticalStrut(15), "wrap");
		inputFieldsPanel.add(passwordLabel, "split3");
		inputFieldsPanel.add(Box.createHorizontalStrut(10));
		inputFieldsPanel.add(passwordField, "pushx, growx, wrap");

		add(lockedAccountLabel, "gapleft 20px, center");
		add(Box.createHorizontalStrut(15));
		add(inputFieldsPanel, "gapright 20px, pushx, growx, wrap");
		
		add(Box.createVerticalStrut(5), "wrap");
		add(changePasswordButton, "gapleft 20px, center");

		add(loginButton, "skip, center, split3");
		add(Box.createHorizontalStrut(20));
		add(clearButton, "wrap");
		
		add(createAccountButton, "spanx3, center, gaptop 20px");
		add(Box.createVerticalGlue(), "pushy, growy, wrap");
	}
	
	private void addListeners() {
		loginButtonListener();
		resetButtonListener();
		createAccountButtonListener();
		changePasswordButtonListener();
	}

	private void loginButtonListener() {
		loginButton.addActionListener(new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
		    	UserBuilder userBuilder = new UserBuilder();
		    	User user;
		    	List<User> matchingUsers;
		    	AccountType accountType;

		    	String typedEmail = emailField.getText();
		    	String hashedTypedPassword = PasswordHash.getInstance()
	    			.get_SHA_512_SecurePassword(passwordField.getText()
				);

		    	userBuilder = userBuilder
	    			.withEmail(typedEmail)
	    			.withPassword(hashedTypedPassword);
		    	user = userBuilder.build();
		    	matchingUsers = userDao.findMatchingEntities(user);
		    			
		    	if (matchingUsers.isEmpty()) {
		    		JOptionPane.showMessageDialog(
	    				null,
	    				"Wrong email or password!",
    					"Error", 
    					JOptionPane.ERROR_MESSAGE
					);
		    	} else {
		    		user = matchingUsers.get(0);
		    		accountType = accountTypeDao.findById(user.getAccountTypeId());

		    		if (accountType == null) {
		    			return;
		    		}

		    		JOptionPane.showMessageDialog(
	    				null,
	    				"You have successfully logged in!",
    					"Success", 
    					JOptionPane.INFORMATION_MESSAGE
					);

		    		if (accountType.getType().equals(AccountTypeEnum.AUTHORITY.getType())) {
		    			notifyAllObservers(ViewType.AUTHORITY_VIEW, user);
		    		} else if (accountType.getType().equals(AccountTypeEnum.VOLUNTEER.getType())) {
		    			notifyAllObservers(ViewType.VOLUNTEER_VIEW, user);
		    		}
		    	}
		    }
		});
	}

	private void resetButtonListener() {
		clearButton.addActionListener(new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
		    	ViewCleaner.getInstance().clearView(instance);
		    }
		});
	}

	private void createAccountButtonListener() {
		createAccountButton.addActionListener(new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
		    	notifyAllObservers(ViewType.CREATE_ACCOUNT_VIEW, null);
		    }
		});
	}

	private void changePasswordButtonListener() {
		changePasswordButton.addActionListener(new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
		    	notifyAllObservers(ViewType.CHANGE_PASSWORD_VIEW, null);
		    }
		});
	}

	@Override
	public void populateView(Object arg) {
		ViewCleaner.getInstance().clearView(instance);
	}
}