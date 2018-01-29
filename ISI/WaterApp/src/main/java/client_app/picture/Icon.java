package client_app.picture;

public enum Icon {
	WATER_ICON("WaterIcon.png"),
	LOCKED_ACCOUNT_ICON("LockedAccountIcon.png"),
	DEFAULT_PROFILE_ICON("DefaultProfileIcon.png"),
	CONFIRM_ICON("ConfirmIcon.png"),
	CLEAR_ICON("ClearIcon.png"),
	BACK_ICON("BackIcon.png"),
	SAVE_ICON("SaveIcon.png"),
	SUBMIT_ICON("SubmitIcon.png"),
	CANCEL_ICON("CancelIcon.png"),
	BROWSE_ICON("BrowseIcon.png"),
	CHANGE_PASSWORD_ICON("ChangePasswordIcon.png"),
	LOGOUT_ICON("LogoutIcon.png"),
	GREEN_SENSOR_ICON("SensorGreenIcon.png"),
	RED_SENSOR_ICON("SensorRedIcon.png"),
	GREEN_INCIDENT_ICON("IncidentGreenIcon.png"),
	RED_INCIDENT_ICON("IncidentRedIcon.png"),
	DEFAULT_INCIDENT_ICON("IncidentDefaultIcon.png"),
	VIEW_ICON("ViewIcon.png"),
	DELETE_ICON("DeleteIcon.png"),
	CHART_ICON("ChartIcon.png"),
	NEXT_ICON("NextIcon.png"),
	PREV_ICON("PrevIcon.png");

	private String iconName;
	
	private Icon(String iconName) {
		this.iconName = iconName;
	}
	
	public String getIconName() {
		return iconName;
	}
}
