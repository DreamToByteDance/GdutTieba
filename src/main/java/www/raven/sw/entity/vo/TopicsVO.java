package www.raven.sw.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import www.raven.sw.entity.po.Comments;
import www.raven.sw.entity.po.Topics;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicsVO {
	private Topics topics;
	private List<Comments> comments;
}
