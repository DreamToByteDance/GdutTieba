package www.raven.sw.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * page vo
 *
 * @author Rawven
 * @date 2024/11/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {
	// 当前页码
	private int currentPage;

	// 每页条数
	private int pageSize;

	// 总条数
	private long totalCount;

	// 总页数
	private int totalPages;

	// 当前页数据列表
	private List<T> data;

	public PageVO(Page<T> page) {
		this.currentPage = (int) page.getCurrent();
		this.pageSize = (int) page.getSize();
		this.totalCount = page.getTotal();
		this.data = page.getRecords();
		this.totalPages = (int) page.getPages();
	}

}
