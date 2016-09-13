package br.com.nadefaciladmin.service.impl;

import com.google.inject.Inject;

import br.com.nadefaciladmin.bean.Login;
import br.com.nadefaciladmin.mapper.LoginMapper;
import br.com.nadefaciladmin.service.LoginService;

public class LoginServiceImpl implements LoginService {
	
	@Inject
	private LoginMapper loginMapper;

	@Override
	public Login login(String userName) {
		// TODO Auto-generated method stub
		return loginMapper.login(userName);
	}

}