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
	<br><br>
	<div class="wrap">
		<div class="juzhong1">
			<form name="myform" >
    			<ul>
					<li><span class="new1">设备序列号</span><input id="seriesid" type="text" name="seriesid" class="new2"/></li>
					<li><span class="new1">设备名</span><input id="seriesname" type="text" name="seriesname" class="new2"/><li>
					<li><input id ="addbtn" type="button" value="添加设备"  class="new4"/><li>
					
				</ul>
			</form>
						
		</div >
		<div class="zx_R_3">
			<h1>我的所有设备</h1>
			<table align="left" cellpadding="10" cellspacing="0"   style="font-size: 14px" >
			
				<tr align="left">
					<th >设备序列号</th>	
					<th >设备名称</th>													
				</tr>
				<c:forEach var="s" items="${serieslist}">
					
		    		<tr align="left">
						<td >
							${s.id }	
						</td>
						<td >
							<a href="servlet/DispatcherServlet?seriesid=${s.id }">${s.name }</a>  	
						</td>
					</tr>
		    	</c:forEach>
				
			</table>	
			<div class="clear"></div>
			<h1></h1><br>
			
		</div>
		
		<script type="text/javascript">
			function createXMLHttpRequest() {
		      	try {
		      		return new XMLHttpRequest();//大多数浏览器
		      	} catch (e) {
					alert("浏览器不支持!");
					throw e;
		      	}	
		    }
			
	      	var btn = document.getElementById("addbtn");
	      	btn.onclick = function() {//给按钮的点击事件注册监听
	      			      		
	      		var seriesid = document.getElementById('seriesid').value;
		            if(seriesid==""){
		            	layer.msg('设备序列号不能为空!');	
		        	    return ;
		            }	
		        var seriesname = document.getElementById('seriesname').value;
			        if(seriesname==""){		        	  
			        	layer.msg("设备名称不能为空!");
			        	  return ;
			          }	
	      		
	      		var userid="${user.id }";
	      	
	      		var xmlHttp = createXMLHttpRequest();
	      		
	      		xmlHttp.open("POSt", "<c:url value='/servlet/AddseriesServlet'/>", true);
	      		xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	      		xmlHttp.send("seriesid="+seriesid+"&seriesname="+seriesname+"&userid="+userid);//GET请求没有请求体，但也要给出null，不然FireFox可能会不能发送！

	      		xmlHttp.onreadystatechange = function() {
	      			if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {

	      				var text = xmlHttp.responseText;
	      				if(text == "1") {
	      					layer.msg("添加成功!");
	      					window.setTimeout("window.location.href = 'servlet/LoginServlet?servlet_method=tomyindex'",1000); 
	    				} else {
	    					layer.msg("添加失败!");
	    				}
	      				
	      			}
	      		};
	      	};
		
		
		</script>
		
		
						
	</div>

	
   
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