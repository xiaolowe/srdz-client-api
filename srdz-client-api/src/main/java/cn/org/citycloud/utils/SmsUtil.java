package cn.org.citycloud.utils;

import com.smsweb.www.servlet.anna.AnnaHttpSendResponseObject;
import com.smsweb.www.servlet.anna.SDKProxy;

/**
 * 短信服务平台相关工具类
 * @author lanbo
 *
 */
public class SmsUtil {
	
	/**
	 * 发送短信验证码
	 * @param phone
	 * @param code
	 * @return
	 */
	public static  String sendSms(String phone,String code){
		
		// 维纳多公司
		// http://www.weinaduo.net/
		SDKProxy sdk2 = 	new SDKProxy();
		sdk2.init("http://yl.mobsms.net/send/sendAnna.aspx?", "shanrong", "shanrong1");
		AnnaHttpSendResponseObject oneresp = sdk2.send(phone, code);
//		System.out.println(oneresp.getErrid()+","+oneresp.getErr());
		
		if(oneresp != null)
		return oneresp.getErrid();
		else 
			return null;
	}

	
	/** 
	  * 创建指定数量的随机字符串 
	  * @param numberFlag 是否是数字 
	  * @param length 
	  * @return 
	  */  
	 public static String getRandomStr(boolean numberFlag, int length){  
	  String retStr = "";  
	  String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";  
	  int len = strTable.length();  
	  boolean bDone = true;  
	  do {  
	   retStr = "";  
	   int count = 0;  
	   for (int i = 0; i < length; i++) {  
	    double dblR = Math.random() * len;  
	    int intR = (int) Math.floor(dblR);  
	    char c = strTable.charAt(intR);  
	    if (('0' <= c) && (c <= '9')) {  
	     count++;  
	    }  
	    retStr += strTable.charAt(intR);  
	   }  
	   if (count >= 2) {  
	    bDone = false;  
	   }  
	  } while (bDone);  
	  
	  return retStr;  
	 }  
	
}
