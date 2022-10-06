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
 * Time: 14:08
 * Description:
 *
 * @author feng
 */
@Entity(name = "PENDING_REVIEW_PACKAGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingReviewPackage {


    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer pendingReviewPackageId;

    @TableField(value = "PENDING_REVIEW_PACKAGE_NAME")
    private String pendingReviewPackageName;

    @TableField(value = "PENDING_REVIEW_PACKAGE_Type")
    private String pendingReviewPackageType;

    @TableField(value = "PENDING_REVIEW_PACKAGE_AUTHOR")
    private String pendingReviewPackageAuthor;


    @TableField(value = "PENDING_REVIEW_PACKAGE_TIME",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pendingReviewPackageTime;


    /**
     * 待审核、待发布、已拒绝
     */
    @TableField(value = "PENDING_REVIEW_PACKAGE_STATUS")
    private String pendingReviewPackageStatus;

    /**
     * 关联的实际PackageId
     */
    @TableField(value = "CONNECTED_PACKAGE_ID")
    private String connectedPackageId;





}
