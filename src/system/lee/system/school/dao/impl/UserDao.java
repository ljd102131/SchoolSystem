package lee.system.school.dao.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


import lee.system.jdbc.JDBCMySql;
import lee.system.school.entity.User;

@Repository
public class UserDao {
	public User findUserByLoginName(String loginName) {
		String sql = "select * from user where loginName = :loginName";
		User user = new User();
		user.setLoginName(loginName);
		//user.setPassWord(passWord);
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
		BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		new JDBCMySql();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = JDBCMySql.namedParameterJdbcTemplate;
		try {
			user = namedParameterJdbcTemplate.queryForObject(sql,parameterSource,rowMapper);
		} catch (DataAccessException e) {
			return null;
		}
		return user;
	}
}
