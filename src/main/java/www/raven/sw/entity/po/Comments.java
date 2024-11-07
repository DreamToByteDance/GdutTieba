package www.raven.sw.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import www.raven.sw.entity.UserInfo;

import java.util.Date;

/**
 * 评论表(Comments)表实体类
 *
 * @author makejava
 * @since 2024-11-07 13:28:58
 */
@TableName("comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	//话题ID
	private Integer topicId;
	//评论者ID
	private Integer userId;
	//评论内容
	private String content;
	//评论时间
	private Date createdAt;
	//用户信息
	@TableField(exist = false)
	private UserInfo userInfo;

}

