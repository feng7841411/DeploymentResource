package com.feng.entity.packageToolEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/10/7
 * Time: 15:20
 * Description:
 *
 * 因为从封装工具读的JSON文件是没有在数据库里的
 * 一个复合类，里面分了基础信息、镜像信息、开发者信息
 * 这个类是用来往数据库的里放的，是一个整合的摘要
 *
 * 能贴的，我还是基本都贴进来了，然后前端那个可以select的时候控制一下
 * 比如版本号，数据库其实都记录的，然后返回给前端展示的时候，给一个拼接的
 *
 * @author feng
 */

@Entity(name = "SERVICE_PACKAGE_DETAIL_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicePackageDetailInfo {

    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer servicePackageDetailInfoId;


    /**
     * 打包工具的基础信息部分
     */
    /**
     * 服务中文名，前端传入，必须是中文
     */
    private String softwareCnName;

    /**
     * 2023年1月12日 英文名，实际上是打包中的serviceName
     */
    private String softwareEnName;

    /**
     * 服务组名，前端传入，这个值目前不是必填项
     */
    private String softwareGroupName;

    /**
     * 服务运行环境，前端传过来，目前有3个可能值，x86、arm、others
     */
    private String softwareEnvironment;

    /**
     * 服务唯一标识，前端当做4段填，后端这个softwareId把它们拼接起来
     */
    private String softwareUniqueName;
    private String domainName;
    private String softwareName;
    private String serviceName;
    private String languageName;

    /**
     * 服务版本号，前端填写3段式，后端softwareVersion进行拼接
     */
    private String softwareVersion;
    private String majorVersionNumber;
    private String minorVersionNumber;
    private String revisionNumber;


    /**
     * 镜像信息这块，暂时不放
     * 2022年10月7日15:26:29
     * 序列化？
     * JSONString
     */


    /**
     * 打包工具的开发者信息部分
     */
    private String developerName;

    private String developerGroup;

    private String developerPhone;


    /**
     * 以下是相比从打包的JSON里新增的
     */
    @TableField(value = "CONNECTED_PACKAGE_UID")
    private String connectedPackageUid;


    /**
     * 包真名
     */
    private String connectedPackageOriginalFileName;

    /**
     * 包大小
     */
    private String connectedPackageSize;


    /**
     * 是否确认，因为这个详情类，在确认之前就会写入数据库；
     * pendingReview、publish、removed是确认后才会写，
     * 所有这里留一个，之后用来清除没有被确认，且没有被取消的
     * 这个字段就是两种状态；true、false
     */
    private String isConfirm;


    /**
     * 打包增加cpu和memory
     *
     */

    private String cpuRequests;
    private String memoryRequests;


}
