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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.DeliveryGoods;
import cn.org.citycloud.bean.EvaluateSearch;
import cn.org.citycloud.bean.FloorGoodsInfo;
import cn.org.citycloud.bean.GoodsClassInfo;
import cn.org.citycloud.bean.GoodsDeliverCost;
import cn.org.citycloud.bean.MallGoodsSearch;
import cn.org.citycloud.bean.ProductPlace;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.entity.EvaluateGood;
import cn.org.citycloud.entity.FlowInfo;
import cn.org.citycloud.entity.FlowTemplate;
import cn.org.citycloud.entity.Good;
import cn.org.citycloud.entity.GoodsClass;
import cn.org.citycloud.entity.GoodsClassBanner;
import cn.org.citycloud.repository.EvaluateGoodDao;
import cn.org.citycloud.repository.FlowInfoDao;
import cn.org.citycloud.repository.FlowTemplateDao;
import cn.org.citycloud.repository.GoodsClassBannerDao;
import cn.org.citycloud.repository.GoodsClassDao;
import cn.org.citycloud.repository.GoodsDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 商品控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/goods")
@Api(tags = "商城", position = 8, value = "/goods", description = "商品模块", consumes = "application/json")
public class GoodsController {

	@Autowired
	private GoodsDao goodsDao;

	@Autowired
	private GoodsClassDao goodsClassDao;

	@Autowired
	private GoodsClassBannerDao goodsClassBannerDao;

	@Autowired
	private FlowTemplateDao flowTemplateDao;

	@Autowired
	private FlowInfoDao flowInfoDao;

	@Autowired
	private EvaluateGoodDao evaluateGoodDao;

	/**
	 * 首页楼层商品信息
	 */
	@RequestMapping(value = "/floorGoods/{goodsClassId}", method = RequestMethod.GET)
	@ApiOperation(value = "首页楼层商品信息", notes = "获取首页楼层商品信息", response = FloorGoodsInfo.class, responseContainer = "List")
	public Object getFloorGoodsInfo(
			@ApiParam(value = "一级分类ID(0是全部获取)", required = false) @PathVariable int goodsClassId) {

		List<FloorGoodsInfo> floorGoodsList = new ArrayList<FloorGoodsInfo>();
		if (goodsClassId != 0) {
			FloorGoodsInfo oneFloorGoods = getAllFloorGoods(goodsClassId);

			floorGoodsList.add(oneFloorGoods);
		} else {
			List<GoodsClass> firstClass = goodsClassDao.findByParentIdAndDelFlagOrderBySortAsc(0, 0);

			for (GoodsClass goodsClass : firstClass) {
				FloorGoodsInfo floorGoods = getAllFloorGoods(goodsClass.getGoodsClassId());

				floorGoodsList.add(floorGoods);
			}
		}

		return floorGoodsList;
	}

	/**
	 * 商城商品列表 过滤条件 分类一 分类二 分类三 产地
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "商城商品列表 过滤条件 分类一 分类二 产地", notes = "获取商城商品列表 过滤条件 分类一 分类二 产地", response = Good.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "keyword", value = "关键字搜索(商品)", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "firstGoodsClass", value = "一级分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "secondGoodsClass", value = "二级分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "thirdGoodsClass", value = "三级分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "regionProv", value = "产地ID（省）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "salerFlg", value = "是否分销商品（2 不区分 1  是   0 否）", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段（salePrice，alreadySale）", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "顺序DESC、ASC", required = false, dataType = "string", paramType = "query") })
	public Object getGoodsClassesByParentId(@ApiIgnore @Valid MallGoodsSearch search) {

		// 分页
		Pageable pageable = new PageRequest(search.getPage(), search.getSize());

		Specification<Good> specs = new Specification<Good>() {

			@Override
			public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				// 关键字
				if (StringUtils.isNotBlank(search.getKeyword())) {
					
					Path<String> goodsName = root.get("goodsName");
					where = cb.and(where, cb.like(goodsName, "%" + search.getKeyword() + "%"));
					
				}

				// 一级分类
				if (search.getFirstGoodsClass() != 0) {
					Path<Integer> goodsClassId = root.get("goodsClassId");
					where = cb.and(where, cb.equal(goodsClassId, search.getFirstGoodsClass()));
				}

				// 二级分类
				if (search.getSecondGoodsClass() != 0) {
					Path<Integer> goodsClassTwoId = root.get("goodsClassTwoId");
					where = cb.and(where, cb.equal(goodsClassTwoId, search.getSecondGoodsClass()));
				}

				// 三级分类
				if (search.getThirdGoodsClass() != 0) {
					Path<Integer> goodsClassThreeId = root.get("goodsClassThreeId");
					where = cb.and(where, cb.equal(goodsClassThreeId, search.getThirdGoodsClass()));
				}

				// 产地
				if (search.getRegionProv() != 0) {
					Path<Integer> regionProv = root.get("regionProv");
					where = cb.and(where, cb.equal(regionProv, search.getRegionProv()));
				}

				// 商品状态
				Path<Integer> goosStatus = root.get("goodsStatus");
				where = cb.and(where, cb.equal(goosStatus, Constants.GOODS_STATE_NORMAL));

				// 是否分销
				if(search.getSalerFlg() != 2) {
					Path<Integer> salerFlg = root.get("salerFlg");
					where = cb.and(where, cb.equal(salerFlg, search.getSalerFlg()));
				}
				
				// 自动下架时间
				Path<Date> autoDownTime = root.get("autoDownTime");
				where = cb.and(where, cb.greaterThan(autoDownTime, new Date()));

				query.where(where);

				List<Order> orderList = new ArrayList<Order>();
				
				// 按销量排序 按价格排序
				if (StringUtils.isNotBlank(search.getSort())) {
					if ("salePrice".equals(search.getSort())) {
						Path<BigDecimal> salePrice = root.get("salePrice");

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
				} else {
					// 推荐商品顺序
					Path<Integer> isRecommend = root.get("isRecommend");
					Order isRecommendFlgOrder = cb.desc(isRecommend);
					orderList.add(isRecommendFlgOrder);
				}
				
				// 上架时间倒序
				Path<Date> goodsAddtime = root.get("goodsAddtime");
				Order goodsAddtimeOrder = cb.desc(goodsAddtime);
				orderList.add(goodsAddtimeOrder);
				
				// 排序
				query.orderBy(orderList);
				return query.getRestriction();
			}

		};

		Page<Good> goodsList = goodsDao.findAll(specs, pageable);

		return goodsList;

	}

	/**
	 * 商城 二级分类商品产地信息
	 */
	@RequestMapping(value = "/productPlaces/{secondClass}", method = RequestMethod.GET)
	@ApiOperation(value = "商城 二级分类商品产地信息", notes = "获取商城 二级分类商品产地信息", response = GoodsClassInfo.class, responseContainer = "List")
	public Object getProductPlacesBySecondClass(
			@ApiParam(value = "二级分类ID", required = true) @PathVariable int secondClass) {

		// 二级分类信息
		GoodsClass classInfo = goodsClassDao.findOne(secondClass);
		if (classInfo == null || classInfo.getParentId() == 0) {
			return null;
		}

		List<Object> tempProductPlaces = goodsDao.findProductPlaces(secondClass);

		List<ProductPlace> productPlaceList = new ArrayList<ProductPlace>();
		for (int i = 0; i < tempProductPlaces.size(); i++) {
			Object[] objArray = (Object[]) tempProductPlaces.get(i);

			ProductPlace place = new ProductPlace();
			place.setRegionProv((Integer) objArray[0]);
			place.setRegionProvName((String) objArray[1]);

			productPlaceList.add(place);
		}

		return productPlaceList;
	}

	/**
	 * 特惠专区商品列表 过滤条件 分类一 分类二 分类三 产地
	 */
	@RequestMapping(value = "/discount", method = RequestMethod.GET)
	@ApiOperation(value = "特惠专区商品列表 过滤条件 分类一 分类二 产地", notes = "获取特惠专区商品列表 过滤条件 分类一 分类二 产地", response = Good.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "firstGoodsClass", value = "一级分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "secondGoodsClass", value = "二级分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "thirdGoodsClass", value = "三级分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "regionProv", value = "产地ID（省）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sort", value = "排序字段（salePrice，alreadySale）", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "order", value = "顺序DESC、ASC", required = false, dataType = "string", paramType = "query") })
	public Object getDiscountGoodsClassesByParentId(@ApiIgnore @Valid MallGoodsSearch search) {

		// 分页
		Pageable pageable = new PageRequest(search.getPage(), search.getSize());

		Specification<Good> specs = new Specification<Good>() {

			@Override
			public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				// 一级分类
				if (search.getFirstGoodsClass() != 0) {
					Path<Integer> goodsClassId = root.get("goodsClassId");
					where = cb.and(where, cb.equal(goodsClassId, search.getFirstGoodsClass()));
				}

				// 二级分类
				if (search.getSecondGoodsClass() != 0) {
					Path<Integer> goodsClassTwoId = root.get("goodsClassTwoId");
					where = cb.and(where, cb.equal(goodsClassTwoId, search.getSecondGoodsClass()));
				}

				// 三级分类
				if (search.getThirdGoodsClass() != 0) {
					Path<Integer> goodsClassThreeId = root.get("goodsClassThreeId");
					where = cb.and(where, cb.equal(goodsClassThreeId, search.getThirdGoodsClass()));
				}

				// 产地
				if (search.getRegionProv() != 0) {
					Path<Integer> regionProv = root.get("regionProv");
					where = cb.and(where, cb.equal(regionProv, search.getRegionProv()));
				}

				// 商品状态
				Path<Integer> goosStatus = root.get("goodsStatus");
				where = cb.and(where, cb.equal(goosStatus, Constants.GOODS_STATE_NORMAL));

				// 自动下架时间
				Path<Date> autoDownTime = root.get("autoDownTime");
				where = cb.and(where, cb.greaterThan(autoDownTime, new Date()));

				// 特惠商品
				Path<Integer> discountFlg = root.get("discountFlg");
				where = cb.and(where, cb.equal(discountFlg, 1));

				query.where(where);

				List<Order> orderList = new ArrayList<Order>();
				
				// 按销量排序 按价格排序
				if (StringUtils.isNotBlank(search.getSort())) {
					if ("salePrice".equals(search.getSort())) {
						Path<BigDecimal> salePrice = root.get("salePrice");

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
				} else {
					// 推荐商品顺序
					Path<Integer> isRecommend = root.get("isRecommend");
					Order isRecommendFlgOrder = cb.desc(isRecommend);
					orderList.add(isRecommendFlgOrder);
				}
				
				// 上架时间倒序
				Path<Date> goodsAddtime = root.get("goodsAddtime");
				Order goodsAddtimeOrder = cb.desc(goodsAddtime);
				orderList.add(goodsAddtimeOrder);

				// 排序
				query.orderBy(orderList);
				return null;
			}

		};

		Page<Good> goodsList = goodsDao.findAll(specs, pageable);

		return goodsList;

	}

	/**
	 * 商品详情
	 * 
	 * 
	 */
	@RequestMapping(value = "/{goodsId}", method = RequestMethod.GET)
	@ApiOperation(value = "商品详情信息", notes = "获取商品详情信息", response = Good.class)
	public Object getGoodsDetail(@ApiParam(value = "商品ID", required = true) @PathVariable int goodsId) {

		return goodsDao.findOne(goodsId);
	}

	/**
	 * 计算商品物流费用
	 */
	@RequestMapping(value = "/deliveryCosts", method = RequestMethod.POST)
	@ApiOperation(value = "获取商品物流费用", notes = "获取商品物流费用", response = GoodsDeliverCost.class,
	responseContainer = "List")
	public List<GoodsDeliverCost> getGoodsDeliveryCosts(@Valid @RequestBody List<DeliveryGoods> search) {

		List<GoodsDeliverCost>  costList = new ArrayList<GoodsDeliverCost>();
		for (DeliveryGoods deliveryGoods : search) {
			GoodsDeliverCost cost = calcGoodsDeliveryCosts(deliveryGoods);

			costList.add(cost);
		}

		return costList;
	}

	/**
	 * 获取首页楼层商品
	 * 
	 * @param goodsClassId
	 * @return
	 */
	private FloorGoodsInfo getAllFloorGoods(int goodsClassId) {
		FloorGoodsInfo floorGood = new FloorGoodsInfo();
		// 楼层商品banner信息
		List<GoodsClassBanner> banners = goodsClassBannerDao.findByGoodsClassId(goodsClassId);
		floorGood.setBanners(banners);

		// 楼层商品信息
		// 分页
		Pageable pageable = new PageRequest(0, 8);
		Specification<Good> specs = new Specification<Good>() {

			@Override
			public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				// 一级分类
				Path<Integer> goodsClassIdPath = root.get("goodsClassId");
				where = cb.and(where, cb.equal(goodsClassIdPath, goodsClassId));

				// 商品状态
				Path<Integer> goosStatus = root.get("goodsStatus");
				where = cb.and(where, cb.equal(goosStatus, Constants.GOODS_STATE_NORMAL));

				// 自动下架时间
				Path<Date> autoDownTime = root.get("autoDownTime");
				where = cb.and(where, cb.greaterThan(autoDownTime, new Date()));
				
				// 是否分销
//				Path<Integer> salerFlg = root.get("salerFlg");
//				where = cb.and(where, cb.equal(salerFlg, 0));

				// 过滤特惠
				Path<Integer> discountFlg = root.get("discountFlg");
				where = cb.and(where, cb.equal(discountFlg, 0));

				query.where(where);

				List<Order> orderList = new ArrayList<Order>();
				
				// 特惠商品顺序
//				Path<Integer> discountFlg = root.get("discountFlg");
//				where = cb.and(where, cb.equal(discountFlg, 0));
//				Order discountFlgOrder = cb.desc(discountFlg);
//				orderList.add(discountFlgOrder);

				// 推荐商品顺序
				Path<Integer> isRecommend = root.get("isRecommend");
				Order isRecommendFlgOrder = cb.desc(isRecommend);
				orderList.add(isRecommendFlgOrder);
				
				// 上架时间倒序
				Path<Date> goodsAddtime = root.get("goodsAddtime");
				Order goodsAddtimeOrder = cb.desc(goodsAddtime);
				orderList.add(goodsAddtimeOrder);

				// 排序
				query.orderBy(orderList);
				return null;
			}

		};

		Page<Good> goodsPageList = goodsDao.findAll(specs, pageable);

		floorGood.setGoods(goodsPageList.getContent());

		return floorGood;
	}

	/**
	 * 商品评价列表
	 */
	@RequestMapping(value = "/evaluates", method = RequestMethod.GET)
	@ApiOperation(value = "商品评价列表", notes = "获取商品评价列表", response = EvaluateGood.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "goodsId", value = "商品ID", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "int", paramType = "query")})
	public Object getGoodsEvaluate(@ApiIgnore @Valid EvaluateSearch search)
	{
		// 分页
		Pageable pageable = new PageRequest(search.getPage(), search.getSize());

		Specification<EvaluateGood> specs = new Specification<EvaluateGood>() {

			@Override
			public Predicate toPredicate(Root<EvaluateGood> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate where = cb.conjunction();

				// 商品ID
				Path<Integer> goodsId = root.get("goodsId");
				where = cb.and(where, cb.equal(goodsId, search.getGoodsId()));

				query.where(where);

				List<Order> orderList = new ArrayList<Order>();

				// 上架时间倒序
				Path<Date> gevalAddtime = root.get("gevalAddtime");
				Order gevalAddtimeOrder = cb.desc(gevalAddtime);
				orderList.add(gevalAddtimeOrder);

				// 排序
				query.orderBy(orderList);
				return null;
			}

		};

		Page<EvaluateGood> goodsList = evaluateGoodDao.findAll(specs, pageable);

		return goodsList;
	}


	/**
	 * 计算商品的物流费用
	 */
	private GoodsDeliverCost calcGoodsDeliveryCosts(DeliveryGoods deliveryGoods) {

		// 物流费用
		GoodsDeliverCost cost = new GoodsDeliverCost();
		cost.setGoodsId(deliveryGoods.getGoodsId());

		// 商品信息
		Good good = goodsDao.findOne(deliveryGoods.getGoodsId());

		// 物流模板信息 包邮
		if(good == null || good.getFlowTemplateId() == 0) {
			cost.setDeliverCost(BigDecimal.ZERO);
			return cost;
		}

		FlowTemplate flowTemp = flowTemplateDao.findByFlowTemplateIdAndSupplierId(good.getFlowTemplateId(), good.getSupplierId());

		// 未设定物流模板 包邮
		if(flowTemp == null) {
			cost.setDeliverCost(BigDecimal.ZERO);
			return cost;
		}

		// 物流费用 重量件数标识( 1 重量   2 件数)
		int weightPieceFlag = flowTemp.getWeightPieceFlag();

		// 收货地市
		int regionCity = deliveryGoods.getRegionCity();

		// 物流信息 按照收货地市查询
		Object[] flowInfo = flowInfoDao.findFlownInfoByRegionCity(good.getFlowTemplateId(), regionCity);

		// 计算数值
		// 物流物品
		BigDecimal flowGoods = BigDecimal.ZERO;

		// 物流价格
		BigDecimal flowPrice = BigDecimal.ZERO;

		// 加多少物品
		BigDecimal addFlowGoods = BigDecimal.ZERO;

		// 加多少价格
		BigDecimal addflowPrice = BigDecimal.ZERO;

		// 获取默认物流计算信息
		if(flowInfo.length == 0) {
			FlowInfo defaultFlowInfo = flowInfoDao.findByFlowTemplateIdAndDefaultFlag(good.getFlowTemplateId(), 1);
			flowGoods = defaultFlowInfo.getFlowGoods();
			flowPrice = defaultFlowInfo.getFlowPrice();
			addFlowGoods = defaultFlowInfo.getAddFlowGoods();
			addflowPrice = defaultFlowInfo.getAddGoodsPrice();
		} else {
			Object[] flowInfoArr = (Object[])flowInfo[0];
			if(flowInfoArr.length == 4) {
				flowGoods = (BigDecimal)flowInfoArr[0];
				flowPrice = (BigDecimal)flowInfoArr[1];
				addFlowGoods = (BigDecimal)flowInfoArr[2];
				addflowPrice = (BigDecimal)flowInfoArr[3];
			}
		}

		if(flowGoods == BigDecimal.ZERO) {
			cost.setDeliverCost(BigDecimal.ZERO);
			return cost;
		}

		// 物流算费
		BigDecimal flowSumPrice = BigDecimal.ZERO;
		// 按重量计算
		if(1 == weightPieceFlag) {

			// 商品规格 重量
			BigDecimal goodsWeight = good.getGoodsWeight();

			// 商品无重量 包邮
			if(goodsWeight == null || goodsWeight == BigDecimal.ZERO) {
				cost.setDeliverCost(BigDecimal.ZERO);
				return cost;
			}

			// 订单商品重量
			BigDecimal presentWeight = goodsWeight.multiply(new BigDecimal(deliveryGoods.getGoodsNum())).setScale(2,
					BigDecimal.ROUND_HALF_UP);

			// 首重

			// 续重
			BigDecimal contHeavy = presentWeight.subtract(flowGoods);

			if(contHeavy.compareTo(BigDecimal.ZERO) <= 0) {
				flowSumPrice = flowPrice;

			} else {
				if (addFlowGoods.intValue() == 0) {
					// 续重价格
					BigDecimal contDown = new BigDecimal(0);

					flowSumPrice = flowPrice.add(contDown.multiply(addflowPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
				} else {
					// 续重价格
					BigDecimal contDown = contHeavy.divide(addFlowGoods, 0, BigDecimal.ROUND_HALF_UP);

					flowSumPrice = flowPrice.add(contDown.multiply(addflowPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
		} else if(2 == weightPieceFlag){
			// 按件数计算	
			int flowGoodsNum = flowGoods.intValue();
			// 不超件数
			if(deliveryGoods.getGoodsNum() - flowGoodsNum <= 0) {

				flowSumPrice = flowPrice;
			} else {
				// 续件
				if (addFlowGoods.intValue() == 0) {
					int countDown = deliveryGoods.getGoodsNum();
					// 2016-07-27 18：22修改by zerobug,注释此行代码
//					int countSum = deliveryGoods.getGoodsNum() - flowGoodsNum % addFlowGoods.intValue();
					int countSum = deliveryGoods.getGoodsNum();
					if (countSum != 0) {
						countDown += 1;
					}

					flowSumPrice = flowPrice.add(addflowPrice.multiply(new BigDecimal(countDown))).setScale(2,
							BigDecimal.ROUND_HALF_UP);
				} else {
					int countDown = deliveryGoods.getGoodsNum() - flowGoodsNum / addFlowGoods.intValue();
					int countSum = deliveryGoods.getGoodsNum() - flowGoodsNum % addFlowGoods.intValue();
					if (countSum != 0) {
						countDown += 1;
					}

					flowSumPrice = flowPrice.add(addflowPrice.multiply(new BigDecimal(countDown))).setScale(2,
							BigDecimal.ROUND_HALF_UP);
				}

			}
			cost.setDeliverCost(flowSumPrice);
			return cost;
		} 

		cost.setDeliverCost(flowSumPrice);
		return cost;
	}
}
