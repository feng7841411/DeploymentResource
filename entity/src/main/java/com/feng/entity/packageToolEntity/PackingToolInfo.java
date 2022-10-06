package com.feng.entity.packageToolEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author feng
 * @date 2022/10/4
 * @time 23:19
 * @apiNote
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackingToolInfo {
    BasicInfoForm basicInfoForm;
    ImageInfoForm imageInfoForm;
    DeveloperInfoForm developerInfoForm;
}
