package www.raven.sw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import www.raven.sw.entity.bo.ReviewBO;
import www.raven.sw.entity.bo.TopicBO;
import www.raven.sw.entity.po.Topics;

/**
 * 话题表(Topics)表服务接口
 *
 * @author makejava
 * @since 2024-11-07 13:28:58
 */
public interface TopicsService extends IService<Topics> {

	void createTopic(TopicBO topicBO);

	void reviewTopic(ReviewBO reviewBO);
}

