package cn.org.citycloud.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.hibernate.jpa.criteria.predicate.InPredicate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.bean.GoodsClassInfo;
import cn.org.citycloud.bean.MallGoodsClassInfo;
import cn.org.citycloud.entity.GoodsClass;
import cn.org.citycloud.repository.GoodsBannerDao;
import cn.org.citycloud.repository.GoodsClassDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 商品分类控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/goodsClasses")
@Api(tags = "商品分类", position = 2, value = "/goodsClasses", description = "商品分类模块", consumes = "application/json")
public class GoodsClassController {

	@Autowired
	private GoodsClassDao goodsClassDao;

	@Autowired
	private GoodsBannerDao goodsBannerDao;

	/**
	 * 首页商品分类信息(三级分类信息)
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "首页商品分类列表", notes = "获取首页商品分类信息", response = GoodsClassInfo.class, responseContainer = "List")
	public Object getGoodsClassesList() {

		// 农产品一级分类List
		List<GoodsClassInfo> classItemList = new ArrayList<GoodsClassInfo>();

		List<GoodsClass> firstClassList = goodsClassDao.findByParentIdAndDelFlagOrderBySortAsc(0, 0);

		for (GoodsClass firstClass : firstClassList) {
			// 农产品二级分类List
			List<GoodsClassInfo> secondClassItemList = new ArrayList<GoodsClassInfo>();
			// 农产品一级分类
			GoodsClassInfo firstClassInfo = new GoodsClassInfo();
			BeanUtils.copyProperties(firstClass, firstClassInfo);

			// 农产品二级分类
			List<GoodsClass> secondClassList = goodsClassDao
					.findByParentIdAndDelFlagOrderBySortAsc(firstClass.getGoodsClassId(), 0);
			for (GoodsClass secondClass : secondClassList) {

				GoodsClassInfo secondClassInfo = new GoodsClassInfo();

				BeanUtils.copyProperties(secondClass, secondClassInfo);

				List<GoodsClass> thirdClassList = goodsClassDao
						.findByParentIdAndDelFlagOrderBySortAsc(secondClass.getGoodsClassId(), 0);

				// 农产品三级分类List
				List<GoodsClassInfo> thirdClassItems = new ArrayList<GoodsClassInfo>();
				for (GoodsClass goodsClass : thirdClassList) {

					GoodsClassInfo thirdClass = new GoodsClassInfo();
					BeanUtils.copyProperties(goodsClass, thirdClass);

					thirdClassItems.add(thirdClass);
				}

				secondClassInfo.setChildClasses(thirdClassItems);

				secondClassItemList.add(secondClassInfo);
			}
			firstClassInfo.setChildClasses(secondClassItemList);
			classItemList.add(firstClassInfo);
		}
		return classItemList;
	}
	
	/**
	 * 商城 商品列表 一级商品分类
	 */
	@RequestMapping(value = "/level/{level}/parentId/{parentId}", method = RequestMethod.GET)
	@ApiOperation(value = "首页商品分类列表(level：1：2)", notes = "获取首页商品分类信息", response = MallGoodsClassInfo.class, responseContainer = "List")
	public Object getGoodsClassesByParentId(
			@ApiParam(value = "分类等级", required = true) @PathVariable int level,
			@ApiParam(value = "父分类ID", required = false) @PathVariable int parentId) {
		
		// parentId = 0 一级分类信息
		List<GoodsClass> goodsClass = new ArrayList<GoodsClass>();
		
		// 一级分类
		if(level == 1) {
			goodsClass = goodsClassDao.findByParentIdAndDelFlagOrderBySortAsc(0, 0);
		}
		// 二级分类
		if(level ==  2) {
			// 全部二级分类
			if(parentId == 0) {
				List<GoodsClass> firstGoodsClass = goodsClassDao.findByParentIdAndDelFlagOrderBySortAsc(0, 0);
				
				List<Integer> parentIdList = new ArrayList<Integer>();
				for (GoodsClass item : firstGoodsClass) {
					
					parentIdList.add(item.getGoodsClassId());
					
				}
				
				Specification<GoodsClass> specs = new Specification<GoodsClass>() {

					@Override
					public Predicate toPredicate(Root<GoodsClass> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						
						Predicate where = cb.conjunction();

						// 分类ID
						Path<Integer> parentId = root.get("parentId");
						
						CriteriaBuilderImpl cbi = null;
						if (cb instanceof CriteriaBuilderImpl) {
							cbi = (CriteriaBuilderImpl) cb;
						}

						InPredicate<Integer> inPredicate = (InPredicate<Integer>) cbi.in(parentId, parentIdList);

						where = cb.and(inPredicate);
						
						// 父分类
						where = cb.and(where, cb.notEqual(parentId, 0));
						
						Path<Integer> delFlag = root.get("delFlag");
						where = cb.and(where, cb.equal(delFlag, 0));
						
						query.where(where);
						
						List<Order> orderList = new ArrayList<Order>();

						// 时间排序
//						Path<Integer> createTime = root.get("createTime");
//						Order createTimeOrder = cb.desc(createTime);
//						orderList.add(createTimeOrder);
						
						Order parentIdOrder = cb.asc(parentId);
						orderList.add(parentIdOrder);
						
						Path<Integer> sort = root.get("sort");
						Order sortOrder = cb.asc(sort);
						orderList.add(sortOrder);

						// 排序
						query.orderBy(orderList);
						return null;
					}
				};
				goodsClass = goodsClassDao.findAll(specs);
				
			} else {
				goodsClass = goodsClassDao.findByParentIdAndDelFlagOrderBySortAsc(parentId, 0);
			}
			
		}
		return goodsClass;
	}
}
