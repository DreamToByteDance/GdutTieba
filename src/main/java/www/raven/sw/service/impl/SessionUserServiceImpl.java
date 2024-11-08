package www.raven.sw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import www.raven.sw.dao.SessionUserDao;
import www.raven.sw.entity.po.SessionUser;
import www.raven.sw.entity.po.Sessions;
import www.raven.sw.service.SessionUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (SessionUser)表服务实现类
 *
 * @author makejava
 * @since 2024-11-08 09:20:18
 */
@Service("sessionUserService")
public class SessionUserServiceImpl extends ServiceImpl<SessionUserDao, SessionUser> implements SessionUserService {

	@Override
	public void saveSessionUser(Sessions sessions, Integer userId, Integer toUserId) {
		SessionUser build = SessionUser.builder()
				.sessionId(sessions.getId())
				.userId(userId)
				.build();
		SessionUser build2 = SessionUser.builder()
				.sessionId(sessions.getId())
				.userId(toUserId)
				.build();
		saveBatch(List.of(build, build2));
	}
}

