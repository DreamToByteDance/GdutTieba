package www.raven.sw.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import www.raven.sw.entity.bo.LoginBO;
import www.raven.sw.entity.bo.RegisterBO;
import www.raven.sw.entity.dto.TokenVO;
import www.raven.sw.entity.po.Users;
import www.raven.sw.result.Result;
import www.raven.sw.service.UsersService;

/**
 * 用户管理
 *
 * @author Rawven
 * @date 2024/11/07
 */
@RestController
@RequestMapping("api/user/")
@ResponseBody
public class UserController {
	@Resource
	private UsersService usersService;

	/**
	 * 登录
	 *
	 * @param loginBO login bo
	 */
	@PostMapping("login")
	public Result<TokenVO> login(@RequestBody LoginBO loginBO) {
		return Result.operateSuccess("登录成功", usersService.login(
				loginBO.getUsername(), loginBO.getPassword()
		));
	}

	/**
	 * 注册
	 *
	 * @param registerBO register bo
	 */
	@PostMapping("register")
	public Result<TokenVO> register(@RequestBody RegisterBO registerBO) {
		return Result.operateSuccess("注册成功", usersService.register(registerBO));
	}

	/**
	 * 获取用户信息
	 *
	 * @param id 用户ID
	 * @return {@link Result }<{@link Void }>
	 */
	@GetMapping("/{id}")
	public Result<Users> getUserInfo(@PathVariable("id") Long id) {
		return Result.operateSuccess("获取用户信息成功", usersService.getById(id));
	}

	/**
	 * 用户升级为管理员
	 */
	@PostMapping("upgrade")
	public Result<Void> upgrade() {
		usersService.upgrade();
		return Result.operateSuccess("升级成功");
	}


}
