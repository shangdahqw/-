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
		<div style="font-size:16px;font-weight:bold">您的晾衣架</div>
		<div class="juzhong1">
			<form name="myform" >
    			<ul>
					<li><span class="new1">与服务器连接状态</span><input id="message" type="text"  class="new21" readonly="readonly" style="border:0px solid;color: red"/><input id ="closeWebSocket" type="button" value="关闭连接"  class="closewebsocket"/></li>
					<li><span class="new1">晾衣架在线情况</span><input id="series_status" type="text"  class="new2"  readonly="readonly" style="border:0px solid;color: red"/><li>
					<li><span class="new1">晾衣架状态</span><input id="state" type="text"  class="new2"  readonly="readonly" style="border:0px solid"/><li>					
					<li><span class="new1">温度:</span><input id="wendu" type="text"  class="new2"  readonly="readonly" style="border:0px solid"/><li>
					<li><span class="new1">湿度:</span><input id="shidu" type="text"  class="new2"  readonly="readonly" style="border:0px solid"/><li>
					<li><input id ="shenchu" type="button" value="伸出"  class="new41"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id ="suojin" type="button" value="缩进"  class="new41"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id ="stop" type="button" value="停止"  class="new41"/><li>

					
				</ul>
			</form>
						
		</div >
		
		 <script type="text/javascript">
		      var websocket = null;    
		      var toid="${seriesid}"; 
		      var fromid="${userid}";
		      //判断当前浏览器是否支持WebSocket
		      if('WebSocket' in window){
		          websocket = new WebSocket("ws://192.168.31.176:8080/websocket_rtc/websocket_rtc/${requestScope.userid}/${seriesid}");
		      }
		      else{
		    	  layer.msg('Not support websocket')
		      }
		       
		      //连接发生错误的回调方法
		      websocket.onerror = function(){
		          setMessageInnerHTML("error");
		      };
		       
		      //连接成功建立的回调方法
		      websocket.onopen = function(event){
		          setMessageInnerHTML("open");
		      }
		       
		      //接收到消息的回调方法
		      websocket.onmessage = function(event){
		    	  var data = event.data;
		          var o = eval ('('+data+')');//将字符串转换成JSON
		          
		          if(o.type == 'message'){
		        	  setMessageInnerHTML(o.data);
		          }else if(o.type == 'user'){
		        	  setMessageInnerHTML(o.data);
		        		  
		          }		    		    	  		    	  
		      }
		       
		      //连接关闭的回调方法
		      websocket.onclose = function(){
		          setMessageInnerHTML("close");
		      }
		       
		      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
		      window.onbeforeunload = function(){
		          websocket.close();
		      }
		       		       
		     // <!--脚本模型  -->

		      var closeWebSocketbt_n=document.getElementById("closeWebSocket");  
		      closeWebSocketbt_n.onclick=closeWebSocket;
		      var shenchubt_n= document.getElementById("shenchu");  
		      shenchubt_n.onclick=shenchu;
		      var  suojinbt_n= document.getElementById("suojin");  
		      suojinbt_n.onclick=suojin;
		      var  stopbt_n= document.getElementById("stop");  
		      stopbt_n.onclick=stop;
		      
		   		      
		      //将消息显示在网页上
		      function setMessageInnerHTML(data){
		    	  if(data=="open"){
		    		  document.getElementById('message').value  = "已连接" ;
		    	  }
		    	  else if(data=="close"){
		    		  document.getElementById('message').value  = "已断开";
		    		  document.getElementById('series_status').value   = "";
		    			document.getElementById('wendu').value   = "";
		         	 	document.getElementById('shidu').value = "";
		         	 	document.getElementById('state').value = "";
		    		  
		    		  
		    		  
		    	  }
		    	  else if(data=="error"){
		    		  document.getElementById('message').value  = "连接发生错误";
		    	  }
		    	  else if(data!=null){
			    		if(data.length===5){
			    	 		var wendu=data.substring(0, 2);
			    	  		var shidu =data.substring(2, 4);
			    	  		var state=data.substring(4);
			    		
			          		document.getElementById('wendu').value   = wendu;
			         	 	document.getElementById('shidu').value = shidu;
						 	if(state ==="1")
								document.getElementById('state').value = "已伸出";
							 else if(state ==="2")
								document.getElementById('state').value = "已缩进";
						 	else
								document.getElementById('state').value = "已停止";
			    		}
			    		if(data=="1"){
			    			document.getElementById('series_status').value   = "已上线";
			    		}
			    		if(data=="0"){
			    			document.getElementById('series_status').value   = "已下线";
			    			document.getElementById('wendu').value   = "";
			         	 	document.getElementById('shidu').value = "";
			         	 	document.getElementById('state').value = "";
			    		}
		    	  }
		    	  else {
		    		  
		    	  }
		      }
		       
		      
		      
		      
		      //关闭连接
		      function closeWebSocket(){
		          websocket.close();
		      }
		      
		      //发送消息
		         
		      function shenchu(){
		          websocket.send(fromid+","+toid+",1");
		      }
		      function suojin(){
		    	  
		          websocket.send(fromid+","+toid+",2");
		      }
		      function stop(){
		          websocket.send(fromid+","+toid+",3");
		      }
	      
    
 	</script>	
 					
	</div>
	<br><br><br><br><br>
	
   
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