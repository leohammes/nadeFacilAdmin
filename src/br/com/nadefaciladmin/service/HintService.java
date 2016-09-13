package br.com.nadefaciladmin.service;

import java.util.List;
import br.com.nadefaciladmin.application.Page;
import br.com.nadefaciladmin.bean.Hint;

public interface HintService {
	
	public Hint getHint(int id);
	
	public List<Hint> getAllHints();
	
	public List<Hint> getHintsByPage(Page pageId);
	
	public boolean createHint(Hint hint);
	
	public boolean updateHint(Hint hint);
	
	public boolean deleteHint(int hintId);

}