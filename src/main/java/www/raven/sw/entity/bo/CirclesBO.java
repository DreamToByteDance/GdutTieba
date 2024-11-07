package www.raven.sw.entity.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * circles bo
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Data
@NoArgsConstructor
public class CirclesBO {
	//主题圈名称
	private String circleName;
	//主题圈描述
	private String circleDescription;
}
