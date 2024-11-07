package www.raven.sw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.bo.CirclesBO;
import www.raven.sw.entity.po.Circles;
import www.raven.sw.result.Result;
import www.raven.sw.service.CirclesService;
import www.raven.sw.service.UsersService;

/**
 * 圈子管理
 *
 * @author Rawven
 * @date 2024/11/07
 */
@RestController
@RequestMapping("api/circle/")
@ResponseBody
public class CircleController {
	@Resource
	private CirclesService circlesService;
	@Resource
	private UsersService usersService;


	/**
	 * 展示所有圈子
	 *
	 * @param page 页码
	 * @param size 页大小
	 */
	@GetMapping("/get")
	public Result<PageVO<Circles>> listCircle(
			@RequestParam Integer page,
			@RequestParam Integer size) {
		Page<Circles> data = circlesService.page(new Page<>(page, size));
		return Result.operateSuccess("查询成功", new PageVO<>(data));
	}

	/**
	 * 获取用户创建的圈子
	 *
	 * @param userId 用户ID
	 * @param page   页码
	 * @param size   页大小
	 */
	@GetMapping("/getByUser")
	public Result<PageVO<Circles>> listCircleByUser(
			@RequestParam Long userId,
			@RequestParam Integer page,
			@RequestParam Integer size) {
		LambdaQueryWrapper<Circles> qp = Wrappers.lambdaQuery(Circles.class)
				.eq(Circles::getCreatedBy, userId)
				.orderByDesc(Circles::getCreatedAt);
		Page<Circles> data = circlesService.page(new Page<>(page, size), qp);
		return Result.operateSuccess("查询成功", new PageVO<>(data));
	}

	/**
	 * 创建圈子 仅管理员
	 *
	 * @param circlesBO circles bo
	 */
	@PostMapping("/add")
	public Result<Void> createCircle(@RequestBody CirclesBO circlesBO) {
		usersService.isAdmin();
		circlesService.createCircles(circlesBO);
		return Result.operateSuccess("创建成功");
	}


}
