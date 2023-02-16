package com.feng.serviceRegistration;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: 冯金河
 * @Date: 2022/10/25 13:35
 * @Description:
 * 依赖服务地址写在yml配置文件的做法，导致每次修改Yml都要重启服务
 * 注册中心当然能解决这个问题
 * 暂时我想到一个折中的办法，就是把数据地址写在数据库
 * 涉及和其他服务交互的时候，从数据库读地址
 * 然后想修改的时候，可以从前端界面，进入ROOT面板改
 */

@Entity(name = "SERVICE_ADDRESS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAddress {

    /**
     * 数据库表主键ID
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer serviceAddressId;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 服务IP地址
     */
    private String serviceIp;

    /**
     * 服务端口
     */
    private String servicePort;

    /**
     * 完整地址，IP+端口
     */
    private String wholeAddress;


}
