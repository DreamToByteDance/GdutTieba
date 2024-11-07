package www.raven.sw.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import www.raven.sw.cache.UserInfoCache;
import www.raven.sw.cache.UserInfoUtils;
import www.raven.sw.constant.RoleEnum;
import www.raven.sw.dao.UsersDao;
import www.raven.sw.entity.dto.TokenVO;
import www.raven.sw.entity.bo.RegisterBO;
import www.raven.sw.entity.po.Users;
import www.raven.sw.exception.BizException;
import www.raven.sw.service.UsersService;
import org.springframework.stereotype.Service;
import www.raven.sw.util.JwtUtil;

/**
 * 用户信息表(Users)表服务实现类
 *
 * @author makejava
 * @since 2024-11-07 13:28:59
 */
@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, Users> implements UsersService {
	@Resource
	private UserInfoCache userInfoCache;

	@Override
	public TokenVO login(String username, String password) {
		LambdaQueryWrapper<Users> eq = Wrappers.<Users>lambdaQuery()
				.eq(Users::getUsername, username)
				.eq(Users::getPassword, password);
		Users users = getOne(eq);
		Assert.notNull(users, "用户名或密码错误");
		userInfoCache.addUserInfo(users.getId(), users);
		return JwtUtil.createLoginToken(users.getId());

	}

	@Override
	public TokenVO register(RegisterBO registerBO) {
		Users users = new Users();
		users.setUsername(registerBO.getUsername());
		//用户名不能重复
		LambdaQueryWrapper<Users> eq = Wrappers.<Users>lambdaQuery()
				.eq(Users::getUsername, registerBO.getUsername());
		Users one = getOne(eq);
		Assert.isNull(one, "用户名已存在");
		users.setPassword(registerBO.getPassword());
		users.setEmail(registerBO.getEmail());
		users.setRole(RoleEnum.USER);
		save(users);
		userInfoCache.addUserInfo(users.getId(), users);
		return JwtUtil.createLoginToken(users.getId());
	}

	@Override
	public void isAdmin() {
		Users userInfo = UserInfoUtils.getUserInfo();
		if (userInfo == null || !RoleEnum.ADMIN.name().toLowerCase().equals(userInfo.getRole())) {
			throw new BizException("无权限");
		}
	}

	@Override
	public void upgrade() {
		Users userInfo = UserInfoUtils.getUserInfo();
		Users users = getById(userInfo.getId());
		users.setRole(RoleEnum.ADMIN);
		updateById(users);
		userInfoCache.addUserInfo(users.getId(), users);
	}

}

