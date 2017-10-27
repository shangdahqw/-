package sd.hqw.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import sd.hqw.service.BusinessService;
import sd.hqw.service.impl.BusinessServiceImpl;



@ServerEndpoint("/websocket_rtc/{fromid}/{toid}")
public class ServerSocket {

	
	private static final Map<String,ServerSocket> onlineUsers = new HashMap<String, ServerSocket>();

	private static int onlineCount = 0;

	private String flagid;

	private Session session;
	private String client_userid;


	@OnOpen
	public void onOpen(Session session, @PathParam("fromid")String flagid,@PathParam("toid")String client_user_toid){

		this.session = session;
		this.flagid=flagid;
		if(flagid.length()==10){		
			onlineUsers.put(this.flagid, this);
		    client_userid=find_uerid_by_seriesid(this.flagid);
			String jsonstr_content = MessageUtil.strTOjsonstr(MessageUtil.USER,"1");

			for (String key : onlineUsers.keySet()) {
				if(key.equals(client_userid))
					try {
						onlineUsers.get(key).session.getBasicRemote().sendText(jsonstr_content);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}		
			
		}else if(flagid.length()==36){
			onlineUsers.put(this.flagid, this);	
			boolean f=false;
			for (String key : onlineUsers.keySet()) {
				if(key.equals(client_user_toid))
					try {
						f=true;
						String jsonstr_content = MessageUtil.strTOjsonstr(MessageUtil.USER,"1");
						onlineUsers.get(this.flagid).session.getBasicRemote().sendText(jsonstr_content);
					} catch (IOException e) {
						e.printStackTrace();
					}

			}	
			if(!f){
				String jsonstr_content = MessageUtil.strTOjsonstr(MessageUtil.USER,"0");
				try {
					onlineUsers.get(this.flagid).session.getBasicRemote().sendText(jsonstr_content);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
		}else
			return;

		addOnlineCount();           //在线数加1
		System.out.println("有新连接加入!当前在线人数为" + onlineUsers.size());
	}
	






	@OnClose
	public void onClose(){
		onlineUsers.remove(this.flagid);  //从map中删除
		
		if(this.flagid.length()==10){
			String jsonstr_content = MessageUtil.strTOjsonstr(MessageUtil.USER,"0");

			for (String key : onlineUsers.keySet()) {
				if(key.equals(client_userid))
					try {
						onlineUsers.get(key).session.getBasicRemote().sendText(jsonstr_content);
					} catch (IOException e) {
						e.printStackTrace();
					}

			}	
		}
		subOnlineCount();           //在线数减1   
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {

		HashMap<String,String> messageMap = MessageUtil.strTOmap(message);    //处理消息类
		String fromid = messageMap.get("fromid");  
		String toid = messageMap.get("toid");
		String content = messageMap.get("content");
		System.out.println(content.length());
		if(fromid.length()==10){			
			String jsonstr_content = MessageUtil.strTOjsonstr(MessageUtil.MESSAGE,content);
			for (String key : onlineUsers.keySet()) {
				if(key.equals(client_userid))
					onlineUsers.get(key).session.getBasicRemote().sendText(jsonstr_content);

			}
			
		}
		else if(fromid.length()==36){
			String jsonstr_content = MessageUtil.strTOjsonstr(MessageUtil.MESSAGE,content);
			for (String key : onlineUsers.keySet()) {
				if(key.equals(toid))
					onlineUsers.get(key).session.getBasicRemote().sendText(jsonstr_content);

			}
		}

		System.out.println("来自客户端的消息:" + message);
		
	}

	

	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}


	
	
	
	
	
	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		ServerSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		ServerSocket.onlineCount--;
	}
	String find_uerid_by_seriesid(String seriesid){
		BusinessService service =new BusinessServiceImpl();
		return service.findByseriesid(seriesid).getId();
	}

}