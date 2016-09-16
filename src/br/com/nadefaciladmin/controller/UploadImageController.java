package br.com.nadefaciladmin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
@ManagedBean
public class UploadImageController {
	
	private UploadedFile uploadedFile;

	public UploadedFile getFile() {
		return uploadedFile;
	}

	public void setFile(UploadedFile file) {
		this.uploadedFile = file;
	}

	public void upload(FileUploadEvent event) {
		uploadedFile = event.getFile();
		if (uploadedFile != null) {
			File file = new File(System.getProperty("user.home") + "\\ImagesUploaded");
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
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
