package www.raven.sw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.dto.MessageDTO;
import www.raven.sw.entity.po.Chats;

/**
 * 聊天表(Chats)表服务接口
 *
 * @author makejava
 * @since 2024-11-07 13:28:57
 */
public interface ChatsService extends IService<Chats> {

	void saveChat(MessageDTO messageDTO, Integer userId);

	PageVO<Chats> historyChat(Long toUserId, Integer page, Integer size);

	void sendChat(MessageDTO messageDTO, Integer userId, String message);
}

