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
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import br.com.nadefaciladmin.bean.Hint;
import br.com.nadefaciladmin.bean.Image;

@ViewScoped
@ManagedBean
public class FormController {
	
	private String SERVER_PATH = System.getProperty("user.home") + "\\ImagesUploaded";
	
	private Hint currentHint;
	private UploadedFile uploadedFile;
	
	@PostConstruct
	public void init() {
		MainController mainController = getMainController();
	    if (mainController.getHint() == null) {
	        currentHint = new Hint();
	        currentHint.setPageCode(mainController.getCurrentPage());
	    } else {
	    	currentHint = mainController.getHint();
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
	
	public void deleteCard(int hintId) {
		boolean deleted = getMainController().getHintsService().deleteHint(hintId);
		if (deleted) {
			showMessage(FacesMessage.SEVERITY_INFO, "Card excluído");
		} else {
			showMessage(FacesMessage.SEVERITY_FATAL, "Erro ao Excluir card");
		}
	}
	
	public void setImageName() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String imageName = params.get("name");
		setImageProperties(imageName);
	}
	
	public void saveOrUpdate() {
		Image image = getMainController().getImagesService().getImage(currentHint.getImage().getName());
		currentHint.setImage(image != null ? image : getMainController().getImagesService().createImage(currentHint.getImage()));
		
		if (currentHint.getId() != 0) {
			updateHint();
		} else {
			save();
		}
	}
	
	private void save() {
		MainController mainController = getMainController();
		mainController.getHintsService().createHint(currentHint);
		showMessage(FacesMessage.SEVERITY_INFO, "Card salvo com sucesso");
	}
	
	private void updateHint() {
		
		getMainController().getHintsService().updateHint(currentHint);
		showMessage(FacesMessage.SEVERITY_INFO, "Card salvo com sucesso");
	}
	
	private void showMessage(Severity type, String message) {
		FacesContext instance = FacesContext.getCurrentInstance();
		instance.addMessage("mensagens", new FacesMessage(type, message, null));
	}
	
	private void setImageProperties(String imageName) {
		this.currentHint.getImage().setName(imageName);
		this.currentHint.getImage().setServerPath(SERVER_PATH);
	}
	
	private MainController getMainController() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{mainController}", MainController.class);
	}
}
