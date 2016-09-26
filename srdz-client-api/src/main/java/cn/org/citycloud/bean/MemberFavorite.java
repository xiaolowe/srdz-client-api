package cn.org.citycloud.bean;

import javax.validation.constraints.Min;

/**
 * 我的收藏提交Bean
 * 
 * @author lanbo
 *
 */
public class MemberFavorite {

	@Min(1)
	private int id;

	@Min(1)
	private int favoriteType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFavoriteType() {
		return favoriteType;
	}

	public void setFavoriteType(int favoriteType) {
		this.favoriteType = favoriteType;
	}

}
