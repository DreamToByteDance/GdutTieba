package www.raven.sw.entity;

import lombok.Data;
import www.raven.sw.entity.po.Users;

/**
 * user info
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Data
public class UserInfo {
	private Integer id;
	private String username;
	private String profile;
	public UserInfo (Users users){
		this.id = users.getId();
		this.username = users.getUsername();
		this.profile = users.getProfile();
	}
}
