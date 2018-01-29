package client_app.authentication;

public class PasswordValidator {
	private static PasswordValidator instance = null;

	private final int minPasswordLength = 6;
	private final char[] allowedSymbols = {
		'!', '@', '#', '$', '%', '^',
		'&', '*', '(', ')', '_', '[',
		']', '{', '}', '<', '>'
	};
	
	private PasswordValidator() {
	}
	
	public static PasswordValidator getInstance() {
		if (instance == null) {
			instance = new PasswordValidator();
		}
		
		return instance;
	}
	
	private boolean passwordsMatch(String password, String retypedPassword) {
		return password.equals(retypedPassword);
	}

	private boolean longEnoughPassword(String password) {
		return password.length() >= minPasswordLength;
	}

	private boolean allowedSymbol(char symbol) {
		for (char c : allowedSymbols) {
			if (c == symbol) {
				return true;
			}
		}

		return false;
	}

	private boolean allSymbolsAllowed(String password) {
		for(char symbol : password.toCharArray()) {
			if (!(Character.isLetter(symbol) || Character.isDigit(symbol) || allowedSymbol(symbol))) {
				return false;
			}
		}

		return true;
	}
	
	public PasswordStatus validPassword(String password, String retypedPassword) {
		if (!passwordsMatch(password, retypedPassword)) {
			return PasswordStatus.NOT_MATCHING;
		}

		if (!longEnoughPassword(password)) {
			return PasswordStatus.TOO_SHORT;
		}

		if (!allSymbolsAllowed(password)) {
			return PasswordStatus.INVALID_SYMBOLS;
		}

		return PasswordStatus.VALID;
	}
}
