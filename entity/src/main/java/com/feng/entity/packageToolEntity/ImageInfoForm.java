package com.feng.entity.packageToolEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/8/4
 * Time: 16:17
 * Description:
 * <p>
 * 这里是涉及封装工具第二部分的表单，属性并不是前端传过来的
 * 接收到文件以后，返回封装类的时候，把这个带回去，用于前端展示，让用户看到刚上传的文件的名字
 *
 * @author feng
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfoForm {


    private String[] imageFileUidList;
    private String[] imageFileNameList;

    private String isImageFileExist;
    private String isYamlFileExist;

    private String yamlFileUid;
    private String yamlFileName;


}
