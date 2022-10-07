package com.feng.entity.LogInfo;

/**
 * Created with IntelliJ IDEA.
 * Author: 风
 * Date: 2022/10/7
 * Time: 14:25
 * Description:
 *
 * 与LogInfo配合使用，往数据库插入日志记录，Action行为应该是从这个枚举类中选
 * @author feng
 */
public interface ActionTypes {

    String upLoadNewResource = "上传新资源";

    String rejectNewResource = "拒绝发布";

    String checkNewResource = "安全扫描";

    String publishNewResource = "发布新资源";

    String removeNewResource = "下架新资源";


}
