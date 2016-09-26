package cn.org.citycloud.bean;

/**
 * 接口查询参数（切片和分页）Bean
 * 
 * @author lanbo
 *
 */
public class SliceAndSort {

	// Slice
	private int page = 0;

	private int size = 20;

	// Sort
	private String sort;

	// DESC ASC
	private String order;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
