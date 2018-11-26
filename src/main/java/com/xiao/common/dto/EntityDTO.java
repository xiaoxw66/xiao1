package com.xiao.common.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EntityDTO extends BasicDto {

    private static final long serialVersionUID = -2222877083775563976L;
    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-6")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss") // 使用fastjson转jsonstring时的时间格式
    private Date createdDate;

    private String updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-6")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss") // 使用fastjson转jsonstring时的时间格式
    private Date updatedDate;

}
