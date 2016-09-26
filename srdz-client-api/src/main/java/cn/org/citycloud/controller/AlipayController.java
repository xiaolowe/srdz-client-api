package cn.org.citycloud.controller;

import cn.org.citycloud.core.BaseController;
import cn.org.citycloud.service.PaymentService;
import cn.org.citycloud.utils.alipay.config.AlipayConfig;
import cn.org.citycloud.utils.alipay.util.AlipayNotify;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/27 15:04
 */
@Controller
@RequestMapping(value = "/alipay")
public class AlipayController extends BaseController {

    @Autowired
    private PaymentService paymentService;

    /**
     * 支付宝接口回调接口
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/alipayNotify", method = RequestMethod.POST)
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        response.setHeader("content-type", "text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (AlipayNotify.verify(params)) {//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                paymentService.callBackPay(out_trade_no);
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                paymentService.callBackPay(out_trade_no);
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            out.print("success");
//            response.sendRedirect("http://60.166.12.102:9010/?s=/Goods/finishPayment&code=1");
            //////////////////////////////////////////////////////////////////////////////////////////
        } else {//验证失败
//            response.sendRedirect("http://60.166.12.102:9010/?s=/Goods/finishPayment&code=0");
            out.print("fail");
        }
    }

    @RequestMapping(value = "/queryTrade/{outTradeNo}", method = RequestMethod.GET)
    @ApiOperation(value="查询订单交易状态",notes="查询订单交易状态",consumes="application/json",produces="application/json")
    public void queryTrade(@ApiParam(name="outTradeNo",value="订单号或者支付单号",required=true) @PathVariable String outTradeNo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.OPENAPI_URL, AlipayConfig.APP_ID, AlipayConfig.PRIVATE_KEY, "json", "GBK", AlipayConfig.PUBLIC_KEY);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{'out_trade_no':'" + outTradeNo + "'}");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response != null && "TRADE_SUCCESS".equals(response.getTradeStatus())) {
            paymentService.ensureAlipay(outTradeNo);
        }
    }
}
