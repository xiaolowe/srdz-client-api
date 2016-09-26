package cn.org.citycloud.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.org.citycloud.service.WeixinService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.JWTSigner;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.org.citycloud.bean.BatchDelete;
import cn.org.citycloud.bean.CartGoods;
import cn.org.citycloud.bean.DistPreOrder;
import cn.org.citycloud.bean.OrderGoods;
import cn.org.citycloud.bean.ShopOrderGoods;
import cn.org.citycloud.bean.ShoppingCartGroupByShop;
import cn.org.citycloud.bean.WeixinToken;
import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.WeixinBaseController;
import cn.org.citycloud.entity.DistShopGood;
import cn.org.citycloud.entity.FinAcc;
import cn.org.citycloud.entity.Good;
import cn.org.citycloud.entity.Order;
import cn.org.citycloud.entity.OrderGood;
import cn.org.citycloud.entity.PayInfo;
import cn.org.citycloud.entity.RegionInfo;
import cn.org.citycloud.entity.SalesMember;
import cn.org.citycloud.entity.SalesOrderInfo;
import cn.org.citycloud.entity.ServiceCenter;
import cn.org.citycloud.entity.ShoppingCart;
import cn.org.citycloud.entity.Supplier;
import cn.org.citycloud.entity.WechatSalesMember;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.DistShopGoodDao;
import cn.org.citycloud.repository.FinAccDao;
import cn.org.citycloud.repository.FlowTemplateDao;
import cn.org.citycloud.repository.GoodsDao;
import cn.org.citycloud.repository.OrderDao;
import cn.org.citycloud.repository.OrderGoodDao;
import cn.org.citycloud.repository.PayInfoDao;
import cn.org.citycloud.repository.RegionInfoDao;
import cn.org.citycloud.repository.SalesMemberDao;
import cn.org.citycloud.repository.SalesOrderInfoDao;
import cn.org.citycloud.repository.ServiceCenterDao;
import cn.org.citycloud.repository.ShoppingCartDao;
import cn.org.citycloud.repository.SupplierDao;
import cn.org.citycloud.repository.WechatSalesMemberDao;
import cn.org.citycloud.utils.HttpClientUtil;
import cn.org.citycloud.utils.IPUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.rubyeye.xmemcached.MemcachedClient;

/**
 * 微信对接模块控制器
 * 
 * @author lanbo
 *
 */
@RestController
@RequestMapping(value = "/weixin")
@Api(tags = "微信对接", position = 1, value = "/weixin", description = "微信对接模块", consumes = "application/json")
public class WeixinController extends WeixinBaseController {

	private static Logger logger = Logger.getLogger(WeixinController.class);

	@Autowired
	private MemcachedClient cacheClient;

	@Autowired
	private WechatSalesMemberDao wechatSalesMemberDao;

	@Autowired
	private GoodsDao goodsDao;

	@Autowired
	private DistShopGoodDao distShopGoodDao;

	@Autowired
	private ShoppingCartDao cartDao;

	@Autowired
	private WeixinService weixinService;
	
	@RequestMapping(value="/distgoods",method=RequestMethod.GET)
	@ApiOperation(value = "分销商品详情", notes = "分销商品详情")
	public Object getDistGoodsDetail(@ApiParam(name="memberId",value="分销商ID",required=true)@RequestParam int memberId,@ApiParam(name="goodsId",value="商品ID",required=true)@RequestParam int goodsId)throws Exception{
		return distShopGoodDao.findByGoodsIdAndMemberId(goodsId, memberId);
	}
	
	
	
	/**
	 * 微信网页授权登录后回调
	 * 
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auth/callback", method = RequestMethod.GET)
	@ApiOperation(value = "微信网页授权登录 登录后回调", notes = "微信网页授权登录 登录后回调")
	public Object callback(@RequestParam int id, @RequestParam String code, @RequestParam String state,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 通过code换取网页授权access_token
		StringBuilder accessUrl = new StringBuilder();
		accessUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
		accessUrl.append(Constants.API_ID);
		accessUrl.append("&secret=");
		accessUrl.append(Constants.API_SECRET);
		accessUrl.append("&code=");
		accessUrl.append(code);
		accessUrl.append("&grant_type=authorization_code");

		String accessTokenStr = HttpClientUtil.getInstance().sendHttpGet(accessUrl.toString());

		ObjectMapper mapper = new ObjectMapper();

		@SuppressWarnings("unchecked")
		Map<String, Object> accessTokenMap = mapper.readValue(accessTokenStr, Map.class);

		Object errcode = accessTokenMap.get("errcode");

		if (errcode != null) {
			logger.error("通过code换取网页授权access_token失败:" + accessUrl.toString());
			String errMsg = "微信登录失败:" + accessTokenMap.get("errmsg");
			logger.error(errMsg);
			// 错误页面
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("errMsg", errMsg);
			return new ModelAndView("error", map);
		}

		// 是否获取到access_token
		String accessToken = (String) accessTokenMap.get("access_token");

		int expires_in = (int) accessTokenMap.get("expires_in");

		String openId = (String) accessTokenMap.get("openid");

		logger.info("accessToken=" + accessToken);
		logger.info("openId=" + openId);

		// 拉取用户信息(需scope为 snsapi_userinfo)
		StringBuilder userinfo = new StringBuilder();
		userinfo.append("https://api.weixin.qq.com/sns/userinfo?access_token=");
		userinfo.append(accessToken);
		userinfo.append("&openid=");
		userinfo.append(openId);
		userinfo.append("&lang=zh_CN");

		String userInfoStr = HttpClientUtil.getInstance().sendHttpGet(userinfo.toString());

		@SuppressWarnings("unchecked")
		Map<String, Object> userInfoMap = mapper.readValue(userInfoStr, Map.class);

		Object userInfoCode = userInfoMap.get("errcode");

		if (userInfoCode != null) {
			logger.error("微信用户信息获取URL:" + userinfo.toString());
			String errMsg = "微信用户信息获取失败:" + userInfoMap.get("errmsg");
			logger.error(errMsg); // 错误页面
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("errMsg", errMsg);
			return new ModelAndView("error", map);
		}

		String nickname = (String) userInfoMap.get("nickname");
		int sex = (int) userInfoMap.get("sex");
		String province = (String) userInfoMap.get("province");
		String city = (String) userInfoMap.get("city");

		String headImgUrl = (String) userInfoMap.get("headimgurl");

		// 只要在微信公众号授权登录过的微信号都算门店会员
		// 新建会员信息
		// OpenID 用户微信登录后回调
		WechatSalesMember wcMember = wechatSalesMemberDao.findByOpenId(openId);

		// 客户端IP
		String remoteIP = IPUtil.getIp(request);
		Date now = new Date();
		if (wcMember != null) {
			wcMember.setWechatAliasname(nickname);
			wcMember.setSex(sex);

			wcMember.setRegionProvName(province);
			wcMember.setRegionCityName(city);

			wcMember.setMemberAvatar(headImgUrl);
			// 登录次数
			wcMember.setMemberLoginNum(wcMember.getMemberLoginNum() + 1);
			// 上次登录时间
			wcMember.setMemberOldLoginTime(wcMember.getMemberLoginTime());
			// 上次登录ip
			wcMember.setMemberOldLoginIp(wcMember.getMemberLoginIp());
			// 当前登录ip
			wcMember.setMemberLoginIp(remoteIP);
			// 当前登录时间
			wcMember.setMemberLoginTime(now);

			wcMember.setUpdateTime(now);

			wechatSalesMemberDao.save(wcMember);
		} else {
			WechatSalesMember entity = new WechatSalesMember();
			entity.setOpenId(openId);
			entity.setWechatAliasname(nickname);
			entity.setSex(sex);
			entity.setRegionProvName(province);
			entity.setRegionCityName(city);
			entity.setMemberAvatar(headImgUrl);
			entity.setMemberLoginIp(remoteIP);
			// 会员状态
			entity.setStatus(Constants.MEMBER_STATE_OPEN);
			entity.setRegisterTime(now);
			// 当前登录ip
			entity.setMemberLoginIp(remoteIP);
			// 当前登录时间
			entity.setMemberLoginTime(now);
			// 登录次数
			entity.setMemberLoginNum(1);
			entity.setCreateTime(now);
			entity.setUpdateTime(now);

			wcMember = wechatSalesMemberDao.save(entity);
		}

		// 生成登录用Token
		String token = generateToken(openId, id, Constants.TOKEN_SECRET);

		// 将Token存入Memcached
		WeixinToken tokenEntity = new WeixinToken();
		tokenEntity.setToken(token);
		tokenEntity.setOpenId(openId);
		tokenEntity.setMemberId(id);
		tokenEntity.setStoreId(wcMember.getWechatSalesMemberId());
		tokenEntity.setAccessToken(accessToken);
		tokenEntity.setCreateTs(System.currentTimeMillis());
		tokenEntity.setExpiresIn(expires_in);

		// 如果已经存在Token 替换掉旧的Token存储
		cacheClient.set(token, Integer.parseInt(String.valueOf(expires_in)), tokenEntity);

		StringBuilder sb = new StringBuilder();
		sb.append("redirect:");
		sb.append(Constants.WSC_URL);
		sb.append(id);
		sb.append("/token/");
		sb.append(token);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("token", token);
		resultMap.put("memberId", id);
		resultMap.put("avatar", headImgUrl);
		
		return resultMap;

	}

	/**
	 * 获取分销购物车商品
	 * 
	 * @return
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/shoppingCarts", method = RequestMethod.GET)
	@ApiOperation(value = "获取购物车商品", notes = "获取购物车商品", response = ShoppingCartGroupByShop.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object getShoppingCart() throws BusinessErrorException {

		return getShoppingCartList();
	}

	/**
	 * 添加购物车商品
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/shoppingCarts", method = RequestMethod.POST)
	@ApiOperation(value = "添加购物车商品", notes = "添加购物车商品")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void addGoodsToCart(@Valid @RequestBody CartGoods cartGoods) throws BusinessErrorException {

		int goodsId = cartGoods.getGoodsId();
		// 商品信息
		Good goodItem = goodsDao.findOne(goodsId);

		if (goodItem == null) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品信息不存在");
		}

		// 商品状态
		int goodState = goodItem.getGoodsStatus();

		if (Constants.GOODS_STATE_NORMAL != goodState) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已下架或者禁售。");
		}
		// 自动下架时间
		Date offlineTime = goodItem.getAutoDownTime();
		Date now = new Date();

		if (offlineTime.getTime() < now.getTime()) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已下架。");
		}

		// 商品已经没有库存了
		if (goodItem.getSurplus() <= 0) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已经售罄。");
		}

		// TODO
		// BigDecimal goodsPrice = BigDecimal.ZERO;
		// if(1 == goodItem.getDiscountFlg()) {
		// BigDecimal goodsPrice = goodItem.get
		//
		// } else {
		//
		// }

		BigDecimal num = new BigDecimal(cartGoods.getGoodsNum());
		BigDecimal goodsPayPrice = goodItem.getSalePrice().multiply(num);

		ShoppingCart goods = cartDao.findByOpenIdAndGoodsId(getOpenId(), goodsId);
		// 购物车中已经有的商品
		if (goods != null) {
			goods.setGoodsNum(cartGoods.getGoodsNum() + goods.getGoodsNum());

			// 实际成交价
			goods.setGoodsPayPrice(goods.getGoodsPayPrice().add(goodsPayPrice));
			goods.setUpdateTime(now);

			cartDao.save(goods);
		} else {
			ShoppingCart cartGood = new ShoppingCart();
			cartGood.setOpenId(getOpenId());
			cartGood.setGoodsId(cartGoods.getGoodsId());
			cartGood.setSupplierId(goodItem.getSupplierId());
			cartGood.setMemberId(getMemberId());

			cartGood.setGoodsName(goodItem.getGoodsName());
			cartGood.setGoodsPrice(goodItem.getSalePrice());
			cartGood.setGoodsNum(cartGoods.getGoodsNum());
			cartGood.setGoodsImage(goodItem.getGoodsImage());
			// 实际成交价
			cartGood.setGoodsPayPrice(goodsPayPrice);
			cartGood.setGoodsSpec(goodItem.getStandard());
			// 状态
			cartGood.setCartStatus(3);

			cartGood.setCreateTime(now);
			cartGood.setUpdateTime(now);
			cartDao.save(cartGood);
		}

	}

	/**
	 * 删除购物车中的商品
	 */
	@RequestMapping(value = "/shoppingCarts/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除购物车中的商品", notes = "删除购物车中的商品", response = ShoppingCartGroupByShop.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object deleteGoodsFromCart(@PathVariable int id) {

		ShoppingCart cart = cartDao.findByShoppingCartIdAndOpenId(id, getOpenId());

		if (cart != null)

			cartDao.delete(cart);

		return getShoppingCartList();

	}

	/**
	 * 批量删除购物车中的商品
	 */
	@RequestMapping(value = "/shoppingCarts/batchDelele", method = RequestMethod.POST)
	@ApiOperation(value = "批量删除购物车中的商品", notes = "批量删除购物车中的商品", response = ShoppingCartGroupByShop.class, responseContainer = "List")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public Object batchDeleteGoodsFromCart(@Valid @RequestBody List<BatchDelete> ids) {

		for (BatchDelete batchDelete : ids) {
			ShoppingCart cart = cartDao.findByShoppingCartIdAndOpenId(batchDelete.getId(), getOpenId());
			if (cart != null)
				cartDao.delete(cart);
		}

		return getShoppingCartList();
	}

	/**
	 * 修改商品数量
	 * 
	 * @throws BusinessErrorException
	 */
	@RequestMapping(value = "/shoppingCarts/id/{id}/number/{num}", method = RequestMethod.PUT)
	@ApiOperation(value = "修改商品数量", notes = "修改商品数量")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "header") })
	public void editCartGoods(@PathVariable int id, @PathVariable int num) throws BusinessErrorException {

		ShoppingCart cartGoods = cartDao.findByShoppingCartIdAndOpenId(id, getOpenId());
		if (cartGoods == null) {
			throw new BusinessErrorException(ErrorCodes.NO_DATA, "此商品不存在或已删除！");
		}

		// 商品信息
		Good goodItem = goodsDao.findOne(cartGoods.getGoodsId());

		if (goodItem == null) {

			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品信息不存在");
		}

		// 商品状态
		int goodState = goodItem.getGoodsStatus();

		if (Constants.GOODS_STATE_NORMAL != goodState) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已下架或者禁售");
		}
		// 自动下架时间
		Date offlineTime = goodItem.getAutoDownTime();
		Date now = new Date();

		if (offlineTime.getTime() < now.getTime()) {
			throw new BusinessErrorException(ErrorCodes.WRONG_GOODS, "此商品已下架");
		}

		cartGoods.setGoodsNum(num);

		// 商品数量
		BigDecimal numDec = new BigDecimal(num);
		// 实际成交价
		cartGoods.setGoodsPayPrice(goodItem.getSalePrice().multiply(numDec));

		cartGoods.setUpdateTime(now);

		cartDao.save(cartGoods);
	}

	/**
	 * @return 购物车商品列表
	 */
	private List<ShoppingCart> getShoppingCartList() {

		List<ShoppingCart> cartGoods = cartDao.findByMemberIdAndOpenIdOrderByCreateTimeDesc(getMemberId(), getOpenId());

		return cartGoods;
	}

	/**
	 * 1)生成分销预订单信息和订单商品信息
	 * 
	 * @throws BusinessErrorException
	 * 
	 * 
	 */
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	@ApiOperation(value = "微信前端调用 返回支付预订单信息", notes = "生成预订单信息和订单商品")
	public Object submitOrder(@ApiParam(value = "预订单信息", required = true) @Valid @RequestBody DistPreOrder preOrder,
			HttpServletRequest request) throws BusinessErrorException {
		return weixinService.submitOrder(preOrder, getMemberId(), getOpenId());
	}

	/**
	 * 
	 * 2)支付订单
	 */

	/**
	 * 
	 * 3)分销订单支付完成后 回调
	 */
	/**
	 * 支付结果通知处理(调试之用)
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/testPayNotify/{payId}", method = RequestMethod.POST)
	@ApiOperation(value = "分销订单支付结果通知处理(调试)", notes = "调用后订单状态改为已支付")
	public void testHandleWSxpayNotify(@PathVariable int payId) {
		weixinService.testHandleWSxpayNotify(payId);
	}

	/**
	 * 生成token值
	 * 
	 * @param user
	 * @return
	 */
	private String generateToken(String openid, int store_id, String secret) {
		JWTSigner jwtSigner = new JWTSigner(secret);
		Map<String, Object> claims = new HashMap<String, Object>();

		claims.put("openid", openid);
		claims.put("store_id", String.valueOf(store_id));
		claims.put("time", System.currentTimeMillis());
		String token = jwtSigner.sign(claims);

		return token;
	}

}
