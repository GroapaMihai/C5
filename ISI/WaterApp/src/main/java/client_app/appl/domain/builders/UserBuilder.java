package client_app.appl.domain.builders;

import java.sql.Timestamp;

import client_app.appl.domain.entities.User;

public class UserBuilder {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Timestamp birthdate;
    private Integer pictureId;
    private Timestamp accountCreationDate;
    private Timestamp lastReceivedEmailTime;
    private Integer accountTypeId;

    public UserBuilder() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Timestamp getBirthdate() {
		return birthdate;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public Timestamp getAccountCreationDate() {
		return accountCreationDate;
	}

	public Timestamp getLastReceivedEmailTime() {
		return lastReceivedEmailTime;
	}

	public Integer getAccountTypeId() {
		return accountTypeId;
	}

	public UserBuilder withId(Integer id) {
        this.id = id;

        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;

        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;

        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;

        return this;
    }
    
    public UserBuilder withPassword(String password) {
        this.password = password;

        return this;
    }
    
    public UserBuilder withBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;

        return this;
    }
    
    public UserBuilder withPictureId(Integer pictureId) {
        this.pictureId = pictureId;

        return this;
    }
    
    public UserBuilder withAccountCreationDate(Timestamp accountCreationDate) {
        this.accountCreationDate = accountCreationDate;

        return this;
    }

    public UserBuilder withLastReceivedEmailTime(Timestamp lastReceivedEmailTime) {
    	this.lastReceivedEmailTime = lastReceivedEmailTime;

    	return this;
    }

    public UserBuilder withAccountTypeId(Integer accountTypeId) {
    	this.accountTypeId = accountTypeId;
    	
    	return this;
    }

    public User build() {
        return new User(this);
    }
}
