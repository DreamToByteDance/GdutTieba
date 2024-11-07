package www.raven.sw.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 用户信息表(Users)表实体类
 *
 * @author makejava
 * @since 2024-11-07 13:28:59
 */
@Data
@SuperBuilder
@NoArgsConstructor
@TableName("users")
public class Users {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	//用户名
	private String username;
	//密码
	private String password;
	//邮箱
	private String email;
	//用户角色
	private Object role;
	//头像
	private String profile;
	//注册时间
	private Date createdAt;
	//更新时间
	private Date updatedAt;
}

