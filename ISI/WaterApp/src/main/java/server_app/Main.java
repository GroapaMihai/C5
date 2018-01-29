package server_app;

import client_app.api.database.DBManager;

public class Main {

	public static void main(String[] args) {
		DBManager.registerDriver();
		SensorsManager.getInstance().simulate();
		DBManager.closeConnection();
	}
}
