package cn.org.citycloud.controller;

import cn.org.citycloud.bean.FavoritesSearch;
import cn.org.citycloud.bean.MemberFavorite;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.MemberGoodsFavorite;
import cn.org.citycloud.entity.MemberShopFavorite;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.MemberGoodsFavoriteDao;
import cn.org.citycloud.repository.MemberShopFavoriteDao;
import cn.org.citycloud.service.FavoritesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的收藏控制器
 *
 * @author lanbo
 */
@RestController
@RequestMapping(value = "/accounts/favorites")
@Api(tags = "个人中心", position = 8, value = "/accounts/favorites", description = "我的收藏模块", consumes = "application/json")
public class FavoritesController extends BaseController {

    @Autowired
    private MemberGoodsFavoriteDao goodsFavoriteDao;

    @Autowired
    private MemberShopFavoriteDao shopFavoriteDao;

    @Autowired
    private FavoritesService favoritesService;

    /**
     * 添加我的收藏
     */
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "添加我的收藏", notes = "添加我的收藏(type：1商品；2店铺)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header")})
    public void addFavorites(@Valid @RequestBody MemberFavorite favorite) throws BusinessErrorException {
        favoritesService.addFavorites(favorite, getMemberId());
    }

    /**
     * 我的收藏列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "我的收藏列表", notes = "我的收藏列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "type", value = "1商品；2店铺", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码（默认0）", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "分页大小（默认20）", required = false, dataType = "string", paramType = "query")})
    public Object getFavorites(@ApiIgnore @Valid FavoritesSearch search) throws BusinessErrorException {

        // 分页
        Pageable pageable = new PageRequest(search.getPage(), search.getSize());
        // 商品收藏
        if (1 == search.getType()) {

            Specification<MemberGoodsFavorite> specs = new Specification<MemberGoodsFavorite>() {

                @Override
                public Predicate toPredicate(Root<MemberGoodsFavorite> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                    Predicate where = cb.conjunction();

                    // 会员
                    Path<Integer> memberId = root.get("memberId");
                    where = cb.and(where, cb.equal(memberId, getMemberId()));

                    query.where(where);

                    List<Order> orderList = new ArrayList<Order>();

                    // 上架时间倒序
                    Path<Date> createTime = root.get("createTime");
                    Order createTimeOrder = cb.desc(createTime);
                    orderList.add(createTimeOrder);

                    // 排序
                    query.orderBy(orderList);
                    return null;
                }

            };

            Page<MemberGoodsFavorite> goodsFavoriteList = goodsFavoriteDao.findAll(specs, pageable);

            return goodsFavoriteList;

        }
        // 店铺收藏
        else if (2 == search.getType()) {

            Specification<MemberShopFavorite> specs = new Specification<MemberShopFavorite>() {

                @Override
                public Predicate toPredicate(Root<MemberShopFavorite> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                    Predicate where = cb.conjunction();

                    // 会员
                    Path<Integer> memberId = root.get("memberId");
                    where = cb.and(where, cb.equal(memberId, getMemberId()));

                    query.where(where);

                    List<Order> orderList = new ArrayList<Order>();

                    // 上架时间倒序
                    Path<Date> createTime = root.get("createTime");
                    Order createTimeOrder = cb.desc(createTime);
                    orderList.add(createTimeOrder);

                    // 排序
                    query.orderBy(orderList);
                    return null;
                }

            };

            Page<MemberShopFavorite> goodsFavoriteList = shopFavoriteDao.findAll(specs, pageable);

            return goodsFavoriteList;
        }

        return null;
    }

    /**
     * 取消我的收藏
     */
    @RequestMapping(value = "/{id}/type/{type}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除我的收藏", notes = "删除我的收藏(id=收藏记录ID；type：1商品；2店铺)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header")})
    public void deleteFavorites(@PathVariable int id, @PathVariable int type) throws BusinessErrorException {
        favoritesService.deleteFavorites(id, type);
    }
}
