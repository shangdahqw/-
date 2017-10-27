package sd.hqw.service;

import java.util.List;

import sd.hqw.domain.Series;
import sd.hqw.domain.User;
import sd.hqw.exception.UserExistException;

public interface BusinessService {

	//对web层提供注册服务
	public void register(User user) throws UserExistException;

	//对web层提供登录服务
	public User login(String username, String password);
	
	//添加设备序列号
	public void addseries(String seriesid,String seriesname,String userid);
	//根据设备序列号找用户
	public User findByseriesid(String seriesid); 
	public  Series find(String seriesid);
	public List<Series> getAll(String userid);

}