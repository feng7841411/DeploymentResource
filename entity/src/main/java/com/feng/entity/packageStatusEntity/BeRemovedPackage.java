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
 * Time: 14:32
 * Description:
 *
 * @author feng
 */

@Entity(name = "BE_REMOVED_PACKAGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeRemovedPackage {

    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer beRemovedPackageId;


    @TableField(value = "BE_REMOVED_PACKAGE_NAME")
    private String beRemovedPackageName;
    @TableField(value = "BE_REMOVED_PACKAGE_AUTHOR")
    private String beRemovedPackageAuthor;

    @TableField(value = "BE_REMOVED_PACKAGE_TIME",fill = FieldFill.INSERT)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beRemovedPackageTime;

    @TableField(value = "BE_REMOVED_PACKAGE_STATUS")
    private String beRemovedPackageStatus;

    @TableField(value = "BE_REMOVED_PACKAGE_Type")
    private String beRemovedPackageType;

    /**
     * 关联的实际PackageUId
     */
    @TableField(value = "CONNECTED_PACKAGE_UID")
    private String connectedPackageUid;



}
