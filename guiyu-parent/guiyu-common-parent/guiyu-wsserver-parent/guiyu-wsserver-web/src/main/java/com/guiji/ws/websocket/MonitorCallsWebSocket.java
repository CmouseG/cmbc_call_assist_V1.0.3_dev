package com.guiji.ws.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.guiji.ws.model.OnlineUser;
import com.guiji.ws.model.WebSocketConnect;
import com.guiji.ws.service.WebSocketConnectService;
import com.guiji.ws.service.WsUserService;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: RobotWebSocket 
* @Description: 监控实时通话信息ws长链接 
* @date 2019年2月21日 下午7:21:13 
* @version V1.0  
*/
@ServerEndpoint("/webSocket/robot/{sence}/{userId}")
@Component
@Slf4j
public class MonitorCallsWebSocket {
	private String uuid;	//连接的唯一编号
	//concurrent包的线程安全Set，用来存放每个客户端对应的RobotWebSocket对象
	private static CopyOnWriteArraySet<MonitorCallsWebSocket> clients = new CopyOnWriteArraySet<MonitorCallsWebSocket>();
	//spring 容器
    private static ApplicationContext applicationContext;
    private Session session;
    private String orgCode;
    private String sence;	//连接场景
    private String userId; 
    //是否协呼人员
  	private boolean assistCallUser;
    
    /**
     * 启动类中设置spring容器
     * @param context
     */
    public static void setApplicationContext(ApplicationContext context) {
    	applicationContext = context;
    }
    
    @OnOpen
    public void onOpen(@PathParam("sence") String sence,@PathParam("userId") String userId,Session session) {
    	this.userId = userId;
    	this.sence = sence;
        this.session = session;
        clients.add(this);     //加入set中
        log.info("有新连接加入！场景：{},加入用户：{}",sence,userId);
        try {
        	WsUserService monitorUserService = (WsUserService) applicationContext.getBean("wsUserService"); //实时监控用户服务
        	WebSocketConnectService webSocketConnectService = (WebSocketConnectService) applicationContext.getBean("webSocketConnectService"); //实时连接服务
        	//监控用户上线
        	OnlineUser user = monitorUserService.wsUserOnLine(sence,userId);
        	this.orgCode = user.getOrgCode();
        	this.assistCallUser = user.isAssistCallUser();
        	this.uuid = user.getUuid();
        	webSocketConnectService.openWs(this);
        } catch (Exception e) {
            log.error("WS OPEN异常",e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    	clients.remove(this);  //从set中删除
    	log.info("有一连接关闭！场景：{},用户编号：{}",this.sence,this.userId);
    	WsUserService monitorUserService = (WsUserService) applicationContext.getBean("wsUserService"); //实时监控用户服务
    	WebSocketConnectService webSocketConnectService = (WebSocketConnectService) applicationContext.getBean("webSocketConnectService"); //实时连接服务
    	//监控用户下线
    	monitorUserService.wsUserOffLine(sence,userId);
    	webSocketConnectService.closeWs(this);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        //群发消息
        for (MonitorCallsWebSocket item : clients) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误",error);
        error.printStackTrace();
    }

    /**
     * 往客户端发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
    	if(this.session.isOpen()) {
    		this.session.getAsyncRemote().sendText(message);
    	}else {
    		log.error("session已关闭");
    	}
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        for (MonitorCallsWebSocket item : clients) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }
    
    
	/**
	 * 查询websocket
	 * @param connect
	 * @return
	 */
	public static MonitorCallsWebSocket queryWsSocketBy(WebSocketConnect connect) {
		if(connect!=null) {
			String connectUUid = connect.getUuid();
			if(clients !=null) {
				for(MonitorCallsWebSocket socket:clients) {
					if(connectUUid.equals(socket.getUuid())) {
						return socket;
					}
				}
			}
		}
		return null;
	}

	
    
	public static CopyOnWriteArraySet<MonitorCallsWebSocket> getClients() {
		return clients;
	}

	public static void setClients(CopyOnWriteArraySet<MonitorCallsWebSocket> clients) {
		MonitorCallsWebSocket.clients = clients;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	public boolean isAssistCallUser() {
		return assistCallUser;
	}
	
	public void setAssistCallUser(boolean assistCallUser) {
		this.assistCallUser = assistCallUser;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSence() {
		return sence;
	}

	public void setSence(String sence) {
		this.sence = sence;
	}
}
