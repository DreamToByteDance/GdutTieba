package www.raven.sw.entity.dto;

import lombok.Data;

@Data
public class MessageDTO {
	private String content;
	private Integer sessionId;
}
