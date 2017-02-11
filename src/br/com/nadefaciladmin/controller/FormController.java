package br.com.nadefaciladmin.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.nadefaciladmin.bean.Hint;
import br.com.nadefaciladmin.bean.Image;
import br.com.nadefaciladmin.utils.ServerUtils;

@ViewScoped
@ManagedBean
public class FormController {
	
	private Hint currentHint;
	
	@PostConstruct
	public void init() {
		MainController mainController = ServerUtils.getMainController();
	    if (mainController.getHint() == null) {
	        currentHint = new Hint();
	        currentHint.setPageCode(mainController.getCurrentPage());
	    } else {
	    	currentHint = mainController.getHint();
	    }
	}

	public Hint getCurrentHint() {
		return currentHint;
	}

	public void setCurrentHint(Hint currentHint) {
		this.currentHint = currentHint;
	}
	
	public void deleteCard(int hintId) {
		boolean deleted = ServerUtils.getMainController().getHintsService().deleteHint(hintId);
		if (deleted) {
			showMessage(FacesMessage.SEVERITY_INFO, "Card excluído");
		} else {
			showMessage(FacesMessage.SEVERITY_FATAL, "Erro ao Excluir card");
		}
	}
	
	public void saveOrUpdate() {
		Image image = ServerUtils.getMainController().getImagesService().getImage(currentHint.getImage().getName());
		currentHint.setImage(image != null ? image : ServerUtils.getMainController().getImagesService().createImage(currentHint.getImage()));
		
		if (currentHint.getId() != 0) {
			updateHint();
		} else {
			save();
		}
	}
	
	private void save() {
		MainController mainController = ServerUtils.getMainController();
		mainController.getHintsService().createHint(currentHint);
		showMessage(FacesMessage.SEVERITY_INFO, "Card salvo com sucesso");
	}
	
	private void updateHint() {
		
		ServerUtils.getMainController().getHintsService().updateHint(currentHint);
		showMessage(FacesMessage.SEVERITY_INFO, "Card salvo com sucesso");
	}
	
	private void showMessage(Severity type, String message) {
		FacesContext instance = FacesContext.getCurrentInstance();
		instance.addMessage("mensagens", new FacesMessage(type, message, null));
	}
}
