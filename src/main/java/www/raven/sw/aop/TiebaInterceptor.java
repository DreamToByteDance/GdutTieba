package www.raven.sw.aop;

import com.google.common.base.Objects;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import www.raven.sw.cache.UserInfoCache;
import www.raven.sw.cache.UserInfoUtils;
import www.raven.sw.entity.dto.TokenDTO;
import www.raven.sw.entity.po.Users;
import www.raven.sw.exception.BizException;
import www.raven.sw.service.UsersService;
import www.raven.sw.util.JwtUtil;


/**
 * tieba interceptor
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Component
@Slf4j
public class TiebaInterceptor implements HandlerInterceptor {
	private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	private static final String FRESH_TOKEN = "FRESH_TOKEN";
	private static final String 后门_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
			".eyJ1c2VySWQiOjMsImV4cGlyZVRpbWUiOjE3MzEwNTk1NTg1MzN9.BdEu4zXOQspstp-AnmVq-Da0OiZvG9ct3ReX01_7lYg";
	@Resource
	private UserInfoCache userInfoCache;
	@Resource
	private UsersService usersService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if(request.getMethod().equals(HttpMethod.OPTIONS.name())){
			return true;
		}
		String token = request.getHeader(ACCESS_TOKEN);
		if(token == null || token.isEmpty()){
			throw new BizException("token为空,未登录!");
		}
		//方便开发测试的后门
		if (token.equals(后门_TOKEN)) {
			log.info("开发走后门");
			usersService.getById(3);
			UserInfoUtils.setUserInfo(usersService.getById(3));
			return true;
		}

		TokenDTO tokenDTO = JwtUtil.parseToken(token);
		//判断是否过期
		if (JwtUtil.isExpire(tokenDTO)) {
			String freshToken = request.getHeader(FRESH_TOKEN);
			String newAccessToken = JwtUtil.refreshAccessToken(freshToken);
			response.setHeader(ACCESS_TOKEN, newAccessToken);
			tokenDTO = JwtUtil.parseToken(newAccessToken);
		}
		Integer userId = tokenDTO.getUserId();
		Users userInfo = userInfoCache.getUserInfo(userId);
		UserInfoUtils.setUserInfo(userInfo);
		return true;
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		UserInfoUtils.removeUserInfo();
	}
}
