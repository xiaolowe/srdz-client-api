package cn.org.citycloud.utils;


import cn.org.citycloud.constants.Constants;
import cn.org.citycloud.entity.Message;

import java.util.Date;
import java.util.Map;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/8/31 16:39
 */
public class MessageFactory {
    /**
     * 获取message实体
     *
     * @param trigger 触发条件
     * @param msgTpl 消息模板名称
     * @param data 消息模板的数据
     * @param type 接收方平台类型
     * @param receiverId 接收方id
     * @param receiver 接收方姓名
     * @return
     */
    public static Message getMessage(String trigger, String msgTpl, Map<String, String> data, int type, int receiverId, String receiver) {
        Message message = new Message();
        message.setSenderId(1);
        message.setSender("系统");
        message.setTriggerEvent(trigger);
        message.setMessageContent(MessageUtils.getMessageTpl(msgTpl, data));
        Date now = new Date();
        message.setSendTime(now);
        message.setCreateDate(now);
        message.setUpdateDate(now);
        message.setSenderPlatform(Constants.MSG_ADMIN);
        message.setPlatform(type); 	//1  管理后台   2 供应商后台   3   服务中心    4   用户
        message.setReceiverId(receiverId);
        message.setReceiver(receiver);
        return message;
    }
}
