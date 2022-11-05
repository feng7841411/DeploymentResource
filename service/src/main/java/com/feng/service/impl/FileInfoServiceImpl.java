package com.feng.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feng.dao.FileInfoMapper;
import com.feng.entity.desertedEntity.FileInfo;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.service.FileInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/9/14
 * Time: 14:48
 * Description:
 *
 * @author feng
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;


    @Value("${server.port}")
    private String serverPort;


    private final FileInfoMapper fileInfoMapper;

    private static final Logger logger = LogManager.getLogger(FileInfoServiceImpl.class);

    public FileInfoServiceImpl(FileInfoMapper fileInfoMapper) {
        this.fileInfoMapper = fileInfoMapper;
    }

    @Override
    public FileInfo storeAndGetFileInfo(MultipartFile multipartFile) {
        // 文件名，就是逗号前面的那个
        String originalFileName = multipartFile.getOriginalFilename();
        // 文件数据类型，逗号后面那个
        //String contentType = multipartFile.getContentType();
        String contentType = FileUtil.extName(originalFileName);

        long size = multipartFile.getSize();

        // 定义文件唯一标识码
        String fileUid = IdUtil.fastSimpleUUID() + StrUtil.DOT + contentType;
        File uploadFile = new File(fileUploadPath + '/' + fileUid);
        // 需要转绝对路径
        File dest = uploadFile.getAbsoluteFile();
        // 判断文件存储路径是否存在，若无则新建路径
        File parentFile = uploadFile.getParentFile();
        logger.info("originalFileName: " + originalFileName);
        logger.info("contentType: " + contentType);
        logger.info("fileUid: " + fileUid);
        logger.info("uploadFile: " + uploadFile);
        logger.info("dest: " + dest);
        logger.info("parentFile: " + parentFile);
        if (!parentFile.exists()) {
            parentFile.mkdir();
            logger.info("当前文件存储路径：" + parentFile + "不存在，已经新建");
        }
        String url = "";
        String md5 = "";
        try {
            md5 = SecureUtil.md5(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("error,文件md5值生成失败。");
        }

        // 从数据库查询是否存在相同的记录
        FileInfo dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getFileUrl();
            // 数据库有记录，但是实际磁盘上没有，也要存！
            File file1 = new File(fileUploadPath + '/' + dbFiles.getFileUid()).getAbsoluteFile();
            if (!file1.exists()) {
                try {
                    multipartFile.transferTo(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        } else {
            //// 上传文件到磁盘
            try {
                // dest 是 xxxx.后缀的 File,从"文件.后缀" 复制到 “文件。后缀”
                multipartFile.transferTo(dest);
                // 数据库若不存在重复文件，则不删除刚才上传的文件
                url = "http://" + serverIp + ":" + serverPort + "/files/" + fileUid;
                logger.info("文件：" + originalFileName + "已经成功接收并存储");
                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileName(originalFileName);
                fileInfo.setFileSize(size/1024);
                fileInfo.setFileType(contentType);
                fileInfo.setFileUrl(url);
                fileInfo.setFileUid(fileUid);
                fileInfo.setFileMd5(md5);
                fileInfoMapper.insert(fileInfo);
                return fileInfo;
            } catch (IOException e) {
                logger.error("error, 数据库没有查询到这个文件，往磁盘存储新文件失败。");
                e.printStackTrace();
            }
            return null;
        }
        //FileInfo fileInfo1 = new FileInfo();

        // 调用set方法

        //return null;

    }

    @Override
    public Integer insertFileInfo(FileInfo fileInfo) {
        return null;
    }

    @Override
    public FileInfo getFileInfo(Integer fileInfoId) {
        return null;
    }

    @Override
    public FileInfo getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_md5", md5);
        List<FileInfo> filesList = fileInfoMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    @Override
    public Boolean setFilePendingReview(Integer fileInfoId) {
        return null;
    }

    @Override
    public Boolean setFileBeRejected(Integer fileInfoId) {
        return null;
    }

    @Override
    public Boolean setFileBePublished(Integer fileInfoId) {
        return null;
    }

    @Override
    public Boolean setFileBeRemoved(Integer fileInfoId) {
        return null;
    }


    @Override
    public ServiceResult storeFile(MultipartFile[] files) {
        logger.info("");
        logger.info("扫描接收的文件数组信息：");
        logger.info(files);
        logger.info("接收文件数组：files_length = " + files.length);
        for (MultipartFile file: files) {
            logger.info("文件信息：");
            logger.info(file.getOriginalFilename());
            logger.info(file.getSize());
        }
        logger.info("扫描接收的文件数组信息完成：");
        logger.info("");
        // 1、拿到文件对象
        MultipartFile file = files[0];
        // file = "xxfw-xttz-xttz-java.zip"
        String originalFilename = file.getOriginalFilename();
        String contentType = FileUtil.extName(originalFilename);
        // 定义文件唯一标识码
        // fileUid = "xxxxxx.zip"
        String fileUid = IdUtil.fastSimpleUUID() + StrUtil.DOT + contentType;
        File uploadFile = new File(fileUploadPath + '/' + fileUid);
        // 需要转绝对路径
        File dest = uploadFile.getAbsoluteFile();
        // 判断文件存储路径是否存在，若无则新建路径
        File parentFile = uploadFile.getParentFile();
        logger.info("originalFileName: " + originalFilename);
        logger.info("contentType: " + contentType);
        logger.info("fileUid: " + fileUid);
        logger.info("uploadFile: " + uploadFile);
        logger.info("dest: " + dest);
        logger.info("parentFile: " + parentFile);
        if (!parentFile.exists()) {
            parentFile.mkdir();
            logger.info("当前文件存储路径：" + parentFile + "不存在，已经新建");
        }
        try {
            file.transferTo(dest);
            // 写一条数据库记录吧
            logger.info("写入一条文件记录，只记录oriName和Uid");
            FileInfo fileInfo = new FileInfo();
            // 	fastjson-1.2.58.tar
            fileInfo.setFileName(originalFilename);
            // 	a8a0402c03ba420f8d160f99ab14fd80.tar，返回给前端暂存和最后提交的是Uid
            fileInfo.setFileUid(fileUid);
            fileInfoMapper.insert(fileInfo);
            return ServiceResult.success("文件：" + originalFilename + "存储成功",fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResult.error("zip文件往存储位置写入失败",null);
        }



    }

    @Override
    public ServiceResult storePublicDataFile(MultipartFile[] files) {
        logger.info("");
        logger.info("扫描接收的文件数组信息：");
        logger.info(files);
        logger.info("接收文件数组：files_length = " + files.length);
        for (MultipartFile file: files) {
            logger.info("文件信息：");
            logger.info(file.getOriginalFilename());
            logger.info(file.getSize());
        }
        logger.info("扫描接收的文件数组信息完成：");
        logger.info("");
        // 1、拿到文件对象
        MultipartFile file = files[0];
        // file = "xxfw-xttz-xttz-java.zip"
        String originalFilename = file.getOriginalFilename();
        String contentType = FileUtil.extName(originalFilename);
        // 定义文件唯一标识码
        // fileUid = "xxxxxx.zip"
        String fileUid = IdUtil.fastSimpleUUID() + StrUtil.DOT + contentType;
        File uploadFile = new File(fileUploadPath + '/' + fileUid);
        // 需要转绝对路径
        File dest = uploadFile.getAbsoluteFile();
        // 判断文件存储路径是否存在，若无则新建路径
        File parentFile = uploadFile.getParentFile();
        logger.info("originalFileName: " + originalFilename);
        logger.info("contentType: " + contentType);
        logger.info("fileUid: " + fileUid);
        logger.info("uploadFile: " + uploadFile);
        logger.info("dest: " + dest);
        logger.info("parentFile: " + parentFile);
        if (!parentFile.exists()) {
            parentFile.mkdir();
            logger.info("当前文件存储路径：" + parentFile + "不存在，已经新建");
        }
        try {
            file.transferTo(dest);
            // 写一条数据库记录吧
            logger.info("写入一条文件记录，只记录oriName和Uid");
            FileInfo fileInfo = new FileInfo();
            // 	fastjson-1.2.58.tar
            fileInfo.setFileName(originalFilename);
            // 	a8a0402c03ba420f8d160f99ab14fd80.tar，返回给前端暂存和最后提交的是Uid
            fileInfo.setFileUid(fileUid);
            fileInfo.setFileType("publicData");
            fileInfoMapper.insert(fileInfo);
            return ServiceResult.success("文件：" + originalFilename + "存储成功",fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return ServiceResult.error("zip文件往存储位置写入失败",null);
        }


    }

}
