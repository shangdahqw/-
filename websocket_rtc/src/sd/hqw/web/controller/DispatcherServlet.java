package sd.hqw.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sd.hqw.domain.User;


@WebServlet("/servlet/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String seriesid=request.getParameter("seriesid");
		
		User user=(User) request.getSession().getAttribute("user");
		request.setAttribute("seriesid", seriesid);
		request.setAttribute("userid", user.getId());
		request.getRequestDispatcher("/WEB-INF/jsp/clothshore.jsp").forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
