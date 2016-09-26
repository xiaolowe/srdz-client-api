package cn.org.citycloud.constants;

/**
 * 
 * Code	Msg	HTTP Status
//0	请求成功	200
//-1	没有数据	400
//001	不合法的凭证	402
//002	不合法的参数	400
//003	不合法的请求格式	400
//004	不合法的请求字符	400
//005	不支持的图片格式	400
//006	缺少zwhs_token参数	402
//007	zwhs_token超时	402
//008	POST的数据包为空	400
//009	解析JSON/XML内容错误	500
//010	不存在的用户	200
//011	参数错误(invalid parameter)	400
//012	系统错误(system error)	500
//013	日期格式错误	400
//014	日期范围错误	400
//015	POST数据参数不合法	400
//016	帐号不合法	400
//017	页面参数不合法	400
//018	时间区间不合法	400
//019	帐号不合法	400
//020	门店ID不合法	400
//021	商品信息不存在	400
//022	订单错误信息	400
//023	此宝贝已经收藏	400
//024	优惠劵领取错误	400
//025	服务劵密码错误	400
//026	店铺服务信息不存在	400
//027	财务错误	400
 * 028   收货地址错误 400
 * 029 验证码错误 400
 * 
 * 
 * 
 * @author lanbo
 *
 */
public class ErrorCodes
{
    
    public final static String NO_DATA = "-1";
    
    public final static String TOKEN_ERROR = "001";
    
    public final static String PARAM_ERROR = "002";
    
    public final static String TYPE_MISMATCH = "003";
    
    public final static String HTTPMESSAGE_NOT_READABLE = "008";
    
    public final static String NON_EXIST_MEMBER = "010";
    
    public final static String INVALID_PARAMETER = "011";
    
    public final static String SYSTEM_ERROR = "012";
    
    public final static String ACCOUNT_ERROR = "016";
    
    public final static String WRONG_MEMBER = "019";
    
    public final static String WRONG_STORE = "020";
    
    public final static String WRONG_GOODS = "021";
    
    public final static String ORDER_ERROR = "022";
    
    public final static String ALREADY_COLLECTED = "023";
    
    public final static String COUPON_ERROR = "024";
    
    public final static String SERVICE_CODE_ERROR = "025";
    
    public final static String WRONG_STORE_SERVICE = "026";
    
    public final static String WRONG_ADDRESS = "028";
    
    public final static String AUTH_CODE_ERROR = "029";

    public final static String NOT_FOUND = "030";

    public final static String MULTIP_FAVORITE = "031";
}
