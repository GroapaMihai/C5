package client_app.appl.domain.builders;

import client_app.appl.domain.entities.AccountType;

public class AccountTypeBuilder {
	private Integer id;
	private String type;
	
	public AccountTypeBuilder() {
	}

	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public AccountTypeBuilder withId(Integer id) {
		this.id = id;
		
		return this;
	}

	public AccountTypeBuilder withType(String type) {
		this.type = type;
		
		return this;
	}
	
	public AccountType build() {
		return new AccountType(this);
	}
}
