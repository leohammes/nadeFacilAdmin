package br.com.nadefaciladmin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;

import br.com.nadefaciladmin.bean.Hint;
import br.com.nadefaciladmin.bean.Image;

@ViewScoped
@ManagedBean
public class SaveHintController {
	
	private Hint currentHint;
	private UploadedFile uploadedFile;

	@PostConstruct
	public void init() {
	    if (currentHint == null) {
	        currentHint = new Hint();
	    }
	}
	
	public UploadedFile getFile() {
		return uploadedFile;
	}

	public void setFile(UploadedFile file) {
		this.uploadedFile = file;
	}

	public Hint getCurrentHint() {
		return currentHint;
	}

	public void setCurrentHint(Hint currentHint) {
		this.currentHint = currentHint;
	}
	
	public void upload(FileUploadEvent event) {
		uploadedFile = event.getFile();
		if (uploadedFile != null) {
			String serverPath = System.getProperty("user.home") + "\\ImagesUploaded";
			File file = new File(serverPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(file.getPath() + File.separator + uploadedFile.getFileName());
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(event.getFile().getContents());
				fos.close();
				FacesContext instance = FacesContext.getCurrentInstance();
				instance.addMessage("mensagens", new FacesMessage(FacesMessage.SEVERITY_INFO, file.getName() + " anexado com sucesso", null));
				
				Image image = new Image();
				image.setName(uploadedFile.getFileName());
				image.setServerPath(serverPath);
				currentHint.setImage(image);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
