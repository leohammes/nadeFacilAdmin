package br.com.nadefaciladmin.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import com.google.inject.Injector;

import br.com.nadefaciladmin.bean.Login;
import br.com.nadefaciladmin.service.HintService;
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
		Injector injector = (Injector) servletContext.getAttribute("injector");
		LoginService service = injector.getInstance(LoginService.class);
		Login login = service.login(userName);
		if (loginController.doLogin(service.login(userName), userPass)) {
			servletContext.setAttribute("login", login);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/admin");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário ou senha incorretos", null));
		}
		/*FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request =  (HttpServletRequest) context.getExternalContext().getRequest();
	    try {
	    	request.getSession();
	        request.login(userName, userPass);
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logado", null));
	    } catch (ServletException e) {
	        e.printStackTrace();
	    }
	    return "/";*/
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
	
	public void checkBeforeLogin() throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Login login = (Login) servletContext.getAttribute("login");
		//if (login != null) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/admin");
		//}
	}
	
	public void checkUserLogin() throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Login login = (Login) servletContext.getAttribute("login");
		if (login == null) {
			//FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/login");
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