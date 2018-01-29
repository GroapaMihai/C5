package client_app.mail;

public class WaterMonitoringResetPasswordMail extends WaterMonitoringMail {
	public WaterMonitoringResetPasswordMail() {
		subject = "[Water Monitoring Application] Password change request";
	}

	public void setMessageWithDetails(String firstName, String lastName, String resetPasswordCode) {
		StringBuilder mailMessageBuilder = new StringBuilder();

		mailMessageBuilder.append("Hello " + firstName + " " + lastName.toUpperCase() + ",\n\n")
			.append("You are receiving this email because we received a password reset ")
			.append("request for your account.\nThe reset code can be found below.\n")
			.append("\t" + resetPasswordCode + "\n\n")
			.append("If you did not request a password reset, no further action is required.\n\n")
			.append("Regards,\nWater Monitoring Application team");
		
		message = mailMessageBuilder.toString();
	}
}