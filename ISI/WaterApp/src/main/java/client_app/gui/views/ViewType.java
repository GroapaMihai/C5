package client_app.gui.views;

public enum ViewType {
    LOGIN_VIEW("Login"),
    CREATE_ACCOUNT_VIEW("Create account"),
    CHANGE_PASSWORD_VIEW("Change password"),
    AUTHORITY_VIEW("Authority view"),
    VOLUNTEER_VIEW("Volunteer view"),
    WATER_SENSOR_ADD_MODAL("Water sensor add modal"),
    WATER_SENSOR_DISPLAY_MODAL("Water sensor display modal"),
    INCIDENT_ADD_MODAL("Incident add modal"),
    INCIDENT_DISPLAY_MODAL("Incident display modal"),
    CHARTS_MODAL("Charts modal"),
    HIDE("Hide");

    private String viewName;

    ViewType(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}

