package client_app.appl.domain.builders;

import java.sql.Blob;
import java.sql.Timestamp;

import client_app.appl.domain.entities.Picture;

public class PictureBuilder {
    private Integer id;
    private String name;
    private Blob picture;
    private Timestamp uploadDate;
    
    public PictureBuilder() {
    }

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Blob getPicture() {
		return picture;
	}
    
	public Timestamp getUploadDate() {
		return uploadDate;
	}

    public PictureBuilder withId(Integer id) {
    	this.id = id;
    	
    	return this;
    }

    public PictureBuilder withName(String name) {
    	this.name = name;
    	
    	return this;
    }

    public PictureBuilder withPicture(Blob picture) {
    	this.picture = picture;
    	
    	return this;
    }

    public PictureBuilder withUploadDate(Timestamp uploadDate) {
    	this.uploadDate = uploadDate;
    	
    	return this;
    }

    public Picture build() {
    	return new Picture(this);
    }
}
