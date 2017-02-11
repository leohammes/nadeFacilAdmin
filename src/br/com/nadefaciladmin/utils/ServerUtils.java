package br.com.nadefaciladmin.utils;

import javax.faces.context.FacesContext;

import br.com.nadefaciladmin.controller.FormController;
import br.com.nadefaciladmin.controller.MainController;

public class ServerUtils {

	private static String SERVER_PATH = System.getProperty("user.home") + "\\ImagesUploaded";

	public static String getServerPath() {
		return SERVER_PATH;
	}

	public static void setServerPath(String path) {
		SERVER_PATH = path;
	}
	
	public static MainController getMainController() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{mainController}", MainController.class);
	}
	
	public static FormController getFormController() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{formController}", FormController.class);
	}
}
