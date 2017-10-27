<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%
	String path=request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<link href="css/webnet_rtc.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="css/layer.css" >
	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/layer.js"></script>
</head>
<body>
	<div class="top1">
		<div class="wrap top1_in">
			您好，欢迎访问物联网平台!
				<c:if test="${empty sessionScope.user}">
				     <a href="index.jsp">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
               		 <a href="servlet/RegisterUIServlet">注册</a> 		 		 	 
				</c:if>
				<c:if test="${!empty sessionScope.user}">
				     <font color="red" style="font-size:14px;font-weight:bold">${sessionScope.user.username}</font>&nbsp;&nbsp;|&nbsp;&nbsp;
	 		 	 	 <a href="servlet/LoginOutServlet">退出</a>
				</c:if>
			

        </div>
    </div>
    <div class="clear"></div>
    
<!-- *******************************************************-->
    <br><br><br><br><br><br><br><br><br><br><br>
	
	<div style="margin:0 auto;text-align: center; color: #CD2626" >
		<h1>
			${message }!
		</h1>
					
	</div>


<br><br><br><br><br><br><br><br><br><br><br>
<!-- foot*******************************************************-->			
	<div class="foot">
		<div class="wrap">
			<div class="friendlink">
				<div class="tit3"><img src="css/index_53.png">
				</div>
				<div class="clear">
				</div>			
				<ul><li><a href="http://www.shu.edu.cn/IndexPage.html">上海大学官网</a></li><li>|</li></ul>
				<ul><li><a href="http://www.jwc.shu.edu.cn/jwc.html">上海大学教务处</a></li><li>|</li></ul>
				<ul><li><a href="http://www.xgb.shu.edu.cn/Default.aspx">上海大学学生办公室</a></li><li>|</li></ul>
			</div>
			<div class="clear"></div>						
		</div>
	</div>
	<div class="foot2">
		<div class="wrap">
			版权所有   物联网平台     小琳科技
		</div>
	</div>
	
</body>
</html>
