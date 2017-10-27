package sd.hqw.utils;


import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;


public class WebUtils {                                                  //RegisterFormBean
	public static <T> T request2Bean(HttpServletRequest request,Class <T> beanClass){
		try {
			T bean = beanClass.newInstance();
			//得到request里面所有数据
			Map<?, ?> map = request.getParameterMap();
			//map{name=aa,password=bb,birthday=1990-09-09}  bean(name=aa,password=dd,birthday=Date)
			BeanUtils.populate(bean, map);   //把map中东西装到bean中
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	public static void copyBean(Object src,Object dest){
		
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			throw new RuntimeException();
		} 
	}
	
  //产生全球唯一id
	public static String generateID(){		
		return UUID.randomUUID().toString();
	}

}
