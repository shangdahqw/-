package sd.hqw.web.formbean;
import java.util.HashMap;
import java.util.Map;

public class RegisterFormBean {

	private String username;
	private String password;
	
	private Map<String,String> errorMap = new HashMap<String,String>();
	
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Map<String,String> getErrorMap() {
		return errorMap;
	}
	public void setErrorMap(Map<String,String> errorMap) {
		this.errorMap = errorMap;
	}

	/*
	private String username;  用户名不能为空，并且要是3-8的字母 abcdABcd
	private String password;  密码不能为空，并且要是3-8的数字
	 * 
	 */
	public boolean validate(){
		
		
		
		if(this.username.contains(" ")){
			
			errorMap.put("username_error", "用户名不能含有空格！！");
			return false;
		}else{
			if(!this.username.matches("[a-zA-Z]{3,8}")){
				
				errorMap.put("username_error", "用户名必须是3-8位的字母！！");
				return false;
			}
		}
		
		errorMap.put("username",this.username);	//回显
		
		if(this.password.contains(" ")){
			
			errorMap.put("password_error", "不能含有空格！！");
			return false;
		}else{
			if(!this.password.matches("\\d{3,8}")){
				
				errorMap.put("password_error", "密码必须是3-8位的数字！！");
				return false;
			}
		}
	
		
		return true;
	}

	
	
	
	
}
