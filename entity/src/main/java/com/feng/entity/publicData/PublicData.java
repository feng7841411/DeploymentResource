package com.feng.entity.publicData;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: 冯金河
 * @Date: 2022/10/28 18:48
 * @Description:
 */
@Entity(name = "PUBLIC_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PublicData {

    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Integer publicDataId;


    private String publicDataName;


    private String publicDataNote;


    private String publicDataUid;

    private String publicDataType;

    private String publicDataVersion;


}
