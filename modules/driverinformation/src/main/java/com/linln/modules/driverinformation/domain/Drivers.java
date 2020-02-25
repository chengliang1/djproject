package com.linln.modules.driverinformation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 程亮
 * @date 2020/01/16
 */
@Data
@Entity
@Table(name="driver_drivers")
public class Drivers implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    // 用户名
    private String drivername;
    //真实姓名
    private String name;
    // 身份证号
    private String IdCard;
    // 身份证照片
    private String IdCard_image;
    //性别 1男 2 女
    private Integer sex;
    // 车品牌
    private String car_brand;
    //车牌照
    private String licence;
    // 驾驶证编号
    private String licence_num;
    // 驾驶证图片
    private String licence_image;
    // 联系电话
    private Long phone;
    // 车颜色
    private String car_color;
    // 司机星级
    private Integer driver_star;
    // 订单次数
    private Integer order_num;
    //账户余额
    private Float money;
    //司机状态 1.工作中，2.等待中，3暂停中
    @JsonIgnore
    private Integer work;
    // 司机驾龄
    @JsonIgnore
    private Integer driver_time;
    // 创建时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    // 最后修改时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date update_time;
    // 简介
    private String remarks;

    //系统需要
    @CreatedDate
    private Date createDate;

}