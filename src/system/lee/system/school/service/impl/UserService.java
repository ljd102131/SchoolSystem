package lee.system.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lee.system.school.dao.impl.UserDao;
import lee.system.school.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public User findUserByLoginName(String loginName) {
		if (loginName.isEmpty()) {
			return null;
		}
		return userDao.findUserByLoginName(loginName);
	}
}
