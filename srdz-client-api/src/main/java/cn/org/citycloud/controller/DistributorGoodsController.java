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

import com.sun.corba.se.impl.orbutil.closure.Constant;

import cn.org.citycloud.bean.DistShopGoodsSearch;
import cn.org.citycloud.bean.DistributorsGoods;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.DistShopGood;
import cn.org.citycloud.entity.Good;
import cn.org.citycloud.entity.SalesMember;
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

/**
 * 分销商商品控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/distributorGoods")
@Api(tags = "分销商", position = 8, value = "/distributorGoods", description = "我的分销模块", consumes = "application/json")
public class DistributorGoodsController extends BaseController {

	@Autowired
	private SalesMemberDao salesMemberDao;

	@Autowired
	private SalesShopDao salesShopDao;

	@Autowired
	private DistShopGoodDao distShopGoodDao;

	@Autowired
	private GoodsDao goodsDao;
	
	
	@RequestMapping(value="/{goodsId}",method=RequestMethod.GET)
	@ApiOperation(value = "分销商品详情", notes = "分销商品详情", response = DistShopGood.class)
	@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")
	public Object getGoodInfo(@ApiParam(name="goodsId",value="分销商品ID",required=true)@PathVariable int goodsId)throws Exception{
		return distShopGoodDao.findByGoodsIdAndMemberId(goodsId, getMemberId());
	}
	
	

	/**
	 * 分销商商品一览
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "分销商上架商品一览", notes = "分销商上架商品一览", response = DistShopGood.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header"),
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
				where = cb.and(where, cb.equal(memberId, getMemberId()));

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

		return goodsList.getContent();
	}

	/**
	 * 分销商上架商品
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "分销商上架商品", notes = "分销商上架商品",response = DistShopGood.class)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header") })
	public Object addGoods(@Valid @RequestBody DistributorsGoods distGoods) throws BusinessErrorException {
		// 验证分销商品 分销属性
		Good distGoodInfo = goodsDao.findOne(distGoods.getGoodsId());

		if (distGoodInfo.getSalerFlg() != 1) {

			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品不是分销商品，无法上架");
		}

		// 分销利润空间Low
		BigDecimal rangeLow = distGoodInfo.getDistGood().getRangeLow().divide(new BigDecimal(100));
		BigDecimal rangeHigh = distGoodInfo.getDistGood().getRangeHigh().divide(new BigDecimal(100));

		BigDecimal rangeLowPrice = (BigDecimal.ONE.add(rangeLow)).multiply(distGoodInfo.getInitPrice());
		BigDecimal rangeHighPrice = (BigDecimal.ONE.add(rangeHigh)).multiply(distGoodInfo.getInitPrice());

		if (distGoods.getShopGoodsPrice().compareTo(rangeLowPrice) < 0
				|| distGoods.getShopGoodsPrice().compareTo(rangeHighPrice) > 0) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "商品价格设置不在分销利润空间内");
		}

//		SalesMember salesMember = salesMemberDao.findOne(getMemberId());

		// 插入分销商品信息 如果已存在，判断状态是否下架，如果不存在，创建
		DistShopGood existGoods = distShopGoodDao.findByGoodsIdAndMemberId(distGoods.getGoodsId(), getMemberId());
		Date now = new Date();
		if (existGoods!=null) {
			if(existGoods.getStatus()==Constants.DIST_GOODS_ONLINE){
				throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "该分销商品已经上架");
			}
			existGoods.setMemberId(getMemberId());
			existGoods.setShopGoodsPrice(distGoods.getShopGoodsPrice());
			existGoods.setStatus(Constants.DIST_GOODS_ONLINE);
			existGoods.setUpdateTime(now);
			return distShopGoodDao.save(existGoods);
		}else {
			DistShopGood distGood = new DistShopGood();
			distGood.setGoodsId(distGoods.getGoodsId());
			distGood.setMemberId(getMemberId());
			distGood.setShopGoodsPrice(distGoods.getShopGoodsPrice());
			
			distGood.setCreateTime(now);
			distGood.setUpdateTime(now);

			return distShopGoodDao.save(distGood);
		}

		
	}
	
	@RequestMapping(value="/{goodsId}",method=RequestMethod.PUT)
	@ApiOperation(value = "分销商下架商品", notes = "分销商下架商品",response = DistShopGood.class)
	@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")
	public Object downGoods(@ApiParam(name="goodsId",value="商品ID",required=true)@PathVariable int goodsId)throws Exception{
		DistShopGood distGood = distShopGoodDao.findByGoodsIdAndMemberId(goodsId, getMemberId());
		if(distGood==null){
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "该分销商品不存在");
		}
		distGood.setStatus(Constants.DIST_GOODS_OFFLINE);
		distGood.setUpdateTime(new Date());
		return distShopGoodDao.save(distGood);
	}
	
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "分销商修改价格", notes = "分销商修改价格",response = DistShopGood.class)
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header") })
	public Object mergeGoods(@Valid @RequestBody DistributorsGoods distGoods) throws BusinessErrorException {
		
		Good distGoodInfo = goodsDao.findOne(distGoods.getGoodsId());

		if (distGoodInfo.getSalerFlg() != 1) {

			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品不是分销商品，无法修改价格");
		}

		// 分销利润空间Low
		BigDecimal rangeLow = distGoodInfo.getDistGood().getRangeLow().divide(new BigDecimal(100));
		BigDecimal rangeHigh = distGoodInfo.getDistGood().getRangeHigh().divide(new BigDecimal(100));

		BigDecimal rangeLowPrice = (BigDecimal.ONE.add(rangeLow)).multiply(distGoodInfo.getInitPrice());
		BigDecimal rangeHighPrice = (BigDecimal.ONE.add(rangeHigh)).multiply(distGoodInfo.getInitPrice());

		if (distGoods.getShopGoodsPrice().compareTo(rangeLowPrice) < 0
				|| distGoods.getShopGoodsPrice().compareTo(rangeHighPrice) > 0) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "商品价格设置不在分销利润空间内");
		}
		
		DistShopGood distGood = distShopGoodDao.findByGoodsIdAndMemberId(distGoods.getGoodsId(), getMemberId());
		if(distGood==null){
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "该分销商品不存在");
		}
		distGood.setShopGoodsPrice(distGoods.getShopGoodsPrice());
		distGood.setUpdateTime(new Date());
		return distShopGoodDao.save(distGood);
	}
}
