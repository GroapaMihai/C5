package client_app.appl.domain.entities;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.AccountTypeBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".ACCOUNT_TYPES")
public class AccountType {
	@Id(name = "ID")
	private Integer id;

	@Column(name = "TYPE")
	private String type;

	public AccountType() {
	}

	public AccountType(AccountTypeBuilder accountTypeBuilder) {
		this.id = accountTypeBuilder.getId();
		this.type = accountTypeBuilder.getType();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "AccountType [id=" + id + ", type=" + type + "]";
	}
}
