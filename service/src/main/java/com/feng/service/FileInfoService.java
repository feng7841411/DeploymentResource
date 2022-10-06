package com.feng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.desertedEntity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/14
 * Time: 14:47
 * Description:
 *
 * @author feng
 */

public interface FileInfoService extends IService<FileInfo> {

    /**
     * 传入一个文件，获取一些基本信息，然后把FileInfo返回去
     * @param multipartFile
     * @return
     */
    public FileInfo storeAndGetFileInfo(MultipartFile multipartFile);


    /**
     * Controller层把关联的JSON记录ID补充以后，提供一个写入数据库方法
     * @param fileInfo
     * @return
     */
    public Integer insertFileInfo(FileInfo fileInfo);


    /**
     * 前端需要渲染的时候，给一个简单的查询
     *
     * 基本信息和软件信息分开，前端需要联查的时候，做一个内部类传回去？
     *
     * @param fileInfoId
     * @return
     */
    public FileInfo getFileInfo(Integer fileInfoId);

    /**
     * 通过Md5值查询文件
     * @param md5
     * @return
     */
    public FileInfo getFileByMd5(String md5);

    /**
     * 软件包文件的4种状态切换方法
     */

    /**
     * 待审核
     * @param fileInfoId
     * @return
     */
    public Boolean setFilePendingReview(Integer fileInfoId);

    /**
     * 审核拒绝
     * @param fileInfoId
     * @return
     */
    public Boolean setFileBeRejected(Integer fileInfoId);

    /**
     * 审核通过
     * @param fileInfoId
     * @return
     */
    public Boolean setFileBePublished(Integer fileInfoId);


    /**
     * 下架
     * @param fileInfoId
     * @return
     */
    public Boolean setFileBeRemoved(Integer fileInfoId);

}
