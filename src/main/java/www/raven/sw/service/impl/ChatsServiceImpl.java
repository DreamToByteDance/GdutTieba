package www.raven.sw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import www.raven.sw.dao.ChatsDao;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.dto.MessageDTO;
import www.raven.sw.entity.po.Chats;
import www.raven.sw.entity.po.SessionUser;
import www.raven.sw.service.ChatsService;
import www.raven.sw.service.SessionUserService;
import www.raven.sw.service.SessionsService;
import www.raven.sw.ws.WsTools;

/**
 * 聊天表(Chats)表服务实现类
 *
 * @author makejava
 * @since 2024-11-07 13:28:57
 */
@Service("chatsService")
public class ChatsServiceImpl extends ServiceImpl<ChatsDao, Chats> implements ChatsService {
	@Resource
	private SessionsService sessionsService;
	@Resource
	private SessionUserService sessionUserService;

	@Override
	public void saveChat(MessageDTO messageDTO, Integer userId) {
		Chats build = Chats.builder()
				.message(messageDTO.getContent())
				.senderId(userId)
				.sessionId(messageDTO.getSessionId())
				.build();
		this.save(build);
	}

	@Override
	public PageVO<Chats> historyChat(Long sessionId, Integer page, Integer size) {
		LambdaQueryWrapper<Chats> qp = Wrappers.lambdaQuery(Chats.class)
				.eq(Chats::getSessionId, sessionId);
		return new PageVO<>(page(new Page<>(page, size), qp));
	}

	@Override
	public void sendChat(MessageDTO messageDTO, Integer userId, String message) {
		Integer sessionId = messageDTO.getSessionId();
		LambdaQueryWrapper<SessionUser> ne = Wrappers.lambdaQuery(SessionUser.class)
				.eq(SessionUser::getSessionId, sessionId)
				.ne(SessionUser::getUserId, userId);
		SessionUser one = sessionUserService.getOne(ne);
		if (one == null) {
			throw new RuntimeException("session not exist");
		}
		WsTools.sendOneMessage(one.getUserId(), message);
	}
}

