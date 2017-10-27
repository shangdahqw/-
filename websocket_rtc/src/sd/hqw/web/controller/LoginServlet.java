package sd.hqw.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sd.hqw.domain.Series;
import sd.hqw.domain.User;
import sd.hqw.service.BusinessService;
import sd.hqw.service.impl.BusinessServiceImpl;
@WebServlet("/servlet/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String servlet_method=request.getParameter("servlet_method");
		
		if(servlet_method.equals("login")){
			String username=request.getParameter("username");
			String password=request.getParameter("password");
					
			BusinessService service  =new BusinessServiceImpl();
			User user=service.login(username, password);
			if(user!=null){
				request.getSession().setAttribute("user", user);
				ajax_send("1", response);
				return ;
			}else{
				ajax_send("0", response);
				return ;
			}
				
			
		}
		
		if(servlet_method.equals("tomyindex")){
			BusinessService service  =new BusinessServiceImpl();
			User user=(User) request.getSession().getAttribute("user");
			List<Series>  list=service. getAll(user.getId());
			request.getSession().setAttribute("serieslist", list);
			//response.sendRedirect("/websocket_rtc/WEB-INF/jsp/myindex.jsp");//不能重定向过去
			request.getRequestDispatcher("/WEB-INF/jsp/myindex.jsp").forward(request, response);
			return ;
			
		}
		

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
	
	
	
	private void ajax_send(String message,HttpServletResponse response){

		try {
			response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
