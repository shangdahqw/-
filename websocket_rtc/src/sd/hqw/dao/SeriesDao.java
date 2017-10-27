package sd.hqw.dao;

import java.util.List;

import sd.hqw.domain.Series;
import sd.hqw.domain.User;

public interface SeriesDao {
	//添加设备序列号
	void add(Series series);
	Series find(String seriesid);
	
	User findByseriesid(String seriesid);
	List<Series> getAll(String userid);
}
