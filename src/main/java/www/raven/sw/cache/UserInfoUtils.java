package www.raven.sw.cache;

import www.raven.sw.entity.po.Users;

/**
 * user info utils
 *
 * @author Rawven
 * @date 2024/11/07
 */
public class UserInfoUtils {
	private static final ThreadLocal<Users> USERS = new ThreadLocal<>();

	public static Users getUserInfo() {
		return USERS.get();
	}

	public static void setUserInfo(Users userInfo) {
		USERS.set(userInfo);
	}

	public static void removeUserInfo() {
		USERS.remove();
	}
}
