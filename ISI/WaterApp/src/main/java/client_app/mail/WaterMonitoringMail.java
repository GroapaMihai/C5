package client_app.mail;

public abstract class WaterMonitoringMail {
	protected String subject;
	protected String message;

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}
}
