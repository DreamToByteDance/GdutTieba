package www.raven.sw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;
import www.raven.sw.entity.po.SessionUser;
import www.raven.sw.entity.po.Sessions;

/**
 * (SessionUser)表服务接口
 *
 * @author makejava
 * @since 2024-11-08 09:20:18
 */
public interface SessionUserService extends IService<SessionUser> {
    @Async
	void saveSessionUser(Sessions sessions, Integer userId, Integer toUserId);
	
}

