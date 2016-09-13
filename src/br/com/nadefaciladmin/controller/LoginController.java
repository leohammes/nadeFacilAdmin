package br.com.nadefaciladmin.controller;

import br.com.nadefaciladmin.bean.Login;

public class LoginController {
	
	public boolean doLogin(Login login, String pass) {
		return login != null && login.getUserPass() != null && login.getUserPass().equals(pass);
	}
}
