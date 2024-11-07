package www.raven.sw.entity.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rawven
 * @date 2024/11/07
 */
@Data
@NoArgsConstructor
public class ReviewBO {

	/**
	 * 话题ID
	 */
	private Long topicId;
	/**
	 * 审核结果  枚举: APPROVED,REJECTED; (同意,拒绝)
	 */
	private String status;
}
