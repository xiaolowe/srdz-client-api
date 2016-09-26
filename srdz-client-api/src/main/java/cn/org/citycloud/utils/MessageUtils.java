package cn.org.citycloud.utils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/8/31 15:40
 */
public class MessageUtils {
    /**
     * 获取相应的短信模板
     *
     * @param busCode 业务编码
     * @param data 业务数据
     * @return 替换相应的业务数据后的结果
     */
    public static String getMessageTpl(@NotNull String busCode, @NotNull Map<String, String> data) {
        Properties properties = new Properties();
        InputStream in = SmsUtil.class.getClassLoader().getResourceAsStream("messages.properties");
        String result = "";
        try {
            properties.load(in);
            result = replacePlaceholder(properties.get(busCode).toString(), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更换占位符${}
     *
     * @param source 源字符串
     * @param data 业务数据
     * @return 更换占位符后的数据
     */
    public static String replacePlaceholder(@NotNull String source, @NotNull Map<String, String> data) {
        StringBuffer result = new StringBuffer();
        Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z]+\\}");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            String matchString = matcher.group();
            matchString = matchString.replace("$", "");
            matchString = matchString.replace("{", "");
            matchString = matchString.replace("}", "");
            String value = data.get(matchString).toString();
            matcher.appendReplacement(result, value);
        }
        return matcher.appendTail(result).toString();
    }

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<>();
        data.put("orderId", "O20160831001");
        data.put("reason", "就是不给退，你咬我啊！！！");
        data.put("userPhone", "15056998121");
        data.put("userName", "demon");
        System.out.println(getMessageTpl("refund_rejected_fin", data));
    }
}
