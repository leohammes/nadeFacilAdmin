package br.com.nadefaciladmin.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Injector;

import br.com.nadefaciladmin.application.Page;
import br.com.nadefaciladmin.bean.Hint;
import br.com.nadefaciladmin.service.HintService;
import br.com.nadefaciladmin.service.ImageService;


@SessionScoped
@ManagedBean
public class MainController {
	
	private int currentPage;
	private Hint hint;
	
	public HintService getHintsService() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Injector injector = (Injector) servletContext.getAttribute("injector");
		HintService service = injector.getInstance(HintService.class);
		return service;
	}
	
	public ImageService getImagesService() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Injector injector = (Injector) servletContext.getAttribute("injector");
		ImageService service = injector.getInstance(ImageService.class);
		return service;
	}
	
	public List<Hint> getHints() {
		return getHintsService().getAllHints();
	}
	
	public List<Hint> getHintsByPage(Page pageCode) {
		return getHintsService().getHintsByPage(pageCode);
	}
	
	public String goToEditCardPage(int cardId) throws Exception {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		setHint(getHintsService().getHint(cardId));
		setCurrentPage(hint.getPageCode());
		ec.redirect("/admin/card/" + cardId);
		return "/";
	}
	
	public String goToAddCard(Page page) throws Exception {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		setCurrentPage(page.ordinal());
		setHint(null);
		ec.redirect("/admin/inserir/" + page.ordinal());
		return "/";
	}

	public Page[] getPageValues() {
		return Page.values();
	}
	
	public String getImageServletUrl() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = request.getRequestURL().toString();
		String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + "/";

		return baseURL + "/images/";
	}
	
	public String getPageDescription(int pageId) {
		Page page = null;
		for (Page currentPage : Page.values()) {
			if (currentPage.ordinal() == pageId) {
				page = currentPage;
			}
		}
		return getPageDescription(page);
	}
	
	public String getPageDescription(Page page) {
		switch (page) {
		case CONTACT:
			return "de Contatos";
		case GALLERY:
			return "de Galeria de Imagens";
		case HINTS:
			return "das Dicas da Semana";
		case HISTORY:
			return "da Hist�ria da academia";
		case INDEX:
			return "inicial";
		case MODES:
			return "das modalidades";
		case SCHEDULE:
			return "da agenda da academia";
		default:
			return "P�gina n�o mapeada no Enumeration";
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Hint getHint() {
		return hint;
	}

	public void setHint(Hint hint) {
		this.hint = hint;
	}
}
