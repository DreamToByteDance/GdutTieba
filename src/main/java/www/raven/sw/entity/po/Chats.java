package www.raven.sw.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 聊天表(Chats)表实体类
 *
 * @author makejava
 * @since 2024-11-07 13:28:57
 */
@TableName("chats")
@Data
@Builder
public class Chats {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer senderId;
	private Integer sessionId;
	private String message;
	private Date sentAt;
}

