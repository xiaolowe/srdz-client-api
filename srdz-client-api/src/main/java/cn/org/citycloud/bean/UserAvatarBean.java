package cn.org.citycloud.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/7/11 10:48
 */
public class UserAvatarBean {
    @NotBlank
    @ApiModelProperty(value="头像路径", required=true)
    private String userAvatar;

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
