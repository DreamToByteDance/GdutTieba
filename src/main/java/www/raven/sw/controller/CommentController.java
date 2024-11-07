package www.raven.sw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import www.raven.sw.entity.bo.CommentBO;
import www.raven.sw.entity.po.Comments;
import www.raven.sw.result.Result;
import www.raven.sw.service.CommentsService;

import java.util.List;

/**
 * 评论管理
 *
 * @author Rawven
 * @date 2024/11/07
 */
@RestController
@RequestMapping("api/comment/")
@ResponseBody
public class CommentController {
	@Resource
	private CommentsService commentsService;

	/**
	 * 获取指定话题的评论
	 *
	 * @param topicId 话题ID
	 */
	@GetMapping("/get")
	public Result<List<Comments>> listComment(@RequestParam Long topicId) {
		LambdaQueryWrapper<Comments> qp = Wrappers.lambdaQuery(Comments.class)
				.eq(Comments::getTopicId, topicId);
		List<Comments> list = commentsService.list(qp);
		return
				Result.operateSuccess("查询成功", list);
	}

	/**
	 * 对话题进行评论
	 *
	 * @param commentBO comment bo
	 */
	@PostMapping("/add")
	public Result<Void> addComment(@RequestBody CommentBO commentBO) {
		commentsService.commentAdd(commentBO);
		return Result.operateSuccess("添加成功");
	}
}
