package www.raven.sw.ws;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import www.raven.sw.entity.dto.MessageDTO;
import www.raven.sw.entity.dto.TokenDTO;
import www.raven.sw.service.ChatsService;
import www.raven.sw.util.JsonUtil;
import www.raven.sw.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * ws连接管理
 * @author 刘家辉
 * @date 2023/12/04
 */
@Component
@Slf4j
@ServerEndpoint("/ws/{token}")
@Data
public class WsConnectionsCenter {

	/**
	 * 在线连接数map
	 */
	public static final Map<Integer, Session> SESSION_POOL = new HashMap<>();
	public static CopyOnWriteArraySet<WsConnectionsCenter> webSockets = new CopyOnWriteArraySet<>();
	/**
	 * 该连接的用户id
	 */
	protected Integer userId;
	/**
	 * 与客户端的连接会话
	 **/
	protected Session session;
	protected static ChatsService chatsService;

	@Autowired
	public void setChatService(ChatsService chatService) {
		WsConnectionsCenter.chatsService = chatService;
	}


	@OnOpen
	public void onOpen(Session session, @PathParam(value = "token") String token) {
		TokenDTO verify = JwtUtil.parseToken(token);
		session.getUserProperties().put("userDto", verify);
		this.userId = verify.getUserId();
		this.session = session;
		Session sessionExisted = SESSION_POOL.get(verify.getUserId());
		if (sessionExisted != null) {
			try {
				sessionExisted.close();
			} catch (Exception e) {
				log.error("关闭已存在的session失败");
			}
		}
		webSockets.add(this);
		SESSION_POOL.put(this.userId, session);
		log.info("ws: 有新的连接,用户id为{},总数为:{}", this.userId, webSockets.size());
	}

	@OnClose
	public void onClose() {
		webSockets.remove(this);
		SESSION_POOL.remove(this.userId);
		log.info("ws:用户id {} 连接断开，总数为:{}", this.userId, webSockets.size());
	}

	@OnMessage
	public void onMessage(String message) {
		log.info("----WebSocket收到客户端发来的消息:{}", message);
		MessageDTO messageDTO = JsonUtil.jsonToObj(message, MessageDTO.class);
		WsTools.sendOneMessage(messageDTO.getToUserId(), message);
		chatsService.saveChat(messageDTO, userId);
	}

	@OnError
	public void onError(Session session, Throwable error) {
		SESSION_POOL.remove(this.userId);
		log.error("--Websocket:内部错误{}", (Object) error.getStackTrace());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}