package www.raven.sw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import www.raven.sw.entity.bo.CirclesBO;
import www.raven.sw.entity.po.Circles;

/**
 * 主题圈表(Circles)表服务接口
 *
 * @author makejava
 * @since 2024-11-07 13:28:57
 */
public interface CirclesService extends IService<Circles> {

	void createCircles(CirclesBO circlesBO);
}

