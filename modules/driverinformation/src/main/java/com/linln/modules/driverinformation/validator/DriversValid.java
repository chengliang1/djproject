package com.linln.modules.driverinformation.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

/**
 * @author 程亮
 * @date 2020/01/16
 */
@Data
public class DriversValid implements Serializable {
    @Pattern(regexp = "(^(\\d{14}|\\d{17})(\\d|[xX])$)?", message = "身份证号码错误")
    private String IdCard;
    @Digits(integer = 12, fraction = 2, message = "司机驾龄不是数字")
    private Integer driver_time;
}