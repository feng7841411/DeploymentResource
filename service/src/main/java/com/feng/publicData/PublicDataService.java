package com.feng.publicData;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feng.entity.publicData.PublicData;
import com.feng.entity.returnClass.ServiceResult;

/**
 * @author: 冯金河
 * @Date: 2022/10/28 18:52
 * @Description:
 */

public interface PublicDataService extends IService<PublicData> {

    public ServiceResult insertPublicData(PublicData publicData);

    public ServiceResult getAllPublicData();

}
