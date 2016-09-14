package br.com.nadefaciladmin.controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import com.google.inject.Injector;
import br.com.nadefaciladmin.application.Page;
import br.com.nadefaciladmin.bean.Hint;
import br.com.nadefaciladmin.service.HintService;


@RequestScoped
@ManagedBean
public class MainController {
	
	private int currentPage;

	public HintService getHintsService() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Injector injector = (Injector) servletContext.getAttribute("injector");
		HintService service = injector.getInstance(HintService.class);
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
		ec.redirect("/admin/editar/card/" + cardId);
		return "/";
	}
	
	public String goToAddCard(Page page) throws Exception {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		setCurrentPage(page.ordinal());
		ec.redirect("/admin/inserir/" + page.ordinal());
		return "/";
	}

	public Page[] getPageValues() {
		return Page.values();
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
		switch (page.ordinal()) {
		case 0:
			return "de Contatos";
		case 1:
			return "de Galeria de Imagens";
		case 2:
			return "das Dicas da Semana";
		case 3:
			return "da História da academia";
		case 4:
			return "inicial";
		case 5:
			return "das modalidades";
		case 6:
			return "da agenda da academia";
		default:
			return "Página não mapeada no Enumeration";
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
