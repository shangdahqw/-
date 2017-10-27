package sd.hqw.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sd.hqw.domain.User;




public class Session_UserFilter implements Filter {
	
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String url=request.getRequestURI();
		//检查用户是否已登陆
		//如果登入了
		
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null){
			chain.doFilter(request, response);
			return;
		}
		//排除注册登入主页
		if(url.startsWith("/websocket_rtc/servlet/Register")||url.startsWith("/websocket_rtc/servlet/LoginServlet")){
			chain.doFilter(request, response);
			return;
		}
			
		//没有登入
		response.sendRedirect("/websocket_rtc/index.jsp");
	}
	

	
	
	

	public void destroy() {
		// TODO Auto-generated method stub

	}

	

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
