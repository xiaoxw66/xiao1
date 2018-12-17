package com.xiao.lottery.dto;

import com.sun.xml.internal.stream.events.EntityReferenceEvent;
import com.xiao.common.dto.EntityDTO;
import lombok.Data;

/**
 * @Description // TODO
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
