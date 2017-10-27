package sd.hqw.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sd.hqw.service.BusinessService;
import sd.hqw.service.impl.BusinessServiceImpl;

@WebServlet("/servlet/AddseriesServlet")
public class AddseriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String seriesid=request.getParameter("seriesid");
		String seriesname=request.getParameter("seriesname");
		String userid=request.getParameter("userid");
		BusinessService service  =new BusinessServiceImpl();
		try {
			
			service.addseries(seriesid, seriesname,userid);		
			
			ajax_send("1", response);
			return ;
			
		} catch (Exception e) {
			ajax_send("0", response);
			return ;
		}
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	private void ajax_send(String message,HttpServletResponse response){

		try {
			response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
