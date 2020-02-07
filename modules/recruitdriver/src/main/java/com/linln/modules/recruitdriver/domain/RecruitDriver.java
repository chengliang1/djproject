package com.linln.modules.recruitdriver.domain;

import com.linln.common.utils.StatusUtil;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
 * @date 2020/02/07
 */
@Data
@Entity
@Table(name="recruit_recruitdriver")
public class RecruitDriver implements Serializable {
    // 主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    // 司机姓名
    private String drivername;
    // 身份证号
    private String IdCard;
    // 身份证照片
    private String IdCard_image;
    // 驾驶证
    private String licence_num;
    // 驾驶证图片
    private String licence_image;
    // 联系电话
    private Integer phone;
    // 性别
    private Integer sex;
    // 驾龄
    private Integer drive_time;
    // 是否从事过代驾行业
    private Integer work;
    // 工作时段
    private Integer work_time;
    // 准驾类型
    private String driver_type;
    // 申请状态
    private Integer recruit_status;
    // 申请时间
    @CreatedDate
    private Date createDate;
}