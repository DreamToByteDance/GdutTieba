package www.raven.sw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.RequestParam;
import www.raven.sw.entity.bo.CommentBO;
import www.raven.sw.entity.po.Comments;

import java.util.List;

/**
 * 评论表(Comments)表服务接口
 *
 * @author makejava
 * @since 2024-11-07 13:28:58
 */
public interface CommentsService extends IService<Comments> {

	void commentAdd(CommentBO commentBO);

	List<Comments> getCommentByTopic(Integer topicId);
}

