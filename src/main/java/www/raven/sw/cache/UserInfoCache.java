package www.raven.sw.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import www.raven.sw.entity.po.Users;

import java.util.concurrent.TimeUnit;

/**
 * user info cache
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Component
public class UserInfoCache {

	private final Cache<Integer, Users> userCache;

	public UserInfoCache() {
		userCache = CacheBuilder.newBuilder()
				.expireAfterWrite(24, TimeUnit.HOURS)
				.maximumSize(1000)
				.build();
	}

	public void addUserInfo(Integer userId, Users userInfo) {
		userCache.put(userId, userInfo);
	}

	public Users getUserInfo(Integer userId) {
		return userCache.getIfPresent(userId);
	}

	public void removeUserInfo(Integer userId) {
		userCache.invalidate(userId);
	}

	public void clearCache() {
		userCache.invalidateAll();
	}
}
