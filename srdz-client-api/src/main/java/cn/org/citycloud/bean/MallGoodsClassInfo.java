package cn.org.citycloud.bean;

import java.util.ArrayList;
import java.util.List;

import cn.org.citycloud.entity.GoodsClass;
import cn.org.citycloud.entity.RegionInfo;

public class MallGoodsClassInfo {

	private List<GoodsClass> firstGoodsClass = new ArrayList<GoodsClass>();

	private List<GoodsClass> secondGoodsClass = new ArrayList<GoodsClass>();

	private List<RegionInfo> reginsInfo = new ArrayList<RegionInfo>();

	public List<GoodsClass> getFirstGoodsClass() {
		return firstGoodsClass;
	}

	public void setFirstGoodsClass(List<GoodsClass> firstGoodsClass) {
		this.firstGoodsClass = firstGoodsClass;
	}

	public List<GoodsClass> getSecondGoodsClass() {
		return secondGoodsClass;
	}

	public void setSecondGoodsClass(List<GoodsClass> secondGoodsClass) {
		this.secondGoodsClass = secondGoodsClass;
	}

	public List<RegionInfo> getReginsInfo() {
		return reginsInfo;
	}

	public void setReginsInfo(List<RegionInfo> reginsInfo) {
		this.reginsInfo = reginsInfo;
	}

}
