package www.raven.sw.entity.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * comment bo
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Data
@NoArgsConstructor
public class CommentBO {
	//话题ID
	private Integer topicId;
	//评论者ID
	private String content;
}
