package br.com.nadefaciladmin.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.google.inject.Injector;

import br.com.nadefaciladmin.bean.Login;
import br.com.nadefaciladmin.service.LoginService;

@ViewScoped
@ManagedBean
public class IndexController {
	
	private String requestedURI;
	private String userName;
	private String userPass;

	@PostConstruct
	public void init() {
	    requestedURI = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
	    if (requestedURI == null) {
	        requestedURI = "/index.xhtml";
	    }
	}
	
	public String submit() throws IOException {
		LoginController loginController = new LoginController();
		FacesContext context = FacesContext.getCurrentInstance();
	    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		Injector injector = (Injector) servletContext.getAttribute("injector");
		LoginService service = injector.getInstance(LoginService.class);
		Login login = service.login(userName);
		if (loginController.doLogin(service.login(userName), userPass)) {
			session.setAttribute("login", login);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/admin");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usu�rio ou senha incorretos", null));
		}
		return "/";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
	public void goToManageImagesPage() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/images");
	}
	
	public void goToManageHintsPage() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/hints");
	}
	
	public void checkBeforeLogin() throws IOException {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Login login = (Login) session.getAttribute("login");
		if (login != null) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/admin");
		}
	}
	
	public void checkUserLogin() throws IOException {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Login login = (Login) session.getAttribute("login");
		if (login == null) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login");
		}
	}
	
	public String unload() throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		servletContext.removeAttribute("login");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login");
		return "";
	}
	
}