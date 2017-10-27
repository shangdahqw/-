package sd.hqw.dao;

import sd.hqw.domain.User;

public interface UserDao {

	void add(User user);

	User find(String username, String password);

	//查找注册的用户是否在数据库中存在
	boolean find(String username);
	
	User findByid(String id);

}