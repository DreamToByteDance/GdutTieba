package www.raven.sw.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * token信息
 *
 * @author 刘家辉
 * @date 2023/11/27
 */
@Data
@Accessors(chain = true)
public class TokenDTO {

	private Integer userId;
	private Long expireTime;
}
