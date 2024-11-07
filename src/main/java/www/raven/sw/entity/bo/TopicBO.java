package www.raven.sw.entity.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * topic bo
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Data
@NoArgsConstructor
public class TopicBO {
	//圈ID，默认校园圈
	private Integer circleId;
	//话题标题
	private String title;
	//话题内容
	private String content;
	//话题媒体数据
	private String media
	;
}
