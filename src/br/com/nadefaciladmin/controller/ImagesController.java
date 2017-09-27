package br.com.nadefaciladmin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import com.google.inject.servlet.SessionScoped;
import br.com.nadefaciladmin.utils.ServerUtils;

@SessionScoped
@ManagedBean
public class ImagesController {
	
	private UploadedFile uploadedFile;
	
	public UploadedFile getFile() {
		return uploadedFile;
	}

	public void setFile(UploadedFile file) {
		this.uploadedFile = file;
	}
	
	public void replaceImage(FileUploadEvent event) {
		uploadedFile = event.getFile();
		if (uploadedFile != null) {
			String imageName = (String) event.getComponent().getAttributes().get("imageName");
			File file = new File(ServerUtils.getServerPath() + File.separator + imageName);
			if (file.exists()) file.delete();
			saveFile(uploadedFile.getContents(), file);
		}
	}
	
	public void upload(FileUploadEvent event) {
		uploadedFile = event.getFile();
		if (uploadedFile != null) {
			File file = new File(ServerUtils.getServerPath());
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(file.getPath() + File.separator + uploadedFile.getFileName());
			saveFile(uploadedFile.getContents(), file);
		}
	}
	
	public List<String> getImages() {
		ArrayList<String> images = new ArrayList<String>();
		String serverPath = System.getProperty("user.home") + "\\ImagesUploaded";
		File file = new File(serverPath);
		if (file.exists() && file.isDirectory()) {
			for (String path : file.list()) {
				if (path.endsWith(".png") || path.endsWith(".jpg")) {
					images.add(path);
				}
			}
		}
		return images;
	}
	
	public void setImageName() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String imageName = params.get("name");
		setImageProperties(imageName);
	}
	
	private void setImageProperties(String imageName) {
		ServerUtils.getFormController().getCurrentHint().getImage().setName(imageName);
		ServerUtils.getFormController().getCurrentHint().getImage().setServerPath(ServerUtils.getServerPath());
	}
	
	private void saveFile(byte[] sourceBytes, File dest) {
		try {
			String fileName = FilenameUtils.getName(dest.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream(dest);
			fos.write(sourceBytes);
			fos.close();
			FacesContext instance = FacesContext.getCurrentInstance();
			setImageProperties(fileName);
			instance.addMessage("mensagens", new FacesMessage(FacesMessage.SEVERITY_INFO, fileName + " anexado com sucesso", null));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}