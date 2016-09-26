package cn.org.citycloud.constants;

/**
 * 常量类
 * 
 * @author lanbo
 *
 */
public class Constants
{
    // 微信登录回调地址
    public static final String CALLBACK_URL = "http://o2o.syisy.com/zwhs_client_api/auth/callback";
    
    // 用户微信端外网地址
    public static final String WSC_URL = "http://o2o.syisy.com/#/store/";
    
    // 调用JavaScript API的网页url
    public static final String JS_PAY_URL = "http://o2o.syisy.com/preorder";
    
    public static final String JS_SERVICE_URL = "http://o2o.syisy.com/services/order";
    
    // 服务预约支付回调URL
    public static final String SERVICE_PAY_NOTIFY_URL = "http://o2o.syisy.com/zwhs_client_api/servicePayNotify";
    
    public static final long TOKEN_EXPIRES_IN = 7200;
    
    public static final String TOKEN_SECRET = "IFFa52XkBEQ9AoO8";
    
    // 善融大宗微信APPID
    public static final String API_ID = "wx50c7c4d3502bc0e7";
    
    public static final String API_SECRET = "14c5b292992281fb7b65ba773ddfce2f";
    
    public static final String MCH_ID = "1283969101";
    
    public static final String KEY = "00452613778959026465898723358440";
    
    // 会员的状态 1为开启 0为关闭
    public static final int MEMBER_STATE_OPEN = 1;
    
    public static final int MEMBER_STATE_CLOSED = 0;
    
    // 分销商状态 （1 已审核（正常）   2 待审核     3 驳回  4 冻结） 
    public static final int SALES_MEMBER_SHENHE = 1;
    
    public static final int SALES_MEMBER_DAISHENHE = 2;
    
    public static final int SALES_MEMBER_BOHUI = 3;
    
    public static final int SALES_MEMBER_DONGJIE = 4;
    
    //分销商品状态（1 上架  2 下架）
    public static final int DIST_GOODS_ONLINE=1;
    
    public static final int DIST_GOODS_OFFLINE=2;
    
    
    // 订单状态：0(已取消)；10(默认):待支付；20:待发货； 40:已发货；50:待评价； 60:已完成
    public static final int ORDER_STATUS_CANCELD = 0;
    
    public static final int ORDER_STATUS_DEFAULT = 10;
    
    public static final int ORDER_STATUS_PAYED = 20;
    
    public static final int ORDER_STATUS_SENDED = 40;
    
    public static final int ORDER_STATUS_EVALUATED = 50;
    
    // 订单类型   （1  普通订单   2  分销订单）
    public static final int ORDER_TYPE_NORMAL = 1;
    
    public static final int ORDER_TYPE_DIST= 2;
    
    // 支付方式代码: 1 银联;2 支付宝;3 微信
    public static final int PAY_TYPE_BANK = 1;
    
    public static final int PAY_TYPE_PAYBAO = 2;
    
    public static final int PAY_TYPE_WEIXIN = 3;
    
    // 支付状态
    public static final int PAY_STATE_PAYED = 1;
    
    // 商品类型 1默认2团购商品3限时折扣商品4组合套装5赠品
    public static final int GOODS_TYPE_DEFAULT = 1;
    
    // 评价状态 0 未评价 1买家评价 2卖家评价 3双方互评
    public static final int GEVAL_TYPE_BUYERS = 1;
    
    // 商品状态（ 1  正常   2  下架  3 推荐）10违规（禁售）
    public static final int GOODS_STATE_OFFLINE = 2;
    
    public static final int GOODS_STATE_NORMAL = 1;
    
    public static final int GOODS_STATE_RECOMMEND = 3;
    
    public static final int GOODS_STATE_DISABLE = 10;
    
    // 服务类型 1 上门服务 ;2 到店服务
    public static final int SERVICE_TYPE_CALL = 1;
    
    public static final int SERVICE_TYPE_STORE = 2;
    
    // 优惠劵状态 10为正常，20为失效
    public static final int COUPON_STATUS_NORMAL = 10;
    
    public static final int COUPON_STATUS_INVALID = 20;
    
    // 优惠劵使用状态
    public static final int COUPON_UNUSED = 10;
    
    public static final int COUPON_USED = 20;
    
    // 服务状态 0下架，1正常，10违规（禁售）
    public static final int SERVICE_STATE_OFFLINE = 0;
    
    public static final int SERVICE_STATE_NORMAL = 1;
    
    public static final int SERVICE_STATE_DISABLE = 0;
    
    // 服务审核 1 审核通过，0未通过，2 驳回
    public static final int SERVICE_VERIFY_PASS = 1;
    
    public static final int SERVICE_VERIFY_NOTPASS = 0;
    
    public static final int SERVICE_VERIFY_REJECT = 2;
    
    // 服务订单状态：0(已取消)10(默认):未付款;20:已付款;30:已接单;40:已使用;50:已评价
    public static final int SERVICE_ORDER_CANCELD = 0;
    
    public static final int SERVICE_ORDER_DEFAULT = 10;
    
    public static final int SERVICE_ORDER_PAYED = 20;
    
    public static final int SERVICE_ORDER_ACCEPT = 30;
    
    public static final int SERVICE_ORDER_USED = 40;
    
    public static final int SERVICE_ORDER_EVALUATED = 50;
    
    // 报名类型 教练：1 陪练：2
    public static final int APPLY_TYPE_COACH = 1;
    
    public static final int APPLY_TYPE_TRAIN = 2;
    
    // 0 默认（未认证）  1 已认证（待审核）     2  已审核    3  驳回  4  冻结
    public static final int SHOP_STATUS_OPEN = 2;
    
	public static final String MAGIC_CODE = "888888";
	
	// 场地状态0 （未申请）待审核  1 正常使用（已审核） 2 已申请（未审核）  3  休息  4 驳回  5 禁用
	 public static final int PLACE_STATUS_NORMAL = 1;
	 
	 // 账户类型(1：平台；2：门店)
	 public static final int ACCOUNT_TYPE_PLATFORM = 1;
	 
	 public static final int ACCOUNT_TYPE_STADIUM = 2;
	 
	 // 场次支付状态：0(已取消)10(默认):未付款;20:已付款;
	 
	 // 供应商状态：0 默认（未认证）  1 已认证（待审核）     2  已审核    3  驳回  4  冻结
    public static final int SUPPLIER_STATUS_VERIFING = 1;
    
    public static final int SUPPLIER_STATUS_VERIFIED = 2;
    
    public static final int SUPPLIER_STATUS_REJECTED = 3;
    
    public static final int SUPPLIER_STATUS_FREEZED = 4;
    
    // 排序DESC ASC
    public static final String SORT_DESC = "DESC";
    
    public static final String SORT_ASC = "ASC";
    
    // 账户类型(1：平台；2：供应商  3：服务中心  4 ：分销商)
    public static final int ACC_TYPE_SRDZ = 1;
    
    public static final int ACC_TYPE_SUPPLIER = 2;
    
    public static final int ACC_TYPE_SS = 3;
    
    public static final int ACC_TYPE_DIST = 4;

    /************************* 站内信platform *************************/
    /** 管理后台 */
    public static int MSG_ADMIN = 1;

    /** 供应商 */
    public static int MSG_SUPPLIER = 2;

    /** 服务中心 */
    public static int MSG_SERVICE = 3;

    /** 用户 */
    public static int MSG_USER = 4;
    
    /** 平安银行支付前后台通知 */
   //前台调用返回商户
  	public static String frontUrl = "http://60.166.12.102:9004/srdz-client-api/pinanFrontNotify";

  	//平安银行调用 通知服务（测试环境）
  	public static String backUrl = "http://60.166.12.102:9004/srdz-client-api/pinanPayNotify";   
	 
}
