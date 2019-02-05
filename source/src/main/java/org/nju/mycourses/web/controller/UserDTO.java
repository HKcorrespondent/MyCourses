package org.nju.mycourses.web.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.nju.mycourses.data.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class UserDTO {
    @JsonProperty(value = "username")
    @NotNull(message = "请填写用户名！")
    @Email(message = "邮箱格式不正确！")
    private String username;
    @JsonProperty(value = "password")
    @Pattern(regexp = "[0-9a-zA-Z_]{8,16}", message = "密码只能包含大小写字母、数字和下划线，且必须为8-16位！")
    @NotNull(message = "请填写密码！")
    private String password;
}
