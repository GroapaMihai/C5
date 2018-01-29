package client_app.authentication;

public enum PasswordStatus {
	NOT_MATCHING("Entered passwords don't match!"),
	TOO_SHORT("Entered password is too short!"),
	INVALID_SYMBOLS("Entered password contains invalid symbols!"),
	VALID("Success!");
	
	private String message;
	
	PasswordStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
