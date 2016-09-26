package cn.org.citycloud.service;

import cn.org.citycloud.bean.MemberFavorite;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.entity.*;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.GoodsDao;
import cn.org.citycloud.repository.MemberGoodsFavoriteDao;
import cn.org.citycloud.repository.MemberShopFavoriteDao;
import cn.org.citycloud.repository.SupplierDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/20 8:59
 */
@Service
public class FavoritesService {

    @Autowired
    private MemberGoodsFavoriteDao goodsFavoriteDao;

    @Autowired
    private MemberShopFavoriteDao shopFavoriteDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private MemberGoodsFavoriteDao memberGoodsFavoriteDao;

    /**
     * 添加收藏
     *
     * @param favorite
     * @param memberId
     * @throws BusinessErrorException
     */
    @Transactional(rollbackFor = Exception.class)
    public void addFavorites(MemberFavorite favorite, int memberId) throws BusinessErrorException {
        // 判断是否重复收藏
        checkUniqueOfFav(favorite, memberId);
        // 商品
        Date now = new Date();
        if (1 == favorite.getFavoriteType()) {
            Good good = goodsDao.findOne(favorite.getId());
            if (good == null) {
                throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "要收藏的商品不存在。");
            }

            MemberGoodsFavorite entity = new MemberGoodsFavorite();
            entity.setMemberId(memberId);
            entity.setCreateTime(now);
            entity.setGoodsId(good.getGoodsId());
            entity.setGoodsName(good.getGoodsName());

            Set<GoodsBanner> goodsBanner = good.getGoodsBanners();
            Iterator<GoodsBanner> iterator = goodsBanner.iterator();

            if (iterator.hasNext()) {
                GoodsBanner banner = (GoodsBanner) iterator.next();
                entity.setGoodsImage(banner.getBannerImage());
            }
            goodsFavoriteDao.save(entity);

            // 商品追加收藏数量
            good.setFavoritesCount(good.getFavoritesCount() + 1);
            goodsDao.save(good);

        }
        // 店铺
        else if (2 == favorite.getFavoriteType()) {
            Supplier supplier = supplierDao.findOne(favorite.getId());

            if (supplier == null) {
                throw new BusinessErrorException(ErrorCodes.WRONG_STORE, "要收藏的店铺不存在。");
            }
            MemberShopFavorite entity = new MemberShopFavorite();
            entity.setMemberId(memberId);
            entity.setCreateTime(now);
            entity.setSupplierId(supplier.getSupplierId());
            entity.setSupplierShopName(supplier.getSupplierShopName());
            entity.setLogoIamge(supplier.getLogoIamge());

            shopFavoriteDao.save(entity);

        } else {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "提交的请求数据格式错误。");
        }
    }

    /**
     * 删除收藏
     *
     * @param id
     * @param type
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteFavorites(int id, int type) {
        // 商品
        if (1 == type) {
            MemberGoodsFavorite entity = goodsFavoriteDao.findOne(id);
            Good good = goodsDao.findOne(entity.getGoodsId());

            if (good.getFavoritesCount() > 0) {
                good.setFavoritesCount(good.getFavoritesCount() - 1);
                goodsDao.save(good);
            }
            memberGoodsFavoriteDao.delete(id);
        }
        // 店铺
        else if (2 == type) {
            shopFavoriteDao.delete(id);
        }
    }


    /**
     * 判断是否重复收藏
     *
     * @param favorite
     * @param memberId
     * @throws BusinessErrorException
     */
    private void checkUniqueOfFav(MemberFavorite favorite, int memberId) throws BusinessErrorException {
        // 商品
        if (1 == favorite.getFavoriteType()) {
            if (memberGoodsFavoriteDao.findByMemberIdAndGoodsId(memberId, favorite.getId()) != null) {
                throw new BusinessErrorException(ErrorCodes.MULTIP_FAVORITE, "您已收藏过该商品！");
            }
        } else {
            if (shopFavoriteDao.findByMemberIdAndSupplierId(memberId, favorite.getId()) != null) {
                throw new BusinessErrorException(ErrorCodes.MULTIP_FAVORITE, "您已收藏过该店铺！");
            }
        }
    }
}
