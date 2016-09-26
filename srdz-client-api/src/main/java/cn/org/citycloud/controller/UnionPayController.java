package cn.org.citycloud.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.org.citycloud.bean.UnionPayBean;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.service.PaymentService;
import cn.org.citycloud.utils.unionsdk.AcpService;
import cn.org.citycloud.utils.unionsdk.LogUtil;
import cn.org.citycloud.utils.unionsdk.SDKConfig;
import cn.org.citycloud.utils.unionsdk.SDKConstants;
import cn.org.citycloud.utils.unionsdk.UnionPayBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "银联支付", value = "/unionPay", description="银联支付接口", consumes = "application/json")
public class UnionPayController extends BaseController {

	private static Logger logger = Logger.getLogger(WeixinController.class);

	 @Autowired
	 private PaymentService paymentService;
	 

	/**
	 * 银联支付接口
	 * 
	 * @param request
	 * @return
	 * @throws BusinessErrorException 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/unionPay", method = RequestMethod.POST)
	@ApiOperation(value = "银联支付", notes = "银联支付")
	public Object unionPay(@Valid @RequestBody UnionPayBean bean) throws IOException, BusinessErrorException {

		Map<String, String> requestData = new HashMap<String, String>();

		/*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
		requestData.put("version", UnionPayBase.version);
		requestData.put("encoding", UnionPayBase.encoding_UTF8);
		requestData.put("signMethod", "01");
		requestData.put("txnType", "01");
		requestData.put("txnSubType", "01");
		requestData.put("bizType", "000201");
		requestData.put("channelType", "07");

		/*** 商户接入参数 ***/
		requestData.put("merId", bean.getMerId());
		requestData.put("accessType", "0");
		requestData.put("orderId", bean.getOrderId());
		requestData.put("txnTime", UnionPayBase.getCurrentTime());
		requestData.put("currencyCode", "156");
		requestData.put("txnAmt", changeToFen(bean.getTxnAmt()));
		
		
		requestData.put("frontUrl", UnionPayBase.frontUrl);

		requestData.put("backUrl", UnionPayBase.backUrl);

		/** 请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面 **/
		Map<String, String> submitFromData = AcpService.sign(requestData, UnionPayBase.encoding_UTF8); // 报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl(); // 获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData, UnionPayBase.encoding_UTF8); // 生成自动跳转的Html表单

		logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据：" + html);
		// 将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过

		return html;

	}

	@RequestMapping(value = { "/unionpayNotify" },method = RequestMethod.POST)
	@ApiOperation(value = "银联支付回调", notes = "银联回调")
	public void unionpayNotify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		logger.info("BackRcvResponse接收后台通知开始");
		
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes(encoding), encoding);
				valideData.put(key, value);
			}
		}

		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			logger.info("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			logger.info("验证签名结果[成功].");
			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
			logger.info("返回对象："+valideData);
			String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
			logger.info("前台传过来的订单号："+orderId);
			if (orderId.startsWith("88888")) { 	//前台传过来的参数 自定义添加88888, 增加订单号长度
				orderId = orderId.replaceAll("88888", "");
			}
			logger.info("处理过的订单号："+orderId);
			this.paymentService.callBackPay(orderId);
			
		}
		logger.info("BackRcvResponse接收后台通知结束");
		//返回给银联服务器http 200  状态码
		resp.getWriter().print("ok");
		
		
	}
	
	
	@RequestMapping(value = { "/frontNotify" },method = RequestMethod.POST)
	@ApiOperation(value = "银联前台回调", notes = "银联前台回调")
	public void unionpayFrontNotify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		logger.info("FrontRcvResponse前台接收报文返回开始");

		String encoding = req.getParameter(SDKConstants.param_encoding);
		logger.info("返回报文中encoding=[" + encoding + "]");
		String pageResult = "";
		if (UnionPayBase.encoding_UTF8.equalsIgnoreCase(encoding)) {
			pageResult = "/utf8_result.jsp";
		} else {
			pageResult = "/gbk_result.jsp";
		}
		Map<String, String> respParam = getAllRequestParam(req);

		// 打印请求报文
		logger.info(respParam);

		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet()
					.iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes(encoding), encoding);
				page.append("<tr><td width=\"30%\" align=\"right\">" + key
						+ "(" + key + ")</td><td>" + value + "</td></tr>");
				valideData.put(key, value);
			}
		}
		if (!AcpService.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
			logger.info("验证签名结果[失败].");
		} else {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
			logger.info("验证签名结果[成功].");
			System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
		}
		req.setAttribute("result", page.toString());
		req.getRequestDispatcher(pageResult).forward(req, resp);

		logger.info("FrontRcvResponse前台接收报文返回结束");
		
		
	}

	
	private String changeToFen(String priceOfYuan) throws BusinessErrorException {
		BigDecimal price = null;
		try {
		    price = new BigDecimal(priceOfYuan);
			price = price.multiply(new BigDecimal(100));
			
		} catch (Exception e) {
			throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "金额参数错误，请重新输入");
		}
		
		return String.valueOf(price.intValue());
	}
	

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}

}
