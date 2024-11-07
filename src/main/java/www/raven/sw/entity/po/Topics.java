package www.raven.sw.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import www.raven.sw.entity.UserInfo;

import java.util.Date;

/**
 * 话题表(Topics)表实体类
 *
 * @author makejava
 * @since 2024-11-07 13:28:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@TableName("topics")
public class Topics {
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	//圈ID，默认校园圈
	private Integer circleId;
	//发布者ID
	private Integer userId;
	//话题标题
	private String title;
	//话题内容
	private String content;
	//话题媒体数据
	private String media;
	//审核状态
	private Object status;
	//发布时间
	private Date createdAt;
	//更新时间
	private Date updatedAt;
	//用户信息
	@TableField(exist = false)
	private UserInfo userInfo;
}

