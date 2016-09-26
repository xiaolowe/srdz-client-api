package cn.org.citycloud.controller;

import java.math.BigDecimal;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.DistShopGoodsSearch;
import cn.org.citycloud.bean.DistributorShopInfo;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.DistShopGood;
import cn.org.citycloud.entity.SalesShop;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.DistShopGoodDao;
import cn.org.citycloud.repository.GoodsDao;
import cn.org.citycloud.repository.SalesMemberDao;
import cn.org.citycloud.repository.SalesShopDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/distributorShop")
@Api(tags = "分销商城", position = 8, value = "/distributorShop", description = "分销商城首页", consumes = "application/json")
public class DistributorShopController {

	@Autowired
	private SalesMemberDao salesMemberDao;

	@Autowired
	private SalesShopDao salesShopDao;

	@Autowired
	private DistShopGoodDao distShopGoodDao;

	@Autowired
	private GoodsDao goodsDao;

	/**
	 * 分销商店铺首页信息
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/{memberId}", method = RequestMethod.GET)
	@ApiOperation(value = "分销店铺信息", notes = "分销店铺信息", response = DistShopGood.class, responseContainer = "List")
	public Object getDistributorShopInfo(@ApiParam(value = "分销商ID", required = true) @PathVariable int memberId)
			throws BusinessErrorException {

		DistributorShopInfo shopInfo = new DistributorShopInfo();
		// 全部商品
		long allGoods = 0L;
		// 上新商品
		long newArrive = 0L;

		SalesShop shop = salesShopDao.findByMemberId(memberId);

		if (shop == null) {

			throw new BusinessErrorException(ErrorCodes.WRONG_STORE, "此分销商信息不存在");
		}
		BeanUtils.copyProperties(shop, shopInfo);

		// 全部商品
		allGoods = distShopGoodDao.countAllDistGoodsByMemberId(memberId);

		// 上新商品
		newArrive = distShopGoodDao.countNewDistGoodsByMemberId(memberId);

		return shopInfo;
	}

	/**
	 * 分销商商品一览
	 */
	@RequestMapping(value = "/goods", method = RequestMethod.GET)
	@ApiOperation(value = "分销店铺商品", notes = "分销店铺商品", response = DistShopGood.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "memberId", value = "分销商会员Id", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段（salePrice，alreadySale）", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "顺序DESC、ASC", required = false, dataType = "string", paramType = "query") })
	public Object getGoods(@ApiIgnore @Valid DistShopGoodsSearch search) {

		// 分页
		Pageable pageable = new PageRequest(search.getPage(), search.getSize());

		Specification<DistShopGood> specs = new Specification<DistShopGood>() {

			@Override
			public Predicate toPredicate(Root<DistShopGood> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				// 分销商ID
				Path<Integer> memberId = root.get("memberId");
				where = cb.and(where, cb.equal(memberId, search.getMemberId()));

				query.where(where);

				List<Order> orderList = new ArrayList<Order>();

				// 按销量排序 按价格排序
				if (StringUtils.isNotBlank(search.getSort())) {
					if ("salePrice".equals(search.getSort())) {
						Path<BigDecimal> salePrice = root.get("shopGoodsPrice");

						Order salePriceFlgOrder = cb.asc(salePrice);
						if (Constants.SORT_DESC.equals(search.getOrder())) {
							salePriceFlgOrder = cb.desc(salePrice);
						}
						orderList.add(salePriceFlgOrder);

					} else if ("alreadySale".equals(search.getSort())) {
						Path<Integer> alreadySale = root.get("alreadySale");

						Order alreadySaleOrder = cb.asc(alreadySale);
						if (Constants.SORT_DESC.equals(search.getOrder())) {
							alreadySaleOrder = cb.desc(alreadySale);
						}
						orderList.add(alreadySaleOrder);
					}
				}

				// 上架时间倒序
				Path<Date> createTime = root.get("createTime");
				Order createTimeOrder = cb.desc(createTime);
				orderList.add(createTimeOrder);

				// 排序
				query.orderBy(orderList);
				return null;
			}

		};

		Page<DistShopGood> goodsList = distShopGoodDao.findAll(specs, pageable);

		return goodsList;
	}

}
