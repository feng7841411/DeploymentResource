package com.feng.entity.desertedEntity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author feng
 * @date 2022/9/10
 * @time 17:01
 * @apiNote
 */

@Entity(name = "FILE_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer fileId;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private String fileUrl;

    private String fileMd5;

    private Boolean isDelete;

    private Boolean enable;

    private String fileUid;


    /**
     * 待审核；
     * 拒绝发布
     * 已发布
     * 已下架
     */
    private String fileStatus;

    /**
     * 压缩包的服务信息需要一张单独的表，
     */
    private Integer packageId;

}
