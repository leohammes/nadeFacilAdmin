package br.com.nadefaciladmin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import br.com.nadefaciladmin.bean.Hint;

@ViewScoped
@ManagedBean
public class SaveHintController {
	
	private String SERVER_PATH = System.getProperty("user.home") + "\\ImagesUploaded";
	
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
	
	public void upload(FileUploadEvent event) {
		uploadedFile = event.getFile();
		if (uploadedFile != null) {
			File file = new File(SERVER_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(file.getPath() + File.separator + uploadedFile.getFileName());
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(event.getFile().getContents());
				fos.close();
				FacesContext instance = FacesContext.getCurrentInstance();
				setImageProperties(uploadedFile.getFileName());
				instance.addMessage("mensagens", new FacesMessage(FacesMessage.SEVERITY_INFO, file.getName() + " anexado com sucesso", null));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public void setImageName() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String imageName = params.get("name");
		setImageProperties(imageName);
	}
	
	public void save() {
		FacesContext context = FacesContext.getCurrentInstance();
		MainController mainController = context.getApplication().evaluateExpressionGet(context, "#{mainController}", MainController.class);
		mainController.getImagesService().createImage(currentHint.getImage());
		mainController.getHintsService().createHint(currentHint);
	}
	
	private void setImageProperties(String imageName) {
		this.currentHint.getImage().setName(imageName);
		this.currentHint.getImage().setServerPath(SERVER_PATH);
	}
}
