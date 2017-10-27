package sd.hqw.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import sd.hqw.domain.User;
import sd.hqw.exception.UserExistException;
import sd.hqw.service.BusinessService;
import sd.hqw.service.impl.BusinessServiceImpl;
import sd.hqw.utils.WebUtils;
import sd.hqw.web.formbean.RegisterFormBean;
//处理注册请求
@WebServlet("/servlet/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//1.合法性校验  formBean把表单数据封装到formbean
		RegisterFormBean formbean=WebUtils.request2Bean(request,RegisterFormBean.class);
		boolean b=formbean.validate();		
		//2.如果校验失败，跳回到表单页面，回显失败消息
		if(!b){	
			Gson gson = new Gson();
		    String jsonMsg = gson.toJson(formbean.getErrorMap());
		    ajax_send(jsonMsg, response);
			return ;
		}
		
		//3.校验成功，则调用service处理注册请求
		BusinessService service =new BusinessServiceImpl();
		
		User user=new User();
		WebUtils.copyBean(formbean, user);
		user.setId(WebUtils.generateID());
		try {
			service.register(user);
			//注册成功，
			formbean.getErrorMap().put("success", "注册成功!");
			Gson gson = new Gson();
			String jsonMsg = gson.toJson(formbean.getErrorMap());
			ajax_send(jsonMsg, response);
			
			return;
		} catch (UserExistException e) {
			//4.service处理不成功，原因是用户存在跳回注册页面显示注册用户已存在
			formbean.getErrorMap().put("username_error", "注册的用户已存在");
			Gson gson = new Gson();
			String jsonMsg = gson.toJson(formbean.getErrorMap());
			ajax_send(jsonMsg, response);
			return;
		}catch (Exception e) {
			//5.service处理不成功，原因是其他问题   ，跳转到全局消息处理界面，显示友好错误页面
			e.printStackTrace();
			request.setAttribute("message", "服务器出现未知错误");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
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
