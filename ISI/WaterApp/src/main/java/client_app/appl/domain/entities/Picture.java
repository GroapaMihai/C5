package client_app.appl.domain.entities;

import java.sql.Blob;
import java.sql.Timestamp;

import client_app.api.annotations.Column;
import client_app.api.annotations.Id;
import client_app.api.annotations.Table;
import client_app.api.database.DBProperties;
import client_app.appl.domain.builders.PictureBuilder;

@Table(name = DBProperties.DATABASE_NAME + ".PICTURES")
public class Picture {
    @Id(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PICTURE")
    private Blob picture;

    @Column(name = "UPLOAD_DATE")
    private Timestamp uploadDate;
    
    public Picture() {
    }

    public Picture(PictureBuilder builder) {
    	this.id = builder.getId();
    	this.name = builder.getName();
    	this.picture = builder.getPicture();
    	this.uploadDate = builder.getUploadDate();
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	
	public Timestamp getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public String toString() {
		return "Picture [id=" + id + ", name=" + name + ", picture=" + picture + ", uploadDate=" + uploadDate + "]";
	}
}
