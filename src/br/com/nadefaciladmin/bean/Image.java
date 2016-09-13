package br.com.nadefaciladmin.bean;

public class Image {
	
	private int id;
	private String name;
	private String serverPath;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getServerPath() {
		return serverPath;
	}
	
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
}