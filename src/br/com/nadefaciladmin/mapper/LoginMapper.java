package br.com.nadefaciladmin.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import br.com.nadefaciladmin.bean.Login;

public interface LoginMapper {
	
	final String LOGIN = "SELECT * FROM users where user_name = #{userName}";

	@Select(LOGIN)
	@Results(value = {
		@Result(column="user_pass", property="userPass"),
		@Result(column="user_name", property="userName"),
	})
	Login login(String userName);
} 