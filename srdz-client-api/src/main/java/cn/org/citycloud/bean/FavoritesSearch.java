package cn.org.citycloud.bean;

import javax.validation.constraints.Min;

public class FavoritesSearch extends SliceAndSort {
	
	@Min(1)
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
