package client_app.gui.components;

public enum AccountTypeEnum {
	AUTHORITY("Authority"),
	VOLUNTEER("Volunteer");
	private String type;
	
	private AccountTypeEnum(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
