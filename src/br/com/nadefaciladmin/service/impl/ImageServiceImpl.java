package br.com.nadefaciladmin.service.impl;

import java.util.List;

import com.google.inject.Inject;

import br.com.nadefaciladmin.bean.Image;
import br.com.nadefaciladmin.mapper.ImageMapper;
import br.com.nadefaciladmin.service.ImageService;

public class ImageServiceImpl implements ImageService {

	private ImageMapper imageMapper;
	
	@Inject
	public void setHintMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}
	
	@Override
	public Image getImage(int id) {
		return imageMapper.selectByCode(id);
	}
	
	@Override
	public Image getImage(String name) {
		return imageMapper.selectByName(name);
	}

	@Override
	public List<Image> getAllImages() {
		return imageMapper.selectAll();
	}

	@Override
	public Image createImage(Image image) {
		Image tempImage = getImage(image.getName());
		if (tempImage == null) {
			imageMapper.createImage(image);
			return image;
		}
		return tempImage;
	}

	@Override
	public Image updateImage(Image image) {
		return imageMapper.updateImage(image);
	}

	@Override
	public boolean deleteImage(int imageId) {
		return imageMapper.deleteImage(imageId);
	}

}
