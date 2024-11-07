package www.raven.sw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import www.raven.sw.cache.UserInfoUtils;
import www.raven.sw.constant.TopicStatusEnum;
import www.raven.sw.dao.TopicsDao;
import www.raven.sw.entity.bo.ReviewBO;
import www.raven.sw.entity.bo.TopicBO;
import www.raven.sw.entity.po.Topics;
import www.raven.sw.entity.po.Users;
import www.raven.sw.exception.BizException;
import www.raven.sw.service.TopicsService;

/**
 * 话题表(Topics)表服务实现类
 *
 * @author makejava
 * @since 2024-11-07 13:28:58
 */
@Service("topicsService")
public class TopicsServiceImpl extends ServiceImpl<TopicsDao, Topics> implements TopicsService {
	private static final int DEFAULT_CIRCLE_ID = 1;

	@Override
	public void createTopic(TopicBO topicBO) {
		Users userInfo = UserInfoUtils.getUserInfo();
		Topics build = Topics.builder()
				.title(topicBO.getTitle())
				.circleId(topicBO.getCircleId() == null ? DEFAULT_CIRCLE_ID : topicBO.getCircleId())
				.content(topicBO.getContent())
				.status(TopicStatusEnum.PENDING)
				.userId(userInfo.getId())
				.media(topicBO.getMedia())
				.build();
		save(build);
	}

	@Override
	public void reviewTopic(ReviewBO reviewBO) {
		Topics topics = getById(reviewBO.getTopicId());
		TopicStatusEnum status = TopicStatusEnum.getByName(reviewBO.getStatus());
		if (status == null) {
			throw new BizException("status is not valid");
		}
		topics.setStatus(status);
		updateById(topics);
	}
}

