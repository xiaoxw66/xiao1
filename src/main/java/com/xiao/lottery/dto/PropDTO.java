package com.xiao.lottery.dto;

import com.xiao.common.baseDto.EntityDTO;
import lombok.Data;

/**
 * @Description 道具表实体类
 * @Author yexiaomu
 * @Date 2018/11/30 0:33
 **/
@Data
public class PropDTO extends EntityDTO {
    private int itemMallId;
    private String propName;
    private String propType;
    private String propImageUrl;
    private String propDescription;
    private int order;
    private int probability;
}
