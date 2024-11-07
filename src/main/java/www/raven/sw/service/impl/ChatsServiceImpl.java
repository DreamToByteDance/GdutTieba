package www.raven.sw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import www.raven.sw.cache.UserInfoUtils;
import www.raven.sw.dao.ChatsDao;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.dto.MessageDTO;
import www.raven.sw.entity.po.Chats;
import www.raven.sw.entity.po.Users;
import www.raven.sw.service.ChatsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天表(Chats)表服务实现类
 *
 * @author makejava
 * @since 2024-11-07 13:28:57
 */
@Service("chatsService")
public class ChatsServiceImpl extends ServiceImpl<ChatsDao, Chats> implements ChatsService {

	@Override
	public void saveChat(MessageDTO messageDTO, Integer userId) {
		Chats build = Chats.builder()
				.message(messageDTO.getContent())
				.senderId(userId)
				.receiverId(messageDTO.getToUserId())
				.build();
		this.save(build);
	}

	@Override
	public PageVO<Chats> historyChat(Long toUserId, Integer page, Integer size) {
		Users userInfo = UserInfoUtils.getUserInfo();
		LambdaQueryWrapper<Chats> qp = Wrappers.<Chats>lambdaQuery()
				.eq(Chats::getSenderId, userInfo.getId())
				.eq(Chats::getReceiverId, toUserId)
				.or()
				.eq(Chats::getSenderId, toUserId)
				.eq(Chats::getReceiverId, userInfo.getId())
				.orderByDesc(Chats::getSentAt);
		return new PageVO<>(page(new Page<>(page, size), qp));
	}
}

