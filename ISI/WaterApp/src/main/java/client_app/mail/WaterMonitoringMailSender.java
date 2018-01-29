package client_app.mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
 
public class WaterMonitoringMailSender {
	private static WaterMonitoringMailSender instance = null;
    private static final String username = "gerogesorosheadquarters@gmail.com";
    private static final String password = "AaBbCc123";
    private static final String host = "smtp.gmail.com";
    private static final String port = "465";

    private WaterMonitoringMailSender() {
    }

    public static WaterMonitoringMailSender getInstance() {
    	if (instance == null) {
    		instance = new WaterMonitoringMailSender();
    	}
    	
    	return instance;
    }

    public boolean send(String destinationAddress, WaterMonitoringMail mail) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationAddress));
			message.setSubject(mail.getSubject());
			message.setText(mail.getMessage());
			Transport.send(message);
		} catch (MessagingException e) {
			return false;
		}
		
		return true;
	}
}