package www.raven.sw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import www.raven.sw.cache.UserInfoUtils;
import www.raven.sw.dao.SessionsDao;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.UserInfo;
import www.raven.sw.entity.po.SessionUser;
import www.raven.sw.entity.po.Sessions;
import www.raven.sw.entity.po.Users;
import www.raven.sw.service.SessionUserService;
import www.raven.sw.service.SessionsService;
import www.raven.sw.service.UsersService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 会话表(Sessions)表服务实现类
 *
 * @author makejava
 * @since 2024-11-08 01:10:40
 */
@Service("sessionsService")
public class SessionsServiceImpl extends ServiceImpl<SessionsDao, Sessions> implements SessionsService {
	@Resource
	private UsersService usersService;
	@Resource
	private SessionUserService sessionUserService;

	@Override
	public Sessions startChat(Integer toUserId) {
		Integer userId = UserInfoUtils.getUserId();
		String mixId = generateSessionMixId(userId, toUserId);
		LambdaQueryWrapper<Sessions> eq = Wrappers.lambdaQuery(Sessions.class)
				.eq(Sessions::getUserMixId, mixId);
		Sessions sessions = getOne(eq);
		if (sessions == null) {
			sessions = Sessions.builder()
					.userMixId(mixId)
					.sessionName(generateSessionName(toUserId))
					.build();
			save(sessions);
			sessionUserService.saveSessionUser(sessions, userId, toUserId);
		}
		return sessions;
	}

	@Override
	public PageVO<Sessions> getSessions(Integer page, Integer size) {
		LambdaQueryWrapper<SessionUser> eq = Wrappers.lambdaQuery(SessionUser.class)
				.eq(SessionUser::getUserId, UserInfoUtils.getUserId());
		Page<SessionUser> paged = sessionUserService.page(new Page<>(page, size), eq);
		List<Integer> sessionIds = paged.getRecords().stream().map(SessionUser::getSessionId).toList();
		List<Sessions> sessions = listByIds(sessionIds);
		List<Integer> toUserIds = sessions.stream().map(Sessions::getUserMixId)
				.map(SessionsServiceImpl::getOtherUserId)
				.toList();
		Map<Integer, UserInfo> users = usersService.listByIds(toUserIds).stream()
				.collect(Collectors.toMap(Users::getId, UserInfo::new));
		sessions.forEach(s -> s.setToUser(users.get(getOtherUserId(s.getUserMixId()))));
		return new PageVO<>(
				(int) paged.getTotal(),
				(int) paged.getPages(),
				paged.getCurrent(),
				(int) paged.getSize(),
				sessions
		);
	}

	public String generateSessionName(Integer toUserId) {
		Users byId = usersService.getById(toUserId);
		return "与" + byId.getUsername() + "的会话";
	}

	public static Integer getOtherUserId(String mixId) {
		String[] split = mixId.split("_");
		return split[0].equals(UserInfoUtils.getUserId().toString()) ? Integer.parseInt(split[1]) : Integer.parseInt(split[0]);
	}

	public static String generateSessionMixId(Integer userId, Integer toUserId) {
		return userId > toUserId ? userId + "_" + toUserId : toUserId + "_" + userId;
	}
}

