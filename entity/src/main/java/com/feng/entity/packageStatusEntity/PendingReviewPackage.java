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


    /**
     * 2022年10月7日 21点58分 上传者
     *
     * 其实前面有作者Author这个字段了
     */
    private String uploader;


    // 2022年10月16日 11点39分 因为3种状态的name,都是上传的zip名字，是那个4段标识.zip，感觉查看效果不是很好
    // 之前的设计是中文名在“查看详情”里才能看到，考虑了一下Deployment里展示也需要中文名比较好，所以这里3种状态都加一下package里包资源的中文服务名
    // 需要改的地方：
    // 1、这个字段一开始进入PendingReview是从JSON里找值
    // 2、包的三种状态切换，这个值要跟着走
    // 3、商店的3张表，也全部加这个字段的展示
    // 4、deployment那边是给了全部的信息，然后deployment挑选的时候，从name改成CnName，这样应该就可以了

    // 这边后面补充的字段，我都命名成了一样的，主要也是感觉看的多眼花了



    private String packageCnName;



}
