package client_app.appl.domain.builders;

import client_app.appl.domain.entities.ResetPasswordCode;

public class ResetPasswordCodeBuilder {
	private Integer id;
	private String code;
	private Integer userId;
	
	public ResetPasswordCodeBuilder() {
	}

	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public Integer getUserId() {
		return userId;
	}
	
	public ResetPasswordCodeBuilder withId(Integer id) {
		this.id = id;
		
		return this;
	}
	
	public ResetPasswordCodeBuilder withCode(String code) {
		this.code = code;
		
		return this;
	}
	
	public ResetPasswordCodeBuilder withUserId(Integer userId) {
		this.userId = userId;
		
		return this;
	}
	
	public ResetPasswordCode build() {
		return new ResetPasswordCode(this);
	}
}
