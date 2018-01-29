package client_app.main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import client_app.api.database.DBManager;
import client_app.picture.Icon;
import client_app.picture.ImageLoader;
import client_app.gui.interfaces.Observer;
import client_app.gui.views.ChangePasswordView;
import client_app.gui.views.CreateAccountView;
import client_app.gui.views.LoginView;
import client_app.gui.views.View;
import client_app.gui.views.ViewType;
import client_app.gui.views.VolunteerView;
import client_app.gui.views.AuthorityView;

public class WaterMonitoringWindow extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;

    private JPanel basePanel;
    private CardLayout cardLayout;

    private LoginView loginView;
    private CreateAccountView createAccountView;
    private ChangePasswordView changePasswordView;
    private AuthorityView authorityView;
    private VolunteerView volunteerView;

    public WaterMonitoringWindow() {
        cardLayout = new CardLayout();
        basePanel = new JPanel();
        basePanel.setLayout(cardLayout);

        loginView = LoginView.getInstance();
        loginView.attachObserver(this);
        basePanel.add(loginView, loginView.getViewName());

        createAccountView = CreateAccountView.getInstance();
        createAccountView.attachObserver(this);
        basePanel.add(createAccountView, createAccountView.getViewName());

        authorityView = AuthorityView.getInstance();
        authorityView.attachObserver(this);
        authorityView.attachObserver(WaterMonitoringForm.getInstance());
        basePanel.add(authorityView, authorityView.getViewName());

        volunteerView = VolunteerView.getInstance();
        volunteerView.attachObserver(this);
        volunteerView.attachObserver(WaterMonitoringForm.getInstance());
        basePanel.add(volunteerView, volunteerView.getViewName());

        changePasswordView = ChangePasswordView.getInstance();
        changePasswordView.attachObserver(this);
        basePanel.add(changePasswordView, changePasswordView.getViewName());

        setVisible(true);
        setTitle("Water Monitoring Application");
        setIconImage(
			ImageLoader.getInstance().getImageFromDB(Icon.WATER_ICON.getIconName())
		);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(basePanel);

	    addWindowListener(new WindowAdapter() {
	    	@Override
	    	public void windowClosing(WindowEvent windowEvent) {
	    		super.windowClosing(windowEvent);
	    		authorityView.disposeMapOnClose();
	    		DBManager.closeConnection();
	    	}
	    });
	    
	    displayView(loginView);
    }

    private void displayView(View view) {
    	Dimension viewDimension = view.getViewDimension();
    	
    	if (viewDimension.width == Integer.MAX_VALUE || viewDimension.height == Integer.MAX_VALUE) {
    		setExtendedState(JFrame.MAXIMIZED_BOTH);
    	} else {
        	setMinimumSize(viewDimension);
        	setSize(viewDimension);
    	}
    	
    	setLocationRelativeTo(null);
    	cardLayout.show(basePanel, view.getViewName());
    }
    
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    	EventQueue.invokeLater(new Runnable() {
    		@Override
    		public void run() {
    			try {
    				DBManager.registerDriver();
    				new WaterMonitoringWindow();
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	});
    }

	@Override
	public void update(ViewType viewType, Object arg) {
		View view;

        switch (viewType) {
	        case LOGIN_VIEW: view = loginView; break;
	        case CREATE_ACCOUNT_VIEW: view = createAccountView; break;
	        case CHANGE_PASSWORD_VIEW: view = changePasswordView; break;
	        case AUTHORITY_VIEW: view = authorityView; break;
	        case VOLUNTEER_VIEW: view = volunteerView; break;
	        default: view = null; break;
        }

        if (view != null) {
	        view.populateView(arg);
	        displayView(view);
        }
	}
}