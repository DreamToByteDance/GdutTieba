package www.raven.sw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.po.Sessions;

/**
 * 会话表(Sessions)表服务接口
 *
 * @author makejava
 * @since 2024-11-08 01:10:40
 */
public interface SessionsService extends IService<Sessions> {

	Sessions startChat(Integer toUserId);

	PageVO<Sessions> getSessions(Integer page, Integer size);
}

