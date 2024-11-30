package www.raven.sw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import www.raven.sw.cache.UserInfoUtils;
import www.raven.sw.dao.CirclesDao;
import www.raven.sw.entity.bo.CirclesBO;
import www.raven.sw.entity.po.Circles;
import www.raven.sw.entity.po.Users;
import www.raven.sw.service.CirclesService;

/**
 * 主题圈表(Circles)表服务实现类
 *
 * @author makejava
 * @since 2024-11-07 13:28:58
 */
@Service("circlesService")
public class CirclesServiceImpl extends ServiceImpl<CirclesDao, Circles> implements CirclesService {
	@Override
	public void createCircles(CirclesBO circlesBO) {
		Users userInfo = UserInfoUtils.getUserInfo();
		Circles build = Circles.builder()
				.name(circlesBO.getCircleName())
				.profile(circlesBO.getCircleProfile())
				.description(circlesBO.getCircleDescription())
				.createdBy(userInfo.getId())
				.build();
		this.save(build);
	}
}

