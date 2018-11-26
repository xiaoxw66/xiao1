package com.xiao.user.dto;

import com.xiao.common.dto.EntityDTO;
import lombok.Data;

@Data
public class UserInfoDTO extends EntityDTO {

    private static final long serialVersionUID = 120490905062695565L;

    private String xxwUserInfoId;

    private String userId;

    private String userAccount;

    private String userName;

    private int age;


}
