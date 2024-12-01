package www.raven.sw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import www.raven.sw.entity.bo.RegisterBO;
import www.raven.sw.entity.dto.TokenVO;
import www.raven.sw.entity.po.Users;

/**
 * 用户信息表(Users)表服务接口
 *
 * @author makejava
 * @since 2024-11-07 13:28:59
 */
public interface UsersService extends IService<Users> {
	TokenVO login(String username, String password);

	TokenVO register(RegisterBO registerBO);

	void isAdmin();

	void upgrade();

	Users getSelfInfo();
}

