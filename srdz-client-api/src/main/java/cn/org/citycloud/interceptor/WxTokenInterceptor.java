package cn.org.citycloud.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.org.citycloud.bean.ErrorResponse;
import cn.org.citycloud.bean.UserToken;
import cn.org.citycloud.bean.WeixinToken;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.core.WeixinBaseController;
import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;

/**
 * 微信Token拦截器
 * @author lanbo
 *
 */
public class WxTokenInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	MemcachedClient memcachedClient;

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("WxTokenInterceptor preHandle------------" + request.getRequestURI());
		// OPTIONS 禁止
		if("OPTIONS".equals(request.getMethod())) {
			response.setStatus(200);
			return false;
		}
		
		// 在这里验证Token
		String token = request.getHeader("token");
		if (token == null || "".equals(token.trim())) {
			// 获取OutputStream输出流
			response.setStatus(403);
			responseOutWithJson(response, new ErrorResponse(
					ErrorCodes.TOKEN_ERROR, "Token Not Valid"));
			return false;
		}

		Object tokenFromCached = memcachedClient.get(token);
		
		// 测试代码 start TODO
		if (token.equals("1111122222")) {
			UserToken newToken = new UserToken();
			newToken.setUserId(2);
			newToken.setExpiresIn(7200);
			newToken.setCreateTs(System.currentTimeMillis());
			newToken.setToken("1111122222");
			newToken.setAccessToken("accesstoken1111122222");
			memcachedClient.set("1111122222", 7200, newToken);
			
			tokenFromCached = memcachedClient.get(token);
		}
		// 测试代码 end TODO

		if (tokenFromCached == null) {
			// 获取OutputStream输出流
			response.setStatus(403);
			responseOutWithJson(response, new ErrorResponse(
					ErrorCodes.TOKEN_ERROR, "Token Not Exists"));
			return false;
		}

		WeixinToken tk = (WeixinToken) tokenFromCached;

		long expiresIn = tk.getExpiresIn();
		long createTs = tk.getCreateTs();

		long now = System.currentTimeMillis();

		if (now - createTs > expiresIn * 1000) {
			// 获取OutputStream输出流
			response.setStatus(403);
			responseOutWithJson(response, new ErrorResponse(
					ErrorCodes.TOKEN_ERROR, "Token Expired"));

			return false;
		}

		if (HandlerMethod.class.equals(handler.getClass())) {
			// 获取controller，判断是不是Token控制器
			HandlerMethod method = (HandlerMethod) handler;
			Object controller = method.getBean();

			// 判断是否为控制器实现类
			if (controller instanceof WeixinBaseController) {
				// 验证Token中信息并注入到Controller中
				WeixinBaseController apiController = (WeixinBaseController) controller;
				apiController.setMemberId(tk.getMemberId());
				apiController.setStoreId(tk.getStoreId());
				apiController.setOpenId(tk.getOpenId());
				apiController.setAccessToken(tk.getAccessToken());
				apiController.setToken(tk.getToken());
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * 以JSON格式输出
	 * 
	 * @param response
	 */
	protected void responseOutWithJson(HttpServletResponse response,
			Object responseObject) {
		// 将实体对象转换为JSON Object转换
		JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
