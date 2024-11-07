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
import www.raven.sw.cache.UserInfoUtils;
import www.raven.sw.constant.TopicStatusEnum;
import www.raven.sw.entity.PageVO;
import www.raven.sw.entity.bo.ReviewBO;
import www.raven.sw.entity.bo.TopicBO;
import www.raven.sw.entity.po.Circles;
import www.raven.sw.entity.po.Topics;
import www.raven.sw.entity.po.Users;
import www.raven.sw.result.Result;
import www.raven.sw.service.CirclesService;
import www.raven.sw.service.TopicsService;

import java.util.List;

/**
 * 话题管理
 *
 * @author Rawven
 * @date 2024/11/07
 */
@RestController
@RequestMapping("api/topic/")
@ResponseBody
public class TopicController {
	@Resource
	private TopicsService topicsService;
	@Resource
	private CirclesService circlesService;

	/**
	 * 展示指定圈子的话题
	 *
	 * @param circleId 圈子ID
	 * @param page     页码
	 * @param size     页大小
	 */
	@GetMapping("/get")
	public Result<PageVO<Topics>> listTopic(
			@RequestParam Long circleId,
			@RequestParam Integer page,
			@RequestParam Integer size) {
		LambdaQueryWrapper<Topics> qp = Wrappers.lambdaQuery(Topics.class)
				.eq(Topics::getCircleId, circleId)
				.orderByDesc(Topics::getUpdatedAt);
		Page<Topics> data = topicsService.page(new Page<>(page, size), qp);
		return Result.operateSuccess("查询成功", new PageVO<>(data));
	}

	/**
	 * 获取指定用户的话题
	 *
	 * @param userId 用户ID
	 * @param page   页码
	 * @param size   页数
	 */
	@GetMapping("/getByUser")
	public Result<PageVO<Topics>> listSelfTopic(
			@RequestParam Long userId,
			@RequestParam Integer page,
			@RequestParam Integer size) {
		LambdaQueryWrapper<Topics> qp = Wrappers.lambdaQuery(Topics.class)
				.eq(Topics::getUserId, userId)
				.orderByDesc(Topics::getUpdatedAt);
		Page<Topics> data = topicsService.page(new Page<>(page, size), qp);
		return Result.operateSuccess("查询成功", new PageVO<>(data));
	}

	/**
	 * 获取需要用户审核的话题
	 * @param page 页码
	 * @param size 页大小
	 */
	@GetMapping("/getNeedReview")
	public Result<PageVO<Topics>> listNeedReviewTopic(
			@RequestParam Integer page,
			@RequestParam Integer size) {
		Users userInfo = UserInfoUtils.getUserInfo();
		Integer id = userInfo.getId();
		LambdaQueryWrapper<Circles> qpCircle = Wrappers.lambdaQuery(Circles.class)
				.eq(Circles::getCreatedBy, id);
		List<Circles> list = circlesService.list(qpCircle);
		List<Integer> circleIds = list.stream()
				.map(Circles::getId)
				.toList();
		LambdaQueryWrapper<Topics> qp = Wrappers.lambdaQuery(Topics.class)
				.eq(Topics::getCircleId,circleIds)
				.eq(Topics::getStatus, TopicStatusEnum.PENDING)
				.orderByDesc(Topics::getUpdatedAt);
		Page<Topics> data = topicsService.page(new Page<>(page, size), qp);
		return Result.operateSuccess("查询成功", new PageVO<>(data));
	}

	/**
	 * 发布话题
	 *
	 * @param topicsBO topics bo
	 */
	@PostMapping("/add")
	public Result<Void> addTopic(@RequestBody TopicBO topicsBO) {
		topicsService.createTopic(topicsBO);
		return Result.operateSuccess("添加成功");
	}

	/**
	 * 审核话题
	 *
	 * @param reviewBO review bo
	 */
	@PostMapping("/review")
	public Result<Void> reviewTopic(@RequestBody ReviewBO reviewBO) {
		topicsService.reviewTopic(reviewBO);
		return Result.operateSuccess("审核成功");
	}


}
