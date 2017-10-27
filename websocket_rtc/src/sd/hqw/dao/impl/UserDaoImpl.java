package sd.hqw.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import sd.hqw.dao.UserDao;
import sd.hqw.domain.User;
import sd.hqw.exception.DaoException;
import sd.hqw.utils.JdbcUtils_DBCP;


public class UserDaoImpl  extends JdbcUtils_DBCP implements UserDao {

	@Override
	public void add(User user) {

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils_DBCP.getConnection();
			String sql = "insert into user(id,username,password) values(?,?,?)";
			st = conn.prepareStatement(sql);			
			st.setString(1, user.getId());
			st.setString(2, user.getUsername());
			st.setString(3, user.getPassword());	
			int num = st.executeUpdate();
			if(num<1){
				throw new RuntimeException("注册用户失败");
			}
		}catch (Exception e) {
			throw new DaoException(e);  //抛运行时异常       上级不能处理抛运行时异常，否则抛编译时异常
		}finally{
			JdbcUtils_DBCP.release(conn, st, rs);
		}
		
		
	}

	@Override
	public User find(String username, String password) {
		
		Connection conn = null;
		PreparedStatement st = null;   //PreparedStatment
		ResultSet rs = null;
		try{
			conn = JdbcUtils_DBCP.getConnection();
			String sql = "select * from user where username=? and password=?";
			st = conn.prepareStatement(sql);   //预编译这条sql
			
			
			st.setString(1, username);
			st.setString(2, password);
			
			rs = st.executeQuery();
			if(rs.next()){
				User user = new User();
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("username"));
				return user;
			}
			return null;
		}catch (Exception e) {
			throw new DaoException(e); 
		}finally{
			JdbcUtils_DBCP.release(conn, st, rs);
		}
	}

	@Override
	public boolean find(String username) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils_DBCP.getConnection();
			String sql = "select * from user where username=?";
			st = conn.prepareStatement(sql);
			st.setString(1, username);
			
			rs = st.executeQuery();
			if(rs.next()){
				return true;
			}
			return false;
		}catch (Exception e) {
			throw new DaoException(e); 
		}finally{
			JdbcUtils_DBCP.release(conn, st, rs);
		}
	}

	@Override
	public User findByid(String id) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils_DBCP.getConnection();
			String sql = "select * from user where id=?";
			st = conn.prepareStatement(sql);
			st.setString(1,id);
			
			rs = st.executeQuery();
			if(rs.next()){
				User user = new User();
				user.setId(rs.getString("id"));
				user.setUsername(rs.getString("username"));
				return user;
			}
			return null;
		}catch (Exception e) {
			throw new DaoException(e); 
		}finally{
			JdbcUtils_DBCP.release(conn, st, rs);
		}
	}
	
	
}
