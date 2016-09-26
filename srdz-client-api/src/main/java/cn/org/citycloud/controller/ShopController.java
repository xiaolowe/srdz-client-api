package cn.org.citycloud.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.ShopGoodsSearch;
import cn.org.citycloud.bean.ShopSearch;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.entity.Good;
import cn.org.citycloud.entity.Supplier;
import cn.org.citycloud.repository.GoodsDao;
import cn.org.citycloud.repository.SupplierDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 店铺控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/shops")
@Api(tags = "店铺", position = 8, value = "/shops", description = "店铺模块", consumes = "application/json")
public class ShopController {

	@Autowired
	private SupplierDao supplierDao;

	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 供应商店铺列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取供应商店铺列表", notes = "获取供应商店铺列表", response = Supplier.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "regionProv", value = "省ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "regionCity", value = "省ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "supplierName", value = "供应商名称（店铺名称）", required = false, dataType = "string", paramType = "query"),})
	public Object getSupplierList(@ApiIgnore @Valid ShopSearch search) {

		// 分页
		Pageable pageable = new PageRequest(search.getPage(), search.getSize());

		Specification<Supplier> specs = new Specification<Supplier>() {

			@Override
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				// 省市区域

				if (search.getRegionProv() != 0) {
					Path<Integer> regionProv = root.get("regionProv");
					where = cb.and(where, cb.equal(regionProv, search.getRegionProv()));
				}

				if (search.getRegionCity() != 0) {
					Path<Integer> regionProv = root.get("regionCity");
					where = cb.and(where, cb.equal(regionProv, search.getRegionCity()));
				}
				if(search.getSupplierName() != null){
					Path<String> supplierName = root.get("supplierName");
					where = cb.and(where, cb.like(supplierName, "%" + search.getSupplierName() + "%"));
				}

				// 店铺状态
				Path<Integer> status = root.get("status");
				where = cb.and(where, cb.equal(status, Constants.SHOP_STATUS_OPEN));
				

				query.where(where);

				List<Order> orderList = new ArrayList<Order>();

				// 时间排序
				Path<Integer> createTime = root.get("createTime");
				Order createTimeOrder = cb.desc(createTime);
				orderList.add(createTimeOrder);

				// 排序
				query.orderBy(orderList);
				return null;
			}

		};

		Page<Supplier> shopList = supplierDao.findAll(specs, pageable);

		return shopList;
	}

	/**
	 * 供应商店铺详情信息
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "获取供应商店铺详情信息", notes = "获取供应商店铺详情信息", response = Supplier.class)
	public Object getSupplierShop(@ApiParam(value = "公告ID", required = true) @PathVariable int id) {

		return supplierDao.findOne(id);
	}

	/**
	 * 供应商店铺商品列表
	 */
	@RequestMapping(value = "/{id}/goods", method = RequestMethod.GET)
	@ApiOperation(value = "获取供应商店铺商品列表", notes = "获取供应商店铺商品列表", response = Supplier.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "goodsClassId", value = "一级分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query") })
	public Object getSupplierGoods(@ApiParam(value = "供应商店铺ID", required = true) @PathVariable int id, @ApiIgnore @Valid ShopGoodsSearch search) {

		// 分页
		Pageable pageable = new PageRequest(search.getPage(), search.getSize());

		Specification<Good> specs = new Specification<Good>() {

			@Override
			public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				// 一级分类ID
				if (search.getGoodsClassId() != 0) {
					Path<Integer> goodsClassId = root.get("goodsClassId");
					where = cb.and(where, cb.equal(goodsClassId, search.getGoodsClassId()));
				}
				// 供应商ID
				Path<Integer> supplierId = root.get("supplierId");
				where = cb.and(where, cb.equal(supplierId, id));

				// 商品状态
				Path<Integer> status = root.get("goodsStatus");
				where = cb.and(where, cb.notEqual(status, Constants.GOODS_STATE_OFFLINE));
				where = cb.and(where, cb.notEqual(status, Constants.GOODS_STATE_DISABLE));
				
				// 自动下架时间
				Path<Date> autoDownTime = root.get("autoDownTime");
				where = cb.and(where,
						cb.greaterThan(autoDownTime, new Date()));

				query.where(where);

				List<Order> orderList = new ArrayList<Order>();

				// 特惠商品 => 普通商品
				Path<Integer> discountFlg = root.get("discountFlg");
				Order discountFlgOrder = cb.desc(discountFlg);
				orderList.add(discountFlgOrder);

				// 时间排序
				Path<Integer> createTime = root.get("createTime");
				Order createTimeOrder = cb.desc(createTime);
				orderList.add(createTimeOrder);

				// 排序
				query.orderBy(orderList);
				return null;
			}

		};

		Page<Good> goodsList = goodsDao.findAll(specs, pageable);

		return goodsList;
	}

}
