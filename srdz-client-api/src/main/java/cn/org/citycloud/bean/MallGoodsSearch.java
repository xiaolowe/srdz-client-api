package cn.org.citycloud.bean;

public class MallGoodsSearch extends SliceAndSort {

	private String keyword;

	private int firstGoodsClass;

	private int secondGoodsClass;

	private int thirdGoodsClass;

	private int regionProv;

	private int salerFlg = 0;
	

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getFirstGoodsClass() {
		return firstGoodsClass;
	}

	public void setFirstGoodsClass(int firstGoodsClass) {
		this.firstGoodsClass = firstGoodsClass;
	}

	public int getSecondGoodsClass() {
		return secondGoodsClass;
	}

	public void setSecondGoodsClass(int secondGoodsClass) {
		this.secondGoodsClass = secondGoodsClass;
	}

	public int getRegionProv() {
		return regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public int getSalerFlg() {
		return salerFlg;
	}

	public void setSalerFlg(int salerFlg) {
		this.salerFlg = salerFlg;
	}

	public int getThirdGoodsClass() {
		return thirdGoodsClass;
	}

	public void setThirdGoodsClass(int thirdGoodsClass) {
		this.thirdGoodsClass = thirdGoodsClass;
	}

}
