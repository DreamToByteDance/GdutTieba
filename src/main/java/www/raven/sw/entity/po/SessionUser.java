package www.raven.sw.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (SessionUser)表实体类
 *
 * @author makejava
 * @since 2024-11-08 09:20:18
 */
@TableName("session_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionUser extends Model<SessionUser> {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private Integer sessionId;

	private Integer userId;
	//创建时间
	private Date createdAt;
	//更新时间
	private Date updatedAt;
}

