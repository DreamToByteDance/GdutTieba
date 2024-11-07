package www.raven.sw.util;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.lang.Assert;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import www.raven.sw.entity.dto.TokenDTO;
import www.raven.sw.entity.dto.TokenVO;

import java.util.HashMap;

import static www.raven.sw.constant.JwtConstant.TIME;
import static www.raven.sw.constant.JwtConstant.USER_ID;

/**
 * jwt util
 *
 * @author 刘家辉
 * @date 2023/11/20
 */
@Slf4j
public class JwtUtil {
	private static final String KEY = "后端我cnm";
	private static final Long EXPIRE_TIME = 1000 * 60 * 60 * 24L;
	private static final Long FRESH_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L;

	public static TokenVO createLoginToken(int userId) {
		HashMap<String, Object> map = new HashMap<>(3);
		map.put(USER_ID, userId);
		map.put(TIME, System.currentTimeMillis() + EXPIRE_TIME);
		String accessToken = JWTUtil.createToken(map, KEY.getBytes());
		map.put(TIME, System.currentTimeMillis() + FRESH_EXPIRE_TIME);
		String refreshToken = JWTUtil.createToken(map, KEY.getBytes());
		return TokenVO.builder().accessToken(accessToken)
				.refreshToken(refreshToken).userId(userId).build();
	}

	public static String refreshAccessToken(String refreshToken) {
		Assert.isTrue(JWTUtil.verify(refreshToken, KEY.getBytes()), "token验证失败");
		JWTPayload payload = JWTUtil.parseToken(refreshToken).getPayload();
		NumberWithFormat userId = (NumberWithFormat) payload.getClaim(USER_ID);
		NumberWithFormat expireTime = (NumberWithFormat) payload.getClaim(TIME);
		Assert.isTrue(System.currentTimeMillis() < expireTime.longValue(), "token已过期");
		HashMap<String, Object> map = new HashMap<>(3);
		map.put(USER_ID, userId.intValue());
		map.put(TIME, System.currentTimeMillis() + EXPIRE_TIME);
		return JWTUtil.createToken(map, KEY.getBytes());
	}

	public static TokenDTO parseToken(String token) {
		Assert.isTrue(JWTUtil.verify(token, KEY.getBytes()), "token验证失败");
		JWTPayload payload = JWTUtil.parseToken(token).getPayload();
		NumberWithFormat expireTime = (NumberWithFormat) payload.getClaim(TIME);
		NumberWithFormat userId = (NumberWithFormat) payload.getClaim(USER_ID);
		return new TokenDTO().setUserId(userId.intValue())
				.setExpireTime(expireTime.longValue());
	}

	public static boolean isExpire(TokenDTO token) {
		return System.currentTimeMillis() > token.getExpireTime();
	}
}
