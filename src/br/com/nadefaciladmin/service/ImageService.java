package br.com.nadefaciladmin.service;

import java.util.List;

import br.com.nadefaciladmin.bean.Image;

public interface ImageService {
	
	public Image getImage(int id);
	
	public Image getImage(String name);
	
	public List<Image> getAllImages();
	
	public Image createImage(Image image);
	
	public Image updateImage(Image image);
	
	public boolean deleteImage(int imageId);
}