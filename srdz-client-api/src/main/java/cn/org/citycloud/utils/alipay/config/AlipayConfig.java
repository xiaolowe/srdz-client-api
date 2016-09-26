package cn.org.citycloud.utils.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088221673443516";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	// MD5密钥，安全检验码，由数字和字母组成的32位字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
    public static String key = "wvvxe889cao67xjwuzoubn0i0ynde4on";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "http://106.75.16.170:8080/srdz-client-api/alipay/alipayNotify";

	public static String notify_url = "http://60.166.12.102:9004/srdz-client-api/alipay/alipayNotify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "";

	// 签名方式
	public static String sign_type = "MD5";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/** 支付宝开放平台需要自己设置的公钥 */
	public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	/** 支付宝开放平台需要自己设置的私钥 */
	public static String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM/liAaTMWews119\n" +
			"kKsmE1XA40dPkX3nQCifUuPPBYcKNgdAya3X51B+hJTQWx8UA53JugR+K670G4lf\n" +
			"1d3if2uZo1fe1cl/2VeHsi0hvTj3le9le/BQ1gqZ07tvHAaUjguuJnKkWT+mL+86\n" +
			"f4nlY61lt9TV8nXQxiRIiP+l3mefAgMBAAECgYAxzobRNHBwrBe8vTlsl3moYMbS\n" +
			"rdBofn+Te+Aq7c0gNiUNhcsLmB813Km80VITVwMqyUqEiwnCJ391YP1m5D2sRgk6\n" +
			"8lKpt33OQXy1HMl5UegErPlxuVYLZcwCugSc/ipEkdQH6076ioLwpQKlKTFopyiM\n" +
			"zctzTlAvAJJdNWqtcQJBAO/+jbHqg9NPI05NWNXbcsgohN0Laj1UZ8YbhyVx2S2Q\n" +
			"t+GwcQGBYbmf4wbD/btrsWGcX1BYUuTij8rlM6cW8CcCQQDdwvhTNXU3SMcCtWGJ\n" +
			"Mb83iqhDPY9n88HGovNg2DBjwclzp+L2IRf+T4cGsb7fyhKrmiF/iFiqYpJ5Fbip\n" +
			"U//JAkEAz5JAJZCrQdlhtPjLC/TI2vvIppKX6cGWG891XVqRt4pCsG3135Jy3qS+\n" +
			"K/zZ+P9VJRKlkY1C7PpT2e3RdgQt4wJAdZlpGqckq9aaQqNxP9Hztz5+kHnazKOF\n" +
			"RRMwwVCkCKLBc+W6BA/0q1X9It2rfP2p/PpVfU4OxvxS9IdCT/lNkQJAMre1cfvE\n" +
			"rp6EyAcyZZf5PTj5nypq3BYeSqrP7FY+HIm3col5QIKccI7dmpwbRdp8ezKAqJEh\n" +
			"kJIbIWwwlGJWeA==";

	/** 开发者应用ID */
	public static String APP_ID = "2016060601489283";

	/** 开放平台url */
	public static String OPENAPI_URL = "https://openapi.alipay.com/gateway.do";
}

