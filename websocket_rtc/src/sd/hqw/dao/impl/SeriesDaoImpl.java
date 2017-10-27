package sd.hqw.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sd.hqw.dao.SeriesDao;
import sd.hqw.domain.Series;
import sd.hqw.domain.User;
import sd.hqw.exception.DaoException;
import sd.hqw.utils.JdbcUtils_DBCP;

public class SeriesDaoImpl implements SeriesDao {

	@Override
	public void add(Series series) {

		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils_DBCP.getConnection();
			String sql = "insert into series(id,userId,name) values(?,?,?)";
			st = conn.prepareStatement(sql);			
			st.setString(1, series.getId());
			st.setString(2, series.getUer().getId());	
			st.setString(3, series.getName());
			int num = st.executeUpdate();
			if(num<1){
				throw new RuntimeException("添加设备失败");
			}
		}catch (Exception e) {
			throw new DaoException(e);  //抛运行时异常       上级不能处理抛运行时异常，否则抛编译时异常
		}finally{
			JdbcUtils_DBCP.release(conn, st, rs);
		}
		
		
	}

	@Override
	public User findByseriesid(String seriesid) {

		//使用外连接查询 	
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			conn = JdbcUtils_DBCP.getConnection();
			String sql = "select s.id as sid, s.userId as userId,"+
								 "u.id as uid,u.username as username "+		
								 "from series as s left join user as u on s.userId=u.id "+
								 "where s.id=?";
			st = conn.prepareStatement(sql);
			st.setString(1, seriesid);
			rs = st.executeQuery();
			
			if(rs.next()){				
				User user = new User();
				user.setId(rs.getString("uid"));
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
	public Series find(String seriesid) {
		
		Connection conn = null;
		PreparedStatement st = null;   //PreparedStatment
		ResultSet rs = null;
		try{
			conn = JdbcUtils_DBCP.getConnection();
			String sql = "select * from series where id =?";
			st = conn.prepareStatement(sql);   //预编译这条sql
			st.setString(1, seriesid);		
			rs = st.executeQuery();
			if(rs.next()){
				Series series = new Series();
				series.setName(rs.getString("name"));
				return series;
			}
			return null;
		}catch (Exception e) {
			throw new DaoException(e); 
		}finally{
			JdbcUtils_DBCP.release(conn, st, rs);
		}
	}

	@Override
	public List<Series> getAll(String userid) {
		
		Connection conn = null;
		PreparedStatement st = null;   //PreparedStatment
		ResultSet rs = null;
		try{
			conn = JdbcUtils_DBCP.getConnection();
			String sql = "select * from series where userId =?";
			st = conn.prepareStatement(sql);   //预编译这条sql
			st.setString(1, userid);		
			rs = st.executeQuery();
			
			List<Series> list = new ArrayList<Series>();
			while(rs.next()){
				Series series = new Series();
				series.setId(rs.getString("id"));
				series.setName(rs.getString("name"));
				list.add(series);
			}
			return list;
		}catch (Exception e) {
			throw new DaoException(e); 
		}finally{
			JdbcUtils_DBCP.release(conn, st, rs);
		}
	}

}
