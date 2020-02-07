package com.linln.admin.recruitdriver.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author 程亮
 * @date 2020/02/07
 */
@Data
public class RecruitDriverValid implements Serializable {
    @NotEmpty(message = "司机姓名不能为空")
    private String drivername;
    @Pattern(regexp = "(^(\\d{14}|\\d{17})(\\d|[xX])$)?", message = "身份证号码错误")
    private String IdCard;
    @Pattern(regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号码格式不正确")
    private Integer phone;
    @Digits(integer = 12, fraction = 2, message = "性别不是数字")
    private Integer sex;
}