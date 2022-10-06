package com.feng.entity.desertedEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;

/**
 *
 * 上传包以后，解析JSON里的信息，要存到数据库
 * @author feng
 * @date 2022/9/10
 * @time 17:04
 * @apiNote
 */
@Entity(name = "PACKAGE_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageInfo {

    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer packageInfoId;


    /**
     * 2022年9月14日15:13:46
     * 我突然想到这个似乎可以直接存在一起，给FileInfo增加一个字段
     * 但是想想似乎FileInfo + XX其他比较
     * 把File本身的基础属性和其他包的属性分开，降低耦合
     *
     * 打包里JSON文件的内容，因为涉及List等需要序列化，干脆直接转String,只存一条
     */
    private String packageDetailInfo;

}
