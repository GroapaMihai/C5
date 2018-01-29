package client_app.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import client_app.api.annotations.Clear;
import client_app.appl.dao.ResetPasswordCodeDao;
import client_app.appl.dao.UserDao;
import client_app.appl.domain.builders.ResetPasswordCodeBuilder;
import client_app.appl.domain.builders.UserBuilder;
import client_app.appl.domain.entities.ResetPasswordCode;
import client_app.appl.domain.entities.User;
import client_app.authentication.PasswordHash;
import client_app.authentication.PasswordStatus;
import client_app.authentication.PasswordValidator;
import client_app.mail.WaterMonitoringMailSender;
import client_app.mail.WaterMonitoringResetPasswordMail;
import client_app.gui.components.Fonts;
import client_app.gui.components.RandomStringGenerator;
import client_app.gui.components.ScaledJTextArea;
import client_app.picture.Icon;
import client_app.picture.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class ChangePasswordView extends View {
	private static final long serialVersionUID = 1L;
	private static ChangePasswordView instance;
	
	private UserDao userDao;
	private ResetPasswordCodeDao resetPasswordCodeDao;

	private JLabel titleLabel;
	private JLabel emailLabel;
	private JLabel codeLabel;
	private JLabel newPasswordLabel;
	private JLabel retypePasswordLabel;
	private JLabel lockLabel; 
	private ScaledJTextArea info;

	@Clear
	private JTextField emailField;

	@Clear
	private JTextField resetCodeField;

	@Clear
	private JPasswordField newPasswordField;

	@Clear
	private JPasswordField retypePasswordField;

	private JButton confirmButton;
	private JButton cancelButton;
	private JButton resetCodeButton;
	
	private ChangePasswordView() {
		viewType = ViewType.CHANGE_PASSWORD_VIEW;
		width = 650;
		height = 500;

		setLayout(new MigLayout("insets 20, align 50% 50%"));

		userDao = UserDao.getInstance();
		resetPasswordCodeDao = ResetPasswordCodeDao.getInstance();
		
		initGUIElements();
		addGUIElements();
		addListeners();
	}
	
	public static ChangePasswordView getInstance() {
		if (instance == null) {
			instance = new ChangePasswordView();
		}
		
		return instance;
	}

	private void initGUIElements() {
		titleLabel = new JLabel("Change Password ", SwingConstants.CENTER);
		titleLabel.setFont(Fonts.SERIF_BOLD_26.getFont());

		lockLabel = new JLabel("", SwingConstants.CENTER);
		lockLabel.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.CHANGE_PASSWORD_ICON.getIconName(), 175, 175)
		));

		emailLabel = new JLabel("Email *");
		emailLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		emailField = new JTextField(18);
		emailField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());

		codeLabel = new JLabel("Reset Code *");
		codeLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		resetCodeField = new JTextField(18);
		resetCodeField.setFont(Fonts.TAHOMA_PLAIN_16.getFont());

		newPasswordLabel = new JLabel("New Password *");
		newPasswordLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		newPasswordField = new JPasswordField(18);
		newPasswordField.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		retypePasswordLabel = new JLabel("Retype Password *");
		retypePasswordLabel.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		retypePasswordField = new JPasswordField(18);
		retypePasswordField.setFont(Fonts.TAHOMA_BOLD_16.getFont());

		StringBuilder sb = new StringBuilder();
        sb.append("Enter the code provided by our server via email, ").
           append("then type your new password (minimum 6 characters). ").
           append("We highly recommend you to choose a strong one.");
		info = new ScaledJTextArea(sb.toString(), 4, 30);

		confirmButton = new JButton("Confirm");
		confirmButton.setHorizontalAlignment(SwingConstants.LEFT);
		confirmButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.CONFIRM_ICON.getIconName(), 25, 25)
		));

		cancelButton = new JButton("Cancel");
		cancelButton.setHorizontalAlignment(SwingConstants.LEFT);
		cancelButton.setIcon(new ImageIcon(
			ImageLoader.getInstance().getScaledImageFromDB(Icon.CANCEL_ICON.getIconName(), 25, 25)
		));
		
		resetCodeButton = new JButton("Email reset code to me");
	}
	
	private void addGUIElements() {
		add(Box.createVerticalStrut(15), "wrap");
		add(titleLabel, "spanx2, center, wrap");
		add(Box.createVerticalStrut(15), "wrap");
		add(Box.createVerticalGlue(), "pushy, growy, wrap");
		add(lockLabel, "spany11, center");
		add(emailLabel, "wrap");
		add(emailField, "split2, pushx, growx");
		add(resetCodeButton, "wrap");
		add(Box.createVerticalStrut(10), "wrap");
		add(codeLabel, "wrap");
		add(resetCodeField, "wrap, pushx, growx");
		add(Box.createVerticalStrut(10), "wrap");
		add(newPasswordLabel, "wrap");
		add(newPasswordField, "wrap, pushx, growx");
		add(Box.createVerticalStrut(10), "wrap");
		add(retypePasswordLabel, "wrap");
		add(retypePasswordField, "wrap, pushx, growx");
		add(Box.createVerticalStrut(10), "wrap");
		add(info, "center");
		
		Box buttonsBox = Box.createHorizontalBox();
		buttonsBox.add(confirmButton);
		buttonsBox.add(Box.createHorizontalStrut(30));
		buttonsBox.add(cancelButton);
		add(buttonsBox, "center, wrap");
		add(Box.createVerticalGlue(), "pushy, growy, wrap");
	}

	private void addListeners() {
		cancelButtonListener();
		resetCodeButtonListener();
		confirmButtonListener();
	}

	private void cancelButtonListener() {
		cancelButton.addActionListener (new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
		    	notifyAllObservers(ViewType.LOGIN_VIEW, null);
		    }
		});
	}

	private void resetCodeButtonListener() {
		resetCodeButton.addActionListener (new ActionListener () {
            @Override
		    public void actionPerformed(ActionEvent e) {
		    	ResetPasswordCodeBuilder resetPasswordCodeBuilder = new ResetPasswordCodeBuilder();
		    	ResetPasswordCode resetPasswordCode;
		    	List<ResetPasswordCode> matchingResetPasswordCodes;

		    	UserBuilder userBuilder = new UserBuilder();
		    	User user = userBuilder
	    			.withEmail(emailField.getText())
	    			.build();
		    	List<User> matchingVolunteers = userDao.findMatchingEntities(user);
		    	WaterMonitoringResetPasswordMail mail = new WaterMonitoringResetPasswordMail();

		    	if (matchingVolunteers.isEmpty()) {
	                JOptionPane.showMessageDialog(
                        null,
                        "No accounts associated with typed email!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
	                return;
		    	} else {
		    		user = matchingVolunteers.get(0);

		    		resetPasswordCode = resetPasswordCodeBuilder
		    			.withUserId(user.getId())
		    			.build();
		    		matchingResetPasswordCodes = resetPasswordCodeDao.findMatchingEntities(resetPasswordCode);

		    		// sterg vechiul cod de resetare al acestui cont (daca exista)
		    		if (!matchingResetPasswordCodes.isEmpty()) {
		    			resetPasswordCode = matchingResetPasswordCodes.get(0);
		    			resetPasswordCodeDao.delete(resetPasswordCode);
		    		}

		    		// creez un nou cod de resetare pentru acest cont
		    		resetPasswordCode = resetPasswordCodeBuilder
	    				.withCode(RandomStringGenerator.generateRandomStringOfLength(10))
	    				.withUserId(user.getId())
	    				.build();
		    		resetPasswordCodeDao.insert(resetPasswordCode);
		    		
		    		mail = new WaterMonitoringResetPasswordMail();
		    		mail.setMessageWithDetails(
	    				user.getFirstName(),
	    				user.getLastName(),
	    				resetPasswordCode.getCode()
    				);
		    		
		    		if (!WaterMonitoringMailSender.getInstance().send(user.getEmail(), mail)) {
		    			JOptionPane.showMessageDialog(
	    					null,
	    					"We couldn't provide you a password reset code. Try again later!",
	    					"Error",
	    					JOptionPane.ERROR_MESSAGE
	    				);
		    		} else {
		                JOptionPane.showMessageDialog(
	                        null,
	                        "Reset password code successfully sent to " + user.getEmail() + "!",
	                        "Success",
	                        JOptionPane.INFORMATION_MESSAGE
	                    );	
		    		}
		    	}
		    }
		});
	}

	private void confirmButtonListener() {
		confirmButton.addActionListener (new ActionListener () {
            @Override
			public void actionPerformed(ActionEvent e) {
		    	User user;

		    	ResetPasswordCodeBuilder resetPasswordCodeBuilder = new ResetPasswordCodeBuilder();
		    	ResetPasswordCode resetPasswordCode = resetPasswordCodeBuilder
	    			.withCode(resetCodeField.getText())
	    			.build();
		    	List<ResetPasswordCode> matchingResetPasswordCodes = resetPasswordCodeDao
	    			.findMatchingEntities(resetPasswordCode);

		    	if (matchingResetPasswordCodes.isEmpty()) {
					JOptionPane.showMessageDialog(
						null, 
						"Wrong reset password code!",
    					"Error",
    					JOptionPane.ERROR_MESSAGE
					);
					return;
		    	}

		    	PasswordStatus passwordStatus = PasswordValidator.getInstance()
	    			.validPassword(newPasswordField.getText(), retypePasswordField.getText()
				);

		    	if(passwordStatus != PasswordStatus.VALID) {
					JOptionPane.showMessageDialog(
						null, 
						passwordStatus.getMessage(),
    					"Error",
    					JOptionPane.ERROR_MESSAGE
					);
					return;
		    	} else {
		    		resetPasswordCode = matchingResetPasswordCodes.get(0);
		    		user = userDao.findById(resetPasswordCode.getUserId());
		    		user.setPassword(
	    				PasswordHash.getInstance()
	    				.get_SHA_512_SecurePassword(newPasswordField.getText())
    				);
					userDao.update(user);

		    		resetPasswordCodeDao.delete(resetPasswordCode);

					JOptionPane.showMessageDialog(
						null, 
						passwordStatus.getMessage(),
    					"Success",
    					JOptionPane.INFORMATION_MESSAGE
					);

					notifyAllObservers(ViewType.LOGIN_VIEW, null);
		    	}
		    }
		});
	}

	@Override
	public void populateView(Object arg) {
		ViewCleaner.getInstance().clearView(instance);
	}
}