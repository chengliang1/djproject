package com.linln.modules.orderinformation.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author 程亮
 * @date 2020/01/30
 */
@Data
@Entity
@Table(name="order_orders")
public class Orders implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    //订单编号
    private Long orderid;
    // 用户
    private String username;
    //司机姓名
    private String name;
    // 订单日期
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date order_date;
    // 出发地
    private String origin;
    // 目的地
    private String destination;
    // 路程
    private Float distance;
    //行驶时间 与小程序传过来的unit进行拼接
    private String duration;
    // 订单类型
    private String order_type;
    // 订单费用
    private Float order_price;
    // 星级评价
    private Integer evaluation_star;
    // 文字评价
    private String evaluation_text;
    // 创建日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDate;
    // 备注
    @Lob
    @Column(columnDefinition="TEXT")
    private String remark;
}