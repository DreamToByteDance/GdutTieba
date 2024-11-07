package www.raven.sw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import www.raven.sw.cache.UserInfoUtils;
import www.raven.sw.dao.CommentsDao;
import www.raven.sw.entity.bo.CommentBO;
import www.raven.sw.entity.po.Comments;
import www.raven.sw.entity.po.Users;
import www.raven.sw.service.CommentsService;
import org.springframework.stereotype.Service;

/**
 * 评论表(Comments)表服务实现类
 *
 * @author makejava
 * @since 2024-11-07 13:28:58
 */
@Service("commentsService")
public class CommentsServiceImpl extends ServiceImpl<CommentsDao, Comments> implements CommentsService {

	@Override
	public void commentAdd(CommentBO commentBO) {
		Users userInfo = UserInfoUtils.getUserInfo();
		Comments build = Comments.builder()
				.topicId(commentBO.getTopicId())
				.userId(userInfo.getId())
				.content(commentBO.getContent())
				.build();
		this.save(build);
	}

}

