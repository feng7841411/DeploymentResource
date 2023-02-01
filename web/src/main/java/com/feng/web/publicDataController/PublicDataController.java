package com.feng.web.publicDataController;

import com.feng.entity.desertedEntity.FileInfo;
import com.feng.entity.publicData.PublicData;
import com.feng.entity.returnClass.Result;
import com.feng.entity.returnClass.ServiceResult;
import com.feng.publicData.impl.PublicDataServiceImpl;
import com.feng.service.impl.FileInfoServiceImpl;
import com.feng.web.controller.PackageController;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 冯金河
 * @Date: 2022/10/28 18:08
 * @Description:
 */
@Transactional(rollbackFor = Exception.class)
@RestController
@RequestMapping("/v1/publicData")
public class PublicDataController {


    private static final Logger logger = LogManager.getLogger(PublicDataController.class);

    private final FileInfoServiceImpl fileInfoService;

    public PublicDataController(FileInfoServiceImpl fileInfoService, PublicDataServiceImpl publicDataService) {
        this.fileInfoService = fileInfoService;
        this.publicDataService = publicDataService;
    }

    private final PublicDataServiceImpl publicDataService;

    @PostMapping("/uploadPublicData")
    public Result uploadImage(@RequestParam("files") MultipartFile[] files) {
        // 读取File 基本信息
        ServiceResult serviceResult = fileInfoService.storePublicDataFile(files);
        logger.info(serviceResult.getMsg());
        logger.info(serviceResult.getData());
        FileInfo fileInfo = (FileInfo) serviceResult.getData();
        // 返回结果
        return Result.success("接收镜像文件成功",fileInfo.getFileUid());
    }



    @PostMapping("/postPublicData")
    public Result postPublicData(@RequestBody Map<String, Object> params) {
        HashMap<String, Object> map = (HashMap<String, Object>) params.get("params");
        logger.info(map);
        PublicData publicData = new PublicData();
        publicData.setPublicDataName((String) map.get("publicDataName"));
        publicData.setPublicDataNote((String) map.get("publicDataNote"));
        publicData.setPublicDataType((String) map.get("publicDataType"));
        publicData.setPublicDataVersion((String) map.get("publicDataVersion"));
        publicData.setPublicDataUid((String) map.get("publicDataUid"));
        publicDataService.insertPublicData(publicData);
        return Result.success("已确认","");
    }

    /**
     * 给开设部署查模拟公共数据包
     * @return
     */
    @GetMapping("/getAllPublicData")
    public Result getAllPublicData() {
        logger.info("公共服务包全查询接口被调用");
        ServiceResult allPublicData = publicDataService.getAllPublicData();
        logger.info(allPublicData.getMsg());
        return Result.success("公共数据包全查询",allPublicData.getData());

    }

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @GetMapping("/getPublicData")
    public void download(@RequestParam("dataKey") String dataKey,
                         HttpServletResponse response) throws Exception {
        File file = new File(fileUploadPath +"/" + dataKey).getAbsoluteFile();
        System.out.println(file.toString());
        if (!file.exists()) {
        } else {
            response.reset();
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename=" + dataKey);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        }
    }

}
