package client_app.authentication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
	private static PasswordHash instance = null;
	private final String salt = "3e47327f62b74ec718f2c31b30f6ff0d7441f9ae143622173b067ac8d531f6e64c80a50eb497050bf6485f4270e5755904938e667e9a0ab5b2a75301a974648e";
	
	private PasswordHash() {
	}
	
	public static PasswordHash getInstance() {
		if (instance == null) {
			instance = new PasswordHash();
		}
		
		return instance;
	}

	public String get_SHA_512_SecurePassword(String passwordToHash) {
		String generatedPassword = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt.getBytes("UTF-8"));
			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return generatedPassword;
	}	
}