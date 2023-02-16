package com.feng.serviceRegistration;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: 冯金河
 * @Date: 2022/11/14 20:26
 * @Description:
 */
@Entity(name = "CONFIGURATION_PARAMETER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationParameter {

    /**
     * 数据库表主键ID
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer configurationParameterId;

    /**
     * 配置参数的名称
     */
    private String parameterName;

    /**
     * 字符串值
     */
    private String stringValue;

    /**
     * 数值型
     */
    private Integer intValue;



}
