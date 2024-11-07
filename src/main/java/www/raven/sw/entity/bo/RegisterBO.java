package www.raven.sw.entity.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * register bo
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Data
@NoArgsConstructor
public class RegisterBO {
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像
	 */
	private String profile;
}
