package cn.org.citycloud.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import com.ecc.emp.data.KeyedCollection;
import com.sdb.payclient.bean.exception.CsiiException;
import com.sdb.payclient.core.PayclientInterfaceUtil;

import cn.org.citycloud.bean.PinanPayBean;
import cn.org.citycloud.bean.PinanPaySearchBean;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "平安银行支付", value = "/pinanPay", description="平安银行支付接口", consumes = "application/json")
public class PinanPayController extends BaseController {

	private static Logger logger = Logger.getLogger(PinanPayController.class);

	 @Autowired
	 private PaymentService paymentService;
	 
	 
	 
	/**
	 * 平安银行支付接口
	 * @param request
	 * @return
	 * @throws BusinessErrorException 
	 * @throws CsiiException 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/pinanPay", method = RequestMethod.POST)
	@ApiOperation(value = "平安银行支付", notes = "平安银行支付")
	public Object pinanPay(@Valid @RequestBody PinanPayBean bean) throws IOException, BusinessErrorException, CsiiException {
		
		logger.info("进入平安银行支付方法！");

		PayclientInterfaceUtil util = new PayclientInterfaceUtil();  //建立客户端实例
		KeyedCollection signDataput = new KeyedCollection("signDataput"); 
	    String orig="";  //原始数据
	    String sign="";  //签名数据
	    String encoding = "GBK";
	    
	    Map<String, String>  map = new HashMap<String,String>();
	    
	    try {
	    	
	    	KeyedCollection inputOrig = new KeyedCollection("inputOrig");
	    	
	    	Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmss" );
			String timestamp = formatter.format( date );  //时间
			String datetamp = timestamp.substring(0, 8);  //日期
			
			inputOrig.put("masterId", bean.getMasterId());  //商户号，注意生产环境上要替换成商户自己的生产商户号
			inputOrig.put("orderId",bean.getMasterId()+datetamp+bean.getOrderId());  //订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
			inputOrig.put("currency","RMB");  //币种，目前只支持RMB
			inputOrig.put("amount", bean.getAmount());  //订单金额，12整数，2小数
			inputOrig.put("paydate",timestamp);  //下单时间，YYYYMMDDHHMMSS	
			inputOrig.put("objectName",bean.getObjectName());  //订单款项描述（商户自定）
			inputOrig.put("validtime","0");  //订单有效期(秒)，0不生效	
			inputOrig.put("remark", bean.getRemark());  //备注字段（商户自定）
			
			
	    	signDataput = util.getSignData(inputOrig);	 //签名
	    	
	    	logger.info("---签名结果---"+signDataput);
		    
		    orig = (String)signDataput.getDataValue("orig");  //获取原始数据
		    logger.info("---原始数据---"+orig);
		    sign = (String)signDataput.getDataValue("sign");  //获取签名数据
		    logger.info("---签名数据---"+sign);		    
		    
		    orig = PayclientInterfaceUtil.Base64Encode(orig,encoding);  //原始数据先做Base64Encode转码
		    logger.info("---Base64Encode转码后原始数据---"+orig);
		    sign = PayclientInterfaceUtil.Base64Encode(sign,encoding);  //签名数据先做Base64Encode转码
		    logger.info("---Base64Encode转码后签名数据---"+sign);
		    
		    orig = java.net.URLEncoder.encode(orig, encoding);  //Base64Encode转码后原始数据,再做URL转码
		    logger.info("---Base64Encode转码URL转码后原始数据---"+orig);
		    sign = java.net.URLEncoder.encode(sign, encoding);  //Base64Encode转码后签名数据,再做URL转码
		    logger.info("---Base64Encode转码URL转码后签名数据---"+sign);
		   
		    map.put("orig", orig);
		    map.put("sign", sign);
		    map.put("date", datetamp);
		    
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	    
	    return map;

	}

	/**
	 * 平安银行支付查询接口
	 * @param request
	 * @return
	 * @throws BusinessErrorException 
	 * @throws CsiiException 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/pinanPaySearch", method = RequestMethod.POST)
	@ApiOperation(value = "平安银行支付查询", notes = "平安银行支付查询")
	public Object pinanPaySearch(@Valid @RequestBody PinanPaySearchBean bean) throws IOException, BusinessErrorException, CsiiException {
		
		logger.info("进入平安银行支付查询方法！");

		com.sdb.payclient.core.PayclientInterfaceUtil  util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection input = new com.ecc.emp.data.KeyedCollection("input");
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");
		
		input.put("masterId",bean.getMasterId());  //商户号，注意生产环境上要替换成商户自己的生产商户号
	    input.put("date",bean.getDate());  //对账日期，格式：YYYYMMDD
	    
	    try {
	    	output = util.execute(input,"KH0003"); //执行发送（KH0003）每日对账单查询请求，并返回结果对象
			String errorCode = (String)output.getDataValue("errorCode");
			String errorMsg = (String)output.getDataValue("errorMsg");
			
			logger.info("---每日对账单查询结果详细信息---"+output);
			
			if((errorCode == null || errorCode.equals(""))&& (errorMsg == null || errorMsg.equals(""))){
				logger.info("---总金额---"+output.getDataValue("sumAmount"));
				logger.info("---总笔数---"+output.getDataValue("sumCount"));
				com.ecc.emp.data.IndexedCollection orderList = (com.ecc.emp.data.IndexedCollection) output.getDataElement("iOrderListDetail");
				for(int i=0;i<orderList.size();i++){
					com.ecc.emp.data.KeyedCollection orderDetail = (com.ecc.emp.data.KeyedCollection) orderList.getElementAt(i);
					logger.info("---订单状态---"+orderDetail.getDataValue("status"));
					logger.info("---支付完成时间---"+orderDetail.getDataValue("date"));
					logger.info("---手续费金额---"+orderDetail.getDataValue("charge"));
					logger.info("---商户号---"+orderDetail.getDataValue("masterId"));
					logger.info("---订单号---"+orderDetail.getDataValue("orderId"));
					logger.info("---币种---"+orderDetail.getDataValue("currency"));
					logger.info("---订单金额---"+orderDetail.getDataValue("amount"));
					logger.info("---下单时间---"+orderDetail.getDataValue("paydate"));
					logger.info("---商品描述---"+orderDetail.getDataValue("objectName"));
					logger.info("---订单有效期---"+orderDetail.getDataValue("validtime"));
					logger.info("---备注---"+orderDetail.getDataValue("remark"));
					logger.info("---本金清算标志---"+orderDetail.getDataValue("settleflg"));  //1已清算，0待清算
					logger.info("---本金清算时间---"+orderDetail.getDataValue("settletime"));
					logger.info("---手续费清算标志---"+orderDetail.getDataValue("chargeflg"));  //1已清算，0待清算
					logger.info("---手续费清算时间---"+orderDetail.getDataValue("chargetime"));
				}
				return orderList;
			}else{
				logger.info("---错误码---"+output.getDataValue("errorCode"));
				logger.info("---错误说明---"+output.getDataValue("errorMsg"));
			}
	    }catch (Exception e) {
			e.printStackTrace();
		 }
	    return null;

	}
	
	@RequestMapping(value = { "/pinanPayNotify" },method = RequestMethod.POST)
	@ApiOperation(value = "平安银行支付回调", notes = "平安银行回调")
	public void unionpayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, CsiiException {
		
		logger.info("进入后台回调通知方法");
		
		com.sdb.payclient.core.PayclientInterfaceUtil  util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");  
		
		String encoding = "GBK";
		
		//模拟银行返回后台通知原始数据，实际页面接收程序应为：
		String orig = request.getParameter("orig");
//		String orig="PGtDb2xsIGlkPSJvdXRwdXQiIGFwcGVuZD0iZmFsc2UiPjxmaWVsZCBpZD0ic3RhdHVzIiB2YWx1ZT0iMDEiLz48ZmllbGQgaWQ9ImRhdGUiIHZhbHVlPSIyMDE0MDUxMjExMzk0MyIvPjxmaWVsZCBpZD0iY2hhcmdlIiB2YWx1ZT0iMTAiLz48ZmllbGQgaWQ9Im1hc3RlcklkIiB2YWx1ZT0iMjAwMDMxMTE0NiIvPjxmaWVsZCBpZD0ib3JkZXJJZCIgdmFsdWU9IjIwMDAzMTExNDYyMDE0MDUxMjc3ODkxNzY5Ii8+PGZpZWxkIGlkPSJjdXJyZW5jeSIgdmFsdWU9IlJNQiIvPjxmaWVsZCBpZD0iYW1vdW50IiB2YWx1ZT0iLjAxIi8+PGZpZWxkIGlkPSJwYXlkYXRlIiB2YWx1ZT0iMjAxNDA1MTIxMTQwMTUiLz48ZmllbGQgaWQ9InJlbWFyayIgdmFsdWU9IiIvPjxmaWVsZCBpZD0ib2JqZWN0TmFtZSIgdmFsdWU9IktIcGF5Z2F0ZSIvPjxmaWVsZCBpZD0idmFsaWR0aW1lIiB2YWx1ZT0iMCIvPjwva0NvbGw+";
		logger.info("---银行返回后台通知原始数据---"+orig); 
		
		//模拟银行返回后台通知原始数据，实际页面接收程序应为：
		String sign = request.getParameter("sign");
//		String sign="YjA4ZmU1NWM3MTA0Yzc2MmQ2ZjdmN2RkNmQ1NGUzNDQyNzE3OTFjZjlmYjlmZDE1NjZhZGQ0MTI1YmIzNTAxZDRhNTljOGY0NDU1NDkzMmVlMzAwYzg3MTEyNjBhMjk5Njg2OGY0OGU3ZGY1Y2I2OGFiZjdiNDI0NjkyYWI4ODAwZjgwNDM3NWVlNGYxNTFiYTJhMmIwYmJiOWE2OTIzZWNhMmY3YmIxOTRkNTY0NmQwMzQzMzQ0MmUxZjRiYzZhZmUzNGYyZWY2YWNmNTE2MDgxZDEzN2ZiYWM1YjA1MDc1ODVjNjhhNThlZGM3ODE3MDFmNzY4MjEzODQwYzQ0OA==";
		logger.info("---银行返回后台通知签名数据---"+sign); 
		
		try {			
			orig = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(orig,encoding);
			sign = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(sign,encoding);
			logger.info("---Base64Decode解码后的后台通知原始数据---"+orig); 
			logger.info("---Base64Decode解码后的后台通知签名数据---"+sign);
			
			boolean result = util.verifyData(sign,orig);
			logger.info("---后台通知验签结果---"+result);
			if(result){
				output = util.parseOrigData(orig);
				logger.info("---订单详细信息---"+output);
				logger.info("---订单状态---"+output.getDataValue("status"));
				logger.info("---支付完成时间---"+output.getDataValue("date"));
				logger.info("---手续费金额---"+output.getDataValue("charge"));
				logger.info("---商户号---"+output.getDataValue("masterId"));
				logger.info("---订单号---"+output.getDataValue("orderId"));
				logger.info("---币种---"+output.getDataValue("currency"));
				logger.info("---订单金额---"+output.getDataValue("amount"));
				logger.info("---下单时间---"+output.getDataValue("paydate"));
				logger.info("---商品描述---"+output.getDataValue("objectName"));
				logger.info("---订单有效期---"+output.getDataValue("validtime"));
				logger.info("---备注---"+output.getDataValue("remark"));
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		
		
	}
	
	
	@RequestMapping(value = { "/pinanFrontNotify" },method = RequestMethod.POST)
	@ApiOperation(value = "平安银行前台回调", notes = "平安银行前台回调")
	public Object unionpayFrontNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CsiiException {
		
		logger.info("进入前台通知方法");

		com.sdb.payclient.core.PayclientInterfaceUtil  util = new com.sdb.payclient.core.PayclientInterfaceUtil();
		com.ecc.emp.data.KeyedCollection output = new com.ecc.emp.data.KeyedCollection("output");  
		
		String encoding = "GBK";
		
		//模拟银行返回通知原始数据，实际页面接收程序应为：
		String orig = request.getParameter("orig");
//		String orig="PGtDb2xsIGlkPSJvdXRwdXQiIGFwcGVuZD0iZmFsc2UiPjxmaWVsZCBpZD0ic3RhdHVzIiB2YWx1%0AZT0iMDEiLz48ZmllbGQgaWQ9ImRhdGUiIHZhbHVlPSIyMDE0MDUwOTA4NTUwMiIvPjxmaWVsZCBp%0AZD0iY2hhcmdlIiB2YWx1ZT0iMTAiLz48ZmllbGQgaWQ9Im1hc3RlcklkIiB2YWx1ZT0iMjAwMDMx%0AMTE0NiIvPjxmaWVsZCBpZD0ib3JkZXJJZCIgdmFsdWU9IjIwMDAzMTExNDYyMDE0MDUwOTI1OTk1%0AMTE0Ii8%2BPGZpZWxkIGlkPSJjdXJyZW5jeSIgdmFsdWU9IlJNQiIvPjxmaWVsZCBpZD0iYW1vdW50%0AIiB2YWx1ZT0iLjAxIi8%2BPGZpZWxkIGlkPSJwYXlkYXRlIiB2YWx1ZT0iMjAxNDA1MDkwODU1MzAi%0ALz48ZmllbGQgaWQ9InJlbWFyayIgdmFsdWU9IiIvPjxmaWVsZCBpZD0ib2JqZWN0TmFtZSIgdmFs%0AdWU9IktIcGF5Z2F0ZSIvPjxmaWVsZCBpZD0idmFsaWR0aW1lIiB2YWx1ZT0iMCIvPjwva0NvbGw%2B%0A";
		logger.info("---银行返回前台通知原始数据---"+orig); 
		
		//模拟银行返回通知原始数据，实际页面接收程序应为：
		String sign = request.getParameter("sign");
//		String sign="MjY5YzJlMDBhMzcyZTJkNWJjYjAxMzhmNGMxNmRkNDVjNjVjYTY3YzhiMjc1NTZhNTk0MTI0MzE5%0AN2Q1MWZkNWI5OTMxNzJhZTJiZDEyNDNmMjE3ZTk4MjU1N2E2YzAzOGI1YjI2YTQ0ZWU0M2EyNjUx%0AZTdmNjk2NDMzMDZhNTM5Y2NjMDM0YzJjZjJjZGE2ZjZlOTE1NTU3MzE1NzYxOGE4NGI1YTAwNTZi%0AODg4ZjVlMDdlMmNjODlmNzUyNzVmMGFmZDAzMWY4MDg3MjRjNjc0ZGE0MmRjNjYzNTM1YjM2MDFi%0ANDA4ZjllYWI4YjgxNDI4Y2E4NWM1NjMxMzA2ZA%3D%3D%0A";		
		logger.info("---银行返回前台通知签名数据---"+sign); 
		try {
			orig = java.net.URLDecoder.decode(orig, encoding);
			sign = java.net.URLDecoder.decode(sign, encoding);
			logger.info("---URL解码后的前台通知原始数据---"+orig); 
			logger.info("---URL解码后的前台通知签名数据---"+sign);
			
			orig = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(orig,encoding);
			sign = com.sdb.payclient.core.PayclientInterfaceUtil.Base64Decode(sign,encoding);
			logger.info("---URL解码Base64Decode解码后的前台通知原始数据---"+orig); 
			logger.info("---URL解码Base64Decode解码后的前台通知签名数据---"+sign);
			
			boolean result = util.verifyData(sign,orig);
			logger.info("---前台通知验签结果---"+result);
			if(result){
				output = util.parseOrigData(orig);
				logger.info("---订单详细信息---"+output);
				logger.info("---订单状态---"+output.getDataValue("status"));
				logger.info("---支付完成时间---"+output.getDataValue("date"));
				logger.info("---手续费金额---"+output.getDataValue("charge"));
				logger.info("---商户号---"+output.getDataValue("masterId"));
				logger.info("---订单号---"+output.getDataValue("orderId"));
				logger.info("---币种---"+output.getDataValue("currency"));
				logger.info("---订单金额---"+output.getDataValue("amount"));
				logger.info("---下单时间---"+output.getDataValue("paydate"));
				logger.info("---商品描述---"+output.getDataValue("objectName"));
				logger.info("---订单有效期---"+output.getDataValue("validtime"));
				logger.info("---备注---"+output.getDataValue("remark"));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		
		return output;
	}

	
	//生成包含原始订单信息的KeyedCollection
//	private static KeyedCollection getInputOrig(){
//		int count= 2;  //商品数量
//		double price= 2.00;  //商品单价
//		
//		Calendar calendar = Calendar.getInstance();
//		Date date = calendar.getTime();
//		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmss" );
//		String timestamp = formatter.format( date );  //时间
//		String datetamp = timestamp.substring(0, 8);  //日期	
//		
//		KeyedCollection inputOrig = new KeyedCollection("inputOrig");
//		
//		inputOrig.put("masterId","2000000009");  //商户号，注意生产环境上要替换成商户自己的生产商户号
//		inputOrig.put("orderId","2000000009"+datetamp+getOrderId());  //订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
//		inputOrig.put("currency","RMB");  //币种，目前只支持RMB
//		inputOrig.put("amount",count*price);  //订单金额，12整数，2小数
//		inputOrig.put("paydate",timestamp);  //下单时间，YYYYMMDDHHMMSS	
//		inputOrig.put("objectName","EPAYpaygate");  //订单款项描述（商户自定）
//		inputOrig.put("validtime","0");  //订单有效期(秒)，0不生效	
//		inputOrig.put("remark","2000000009");  //备注字段（商户自定）
//		System.out.println("---原始订单信息---"+inputOrig);
//		
//		return inputOrig;
//	}
	
	//生成8位随机数
//	private static String getOrderId(){
//		String orderId ;
//		java.util.Random r=new java.util.Random();
//		while(true){
//			int i=r.nextInt(99999999);
//			if(i<0)i=-i;
//			orderId = String.valueOf(i);
//			System.out.println("---生成随机数---"+orderId);
//			if(orderId.length()<8){
//				System.out.println("---位数不够8位---"+orderId);
//				continue;
//			}
//			if(orderId.length()>=8){
//				orderId = orderId.substring(0,8);
//				System.out.println("---生成8位流水---"+orderId);
//				break;
//			}
//		  }
//		return orderId;
//	}
}
