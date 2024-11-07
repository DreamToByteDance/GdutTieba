package www.raven.sw.entity.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 主题圈表(Circles)表实体类
 *
 * @author makejava
 * @since 2024-11-07 13:28:57
 */
@EqualsAndHashCode(callSuper = true)
@TableName("circles")
@Data
@Builder
public class Circles extends Model<Circles> {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	//圈名称
	private String name;
	//圈描述
	private String description;
	//创建者ID
	private Integer createdBy;
	//创建时间
	private Date createdAt;

}

