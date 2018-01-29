package client_app.appl.domain.entities;

import java.sql.Timestamp;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.UserBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".USERS")
public class User {
    @Id(name = "ID")
    private Integer id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "BIRTHDATE")
    private Timestamp birthdate;
    
    @Column(name = "FK_PICTURE")
    private Integer pictureId;

    @Column(name = "ACCOUNT_CREATION_DATE")
    private Timestamp accountCreationDate;
    
    @Column(name = "LAST_RECV_EMAIL")
    private Timestamp lastReceivedEmailTime;
    
    @Column(name = "FK_ACCOUNT_TYPE")
    private Integer accountTypeId;

    public User() {
    }

    public User(UserBuilder userBuilder) {
        id = userBuilder.getId();
        firstName = userBuilder.getFirstName();
        lastName = userBuilder.getLastName();
        email = userBuilder.getEmail();
        password = userBuilder.getPassword();
        birthdate = userBuilder.getBirthdate();
        pictureId = userBuilder.getPictureId();
        accountCreationDate = userBuilder.getAccountCreationDate();
        lastReceivedEmailTime = userBuilder.getLastReceivedEmailTime();
        accountTypeId = userBuilder.getAccountTypeId();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Timestamp birthdate) {
		this.birthdate = birthdate;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public Timestamp getAccountCreationDate() {
		return accountCreationDate;
	}

	public void setAccountCreationDate(Timestamp accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}

	public Timestamp getLastReceivedEmailTime() {
		return lastReceivedEmailTime;
	}

	public void setLastReceivedEmailTime(Timestamp lastReceivedEmailTime) {
		this.lastReceivedEmailTime = lastReceivedEmailTime;
	}

	public Integer getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Integer accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", birthdate=" + birthdate + ", pictureId=" + pictureId
				+ ", accountCreationDate=" + accountCreationDate + ", lastReceivedEmailTime=" + lastReceivedEmailTime
				+ ", accountTypeId=" + accountTypeId + "]";
	}
}
