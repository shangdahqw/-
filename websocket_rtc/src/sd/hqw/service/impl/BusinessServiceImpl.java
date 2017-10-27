package sd.hqw.service.impl;

import java.util.List;

import sd.hqw.dao.SeriesDao;
import sd.hqw.dao.UserDao;
import sd.hqw.domain.Series;
import sd.hqw.domain.User;
import sd.hqw.exception.UserExistException;
import sd.hqw.factory.DaoFactory;
import sd.hqw.service.BusinessService;
import sd.hqw.utils.ServiceUtils;

//6  对web层提供所有的业务服务
public class BusinessServiceImpl implements BusinessService {

	//private UserDao dao=new UserDaoJdbcImpl();//此处不合适，因为底层接口变了，上层也要变化。应该用工厂模式  或者 spring
	
	private UserDao dao = DaoFactory.getInstance().createDao(UserDao.class); //工厂模式
	private SeriesDao dao2 = DaoFactory.getInstance().createDao(SeriesDao.class); //工厂模式
	//对web层提供注册服务
	public void register(User user) throws UserExistException{
		boolean b=dao.find(user.getUsername());
		if(b){
			throw new UserExistException("用户已经存在！");
			//用户存在，给web层抛出编译时异常
		}else{
			user.setPassword(ServiceUtils.md5(user.getPassword()));
			dao.add(user);
		}
	}
	//对web层提供登录服务
	public User login(String username,String password){
		password=ServiceUtils.md5(password);
		return dao.find(username, password);
	}
	
	//添加设备序列号
	@Override
	public void addseries(String seriesid,String seriesname,String userid) {
		User user=dao.findByid(userid);
		Series series =new Series();
		series.setId(seriesid);
		series.setName(seriesname);
		series.setUer(user);
		dao2.add(series);
	}
	
	//根据设备序列号找用户
	@Override
	public User findByseriesid(String seriesid) {
		User user=dao2.findByseriesid(seriesid);		
		return user;
	}
	
	@Override
	public Series find(String seriesid) {
		Series series=dao2.find(seriesid);
		return series;
	}
	@Override
	public List<Series> getAll(String userid) {
		List<Series> list=dao2.getAll(userid);
		return list;
	}
	
}
