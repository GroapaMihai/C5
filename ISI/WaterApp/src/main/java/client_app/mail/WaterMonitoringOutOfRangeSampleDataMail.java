package client_app.mail;

import client_app.appl.domain.entities.Sample;
import client_app.appl.domain.entities.User;
import client_app.appl.domain.entities.WaterSensor;

public class WaterMonitoringOutOfRangeSampleDataMail extends WaterMonitoringMail {
	public WaterMonitoringOutOfRangeSampleDataMail() {
		subject = "[Water Monitoring Application] Water sensor out of normal range sample values";
	}

	public void setMessageWithDetails(Sample lastSample, WaterSensor waterSensor, User waterSensorOwner) {
		StringBuilder mailMessageBuilder = new StringBuilder();

		mailMessageBuilder.append("Hello " + waterSensorOwner.getFirstName() + " " + waterSensorOwner.getLastName().toUpperCase() + ",\n\n")
			.append("You are receiving this email because the water sensor with the following coordinates:\n")
			.append("\t [lat: " + waterSensor.getLatitude() + ", long: " + waterSensor.getLongitude() + "]\n")
			.append("measured out of normal range values for a sample taken at ")
			.append(lastSample.getSampleTime() + "\n\n")
			.append("This message has informative content, no further action is required.\n\n")
			.append("Regards,\nWater Monitoring Application team");

		message = mailMessageBuilder.toString();
	}
}
