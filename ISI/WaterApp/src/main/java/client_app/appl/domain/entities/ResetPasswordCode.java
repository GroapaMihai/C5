package client_app.appl.domain.entities;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.ResetPasswordCodeBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".RESET_PASSWORD_CODES")
public class ResetPasswordCode {
	@Id(name = "ID")
	private Integer id;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "FK_USER")
	private Integer userId;

	public ResetPasswordCode() {
	}
	
	public ResetPasswordCode(ResetPasswordCodeBuilder builder) {
		this.id = builder.getId();
		this.code = builder.getCode();
		this.userId = builder.getUserId();
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

	@Override
	public String toString() {
		return "ResetPasswordCode [id=" + id + ", code=" + code + ", userId=" + userId + "]";
	}
}
