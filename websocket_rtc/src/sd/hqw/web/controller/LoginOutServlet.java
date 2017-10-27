package sd.hqw.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/servlet/LoginOutServlet")
public class LoginOutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession(false);
		if(session!=null)
			session.removeAttribute("user");
		//request.getSession().setAttribute("message", "注销成功!3秒后跳转<br/><meta http-equiv='refresh' content='3;"+request.getContextPath()+"/'>");
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
	}

}
