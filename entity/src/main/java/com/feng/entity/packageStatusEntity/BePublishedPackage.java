package com.feng.entity.packageStatusEntity;

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
 * Date: 2022/9/20
 * Time: 14:15
 * Description:
 *
 * @author feng
 */
@Entity(name = "BE_PUBLISHED_PACKAGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BePublishedPackage {

    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer bePublishedPackageId;


    @TableField(value = "BE_PUBLISHED_PACKAGE_NAME")
    private String bePublishedPackageName;

    @TableField(value = "BE_PUBLISHED_PACKAGE_Type")
    private String bePublishedPackageType;

    @TableField(value = "BE_PUBLISHED_PACKAGE_AUTHOR")
    private String bePublishedPackageAuthor;



    @TableField(value = "BE_PUBLISHED_PACKAGE_TIME",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bePublishedPackageTime;

    @TableField(value = "BE_PUBLISHED_PACKAGE_STATUS")
    private String bePublishedPackageStatus;

    /**
     * 在磁盘上zip的名字
     */
    @TableField(value = "CONNECTED_PACKAGE_UID")
    private String connectedPackageUid;


    /**
     * 关联的详情表ID
     * 这个就是表之间的关联了
     */
    @TableField(value = "CONNECTED_DETAIL_INFO_ID")
    private Integer connectedDetailInfoId;


    private String packageCnName;


    /**
     * 2022年10月19日 18点44分
     * 容器服务的CPU和内存需求在详情了，但是为了部署那边能拿到这两个值，并发到边缘上，这里也加字段吧，但是感觉这样很不优雅
     */

    private String cpuRequests;
    private String memoryRequests;



}
