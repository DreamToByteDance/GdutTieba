package www.raven.sw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
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
import www.raven.sw.entity.UserInfo;
import www.raven.sw.entity.bo.ReviewBO;
import www.raven.sw.entity.bo.TopicBO;
import www.raven.sw.entity.po.Circles;
import www.raven.sw.entity.po.Comments;
import www.raven.sw.entity.po.Topics;
import www.raven.sw.entity.po.Users;
import www.raven.sw.entity.vo.CirclesTopicsVO;
import www.raven.sw.entity.vo.TopicsVO;
import www.raven.sw.result.Result;
import www.raven.sw.service.CirclesService;
import www.raven.sw.service.CommentsService;
import www.raven.sw.service.TopicsService;
import www.raven.sw.service.UsersService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	@Resource
	private CommentsService commentsService;
	@Resource
	private UsersService usersService;

	/**
	 * 展示主页(展示所有圈子审核通过的话题及其评论)
	 *
	 * @param page    page
	 * @param size    size
	 * @param keyword 标题关键字(模糊查询)
	 */
	@GetMapping("/getForMainPage")
	public Result<PageVO<CirclesTopicsVO>> listTopicForMainPage(
			@RequestParam Integer page,
			@RequestParam Integer size,
			@RequestParam(required = false) String keyword) {
		// 合并查询 Topics 和 Circles
		LambdaQueryWrapper<Topics> topicsQuery = Wrappers.lambdaQuery(Topics.class)
				.ne(Topics::getStatus, TopicStatusEnum.PENDING)
				.orderByDesc(Topics::getUpdatedAt);
		if (!Strings.isNullOrEmpty(keyword)) {
			topicsQuery.like(Topics::getTitle, keyword);
		}

		// 获取分页数据并一次性查询所需的 Circles 数据
		Page<Topics> data = topicsService.page(new Page<>(page, size), topicsQuery);
		List<Integer> circleIds = data.getRecords().stream().map(Topics::getCircleId).collect(Collectors.toList());
		Map<Integer, Circles> circlesMap = circlesService.listByIds(circleIds).stream()
				.collect(Collectors.toMap(Circles::getId, Function.identity()));

		// 合并查询 Comments 和 Users
		List<Integer> topicIds = data.getRecords().stream().map(Topics::getId).collect(Collectors.toList());
		LambdaQueryWrapper<Comments> commentsQuery = Wrappers.lambdaQuery(Comments.class)
				.in(Comments::getTopicId, topicIds);
		List<Comments> commentsList = commentsService.list(commentsQuery);

		// 获取所有评论的用户ID
		List<Integer> userIds = new ArrayList<>();
		userIds.addAll(data.getRecords().stream().map(Topics::getUserId).collect(Collectors.toList()));
		userIds.addAll(commentsList.stream().map(Comments::getUserId).collect(Collectors.toList()));

		// 批量查询 Users 数据
		Map<Integer, UserInfo> usersMap = usersService.listByIds(userIds).stream()
				.collect(Collectors.toMap(Users::getId, UserInfo::new));

		// 将评论和话题信息合并到 VO 中
		List<CirclesTopicsVO> topicsVOS = new ArrayList<>(data.getRecords().size());
		for (Topics topic : data.getRecords()) {
			// 获取与话题相关的评论并设置用户信息
			List<Comments> topicComments = commentsList.stream()
					.filter(comment -> comment.getTopicId().equals(topic.getId()))
					.peek(comment -> comment.setUserInfo(usersMap.get(comment.getUserId())))
					.collect(Collectors.toList());

			// 设置话题的用户信息
			topic.setUserInfo(usersMap.get(topic.getUserId()));

			// 构建 VO 对象
			topicsVOS.add(new CirclesTopicsVO(circlesMap.get(topic.getCircleId()), new TopicsVO(topic, topicComments)));
		}

		return Result.operateSuccess("查询成功", new PageVO<>(
				(int) data.getCurrent(),
				(int) data.getSize(),
				data.getTotal(),
				(int) data.getPages(),
				topicsVOS
		));
	}


	/**
	 * 展示指定圈子的主页(展示审核通过的话题及其评论)
	 *
	 * @param circleId 圈子ID
	 * @param page     页码
	 * @param size     页大小
	 * @param keyword  标题关键字(模糊查询)
	 */
	@GetMapping("/getToShowPage")
	public Result<PageVO<TopicsVO>> listTopic(
			@RequestParam Long circleId,
			@RequestParam Integer page,
			@RequestParam Integer size,
			@RequestParam(required = false) String keyword) {
		// 构建查询条件
		LambdaQueryWrapper<Topics> qp = Wrappers.lambdaQuery(Topics.class)
				.eq(Topics::getCircleId, circleId)
				.ne(Topics::getStatus, TopicStatusEnum.PENDING)
				.orderByDesc(Topics::getUpdatedAt);
		if (!Strings.isNullOrEmpty(keyword)) {
			qp.like(Topics::getTitle, keyword);
		}
		// 分页查询Topic数据
		Page<Topics> data = topicsService.page(new Page<>(page, size), qp);
		// 获取所有话题ID
		List<Integer> topicIds = data.getRecords().stream()
				.map(Topics::getId)
				.collect(Collectors.toList());
		// 批量查询评论数据，并根据话题ID分组
		LambdaQueryWrapper<Comments> in = Wrappers.lambdaQuery(Comments.class)
				.in(Comments::getTopicId, topicIds);
		Map<Integer, List<Comments>> commentsMap = commentsService.list(in)
				.stream()
				.collect(Collectors.groupingBy(Comments::getTopicId));
		// 将查询到的Topic数据转换为TopicsVO，并合并评论数据
		List<TopicsVO> topicsVOS = new ArrayList<>(data.getRecords().size());
		List<Integer> userIds = data.getRecords().stream()
				.map(Topics::getUserId)
				.collect(Collectors.toList());
		userIds.addAll(commentsMap.values().stream()
				.flatMap(List::stream)
				.map(Comments::getUserId)
				.toList());
		Map<Integer, UserInfo> usersMap = usersService.listByIds(userIds)
				.stream()
				.collect(Collectors.toMap(Users::getId, UserInfo::new));
		data.getRecords().forEach(topics -> {
			List<Comments> commentsList = commentsMap.getOrDefault(topics.getId(), Collections.emptyList());
			commentsList.forEach(comments -> comments.setUserInfo(usersMap.get(comments.getUserId())));
			topics.setUserInfo(usersMap.get(topics.getUserId()));
			topicsVOS.add(new TopicsVO(topics, commentsList));
		});
		return Result.operateSuccess("查询成功", new PageVO<>(
				(int) data.getCurrent(),
				(int) data.getSize(),
				data.getTotal(),
				(int) data.getPages(),
				topicsVOS
		));
	}


	/**
	 * 获取指定用户的话题(无审核过滤,仅话题)
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
	 *
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
				.in(Topics::getCircleId, circleIds)
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
