package br.com.nadefaciladmin.bean;

import java.sql.Date;

public class Hint {
	
	private int id;
	private String title;
	private String description;
	private String shortDescription;
	private Image image;
	private Date date;
	private int pageCode;

	public Hint() {
		this.image = new Image();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int code) {
		this.id = code;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getPageCode() {
		return pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
}