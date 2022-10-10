package com.feng.entity.myResources;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author feng
 * @date 2022/10/10
 * @time 16:19
 * @apiNote
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyResources {


    private String packageName;

    private Date packageTime;

    private String packageStatus;

    private String packageType;

    /**
     * 在磁盘上zip的名字
     */
    private String connectedPackageUid;


    /**
     * 关联的详情表ID
     * 这个就是表之间的关联了
     */
    private Integer connectedDetailInfoId;

}
