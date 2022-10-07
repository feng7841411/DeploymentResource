package com.feng.entity.LogInfo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/10/7
 * Time: 14:18
 * Description:
 *
 * @author feng
 */
@Entity(name = "LOG_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInfo {


    /**
     * 日志记录的数据库ID
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer logInfoId;

    /**
     * 日志记录的数据库时间
     */
    @TableField(value = "LOG_INFO_TIME",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logInfoTime;


    /**
     * 行为的操作者
     */
    private String operator;

    /**
     * 行为的类型，上传新资源、安全检查、发布、下架等
     */
    private String Action;


    private String Object;


}
