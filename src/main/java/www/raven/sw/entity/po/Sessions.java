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
 * 会话表(Sessions)表实体类
 *
 * @author makejava
 * @since 2024-11-08 01:10:40
 */
@TableName("sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sessions {
	//会话ID
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	//用户组合ID
	private String userMixId;
	//会话名称（例如，用户A与用户B的私聊）
	private String sessionName;
	//创建时间
	private Date createdAt;
	//更新时间
	private Date updatedAt;
	/**
	 * 对方的用户信息
	 */
	@TableField(exist = false)
	private UserInfo toUser;

}

