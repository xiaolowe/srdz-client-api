package cn.org.citycloud.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/24 9:50
 */
public class AlipayBean {
    @ApiModelProperty(value = "订单号/支付单号(建议是英文字母和数字,不能含有特殊字符)", required = true)
    @NotBlank(message = "订单号不能为空")
    private String outTradeNo;

    @ApiModelProperty(value = "订单名称/产品名称(建议中文，英文，数字，不能含有特殊字符)", required = true)
    @NotBlank(message = "订单名称/产品名称不能为空")
    private String subject;

    @ApiModelProperty(value = "付款金额(格式如：1.00,请精确到分)", required = true)
    @NotNull
    private BigDecimal totalFee;

    @ApiModelProperty(value = "订单/产品描述(body)，选填(建议中文，英文，数字，不能含有特殊字符)", required = true)
    private String body;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
