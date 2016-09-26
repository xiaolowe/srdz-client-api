package cn.org.citycloud.bean;

public class DistShopGoodsSearch extends SliceAndSort {

	private int memberId;

	private String keyword;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
