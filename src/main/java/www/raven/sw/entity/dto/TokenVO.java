package www.raven.sw.entity.dto;

import lombok.Builder;
import lombok.Data;

/**
 * token vo
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Data
@Builder
public class TokenVO {
	//用户id
	private int userId;
	//权限token
	private String accessToken;
	//刷新token
	private String refreshToken;
}
