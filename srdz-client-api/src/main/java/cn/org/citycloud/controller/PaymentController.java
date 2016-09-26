package cn.org.citycloud.controller;

import cn.org.citycloud.bean.AlipayBean;
import cn.org.citycloud.bean.PreOrder;
import cn.org.citycloud.constants.ErrorCodes;
import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.entity.Order;
import cn.org.citycloud.entity.PayInfo;
import cn.org.citycloud.exception.BusinessErrorException;
import cn.org.citycloud.repository.OrderDao;
import cn.org.citycloud.service.OrdersService;
import cn.org.citycloud.service.PaymentService;
import cn.org.citycloud.utils.alipay.config.AlipayConfig;
import cn.org.citycloud.utils.alipay.util.AlipayNotify;
import cn.org.citycloud.utils.alipay.util.AlipaySubmit;
import io.swagger.annotations.*;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * PC端支付控制器
 *
 * @author lanbo
 */
@RestController
@RequestMapping(value = "/payments")
@Api(tags = "支付", position = 8, value = "/payments", description = "支付模块", consumes = "application/json")
public class PaymentController extends BaseController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderDao orderDao;

    /**
     * 1)生成预订单信息和订单商品信息
     *
     * @throws BusinessErrorException
     */
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    @ApiOperation(value = "前端调用 返回支付预订单信息", notes = "生成预订单信息和订单商品", response = PayInfo.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "string", paramType = "header")})
    public Object submitOrder(@ApiParam(value = "预订单信息", required = true) @Valid @RequestBody PreOrder preOrder,
                              HttpServletRequest request) throws BusinessErrorException {
        return ordersService.submitOrder(preOrder, getMemberId());
    }

    /**
     * 支付宝支付接口
     *
     * @param alipayBean
     * @return
     */
    @RequestMapping(value = "/alipay", method = RequestMethod.POST)
    @ApiOperation(value = "支付宝支付", notes = "支付宝支付")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = false, dataType = "string", paramType = "header")})
    public Object alipay(@Valid @RequestBody AlipayBean alipayBean) throws BusinessErrorException {
        if (alipayBean.getOutTradeNo().startsWith("O")) {
            Order order = orderDao.findOne(Integer.valueOf(alipayBean.getOutTradeNo().substring(1)));
            // 订单已经在走退款流程
            if (0 != order.getBackOrderStatus()) {
                throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单正在退款，无法支付");
            }
        }

        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", AlipayConfig.payment_type);
        sParaTemp.put("notify_url", AlipayConfig.notify_url);
        sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
        sParaTemp.put("out_trade_no", alipayBean.getOutTradeNo());
        sParaTemp.put("subject", alipayBean.getSubject());
        sParaTemp.put("total_fee", alipayBean.getTotalFee().toString());
        sParaTemp.put("body", alipayBean.getBody());
        //其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        //如sParaTemp.put("参数名","参数值");

        //建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认");
        Map<String, Object> result = new HashMap<>();
        result.put("aliForm", sHtmlText);
        return result;
    }

    @RequestMapping(value="/{payId}",method=RequestMethod.GET)
    @ApiOperation(value="获取支付单详情",notes="获取支付单详情",consumes="application/json",produces="application/json",httpMethod="GET")
    public Object getPayInfo(@ApiParam(name="payId",value="支付单id",required=true) @PathVariable int payId) {
        return paymentService.getPayInfo(payId);
    }

    /**
     *
     * 2)支付订单
     */
    /**
     * 前端调用 返回微信支付预订单信息
     *
     * @throws BusinessErrorException
     * @throws IOException
     * @throws MemcachedException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    @RequestMapping(value = "/wxRetryPayOrders/{orderId}", method = RequestMethod.POST)
    @ApiOperation(value = "再次支付订单(调试)", notes = "调用后订单状态改为已支付")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "string", paramType = "header")})
    @ResponseBody
    public void payOrder(@PathVariable int orderId)
            throws BusinessErrorException, IOException, TimeoutException, InterruptedException {
        paymentService.payOrder(orderId, getMemberId(), getMemberPhone());
    }

    /**
     *
     * 3)支付完成后 回调
     */
    /**
     * 支付结果通知处理(调试之用)
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/testPayNotify/{payId}", method = RequestMethod.POST)
    @ApiOperation(value = "支付结果通知处理(调试)", notes = "调用后订单状态改为已支付")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "string", paramType = "header")})
    public void testHandleWSxpayNotify(@PathVariable int payId) {
        paymentService.testHandleWSxpayNotify(payId, getMemberId());
    }
}
