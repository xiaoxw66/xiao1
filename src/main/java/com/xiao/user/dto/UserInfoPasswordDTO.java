package com.xiao.user.dto;

import lombok.Data;

@Data
public class UserInfoPasswordDTO extends UserInfoDTO {

    private static final long serialVersionUID = -3491120747517985868L;
    private String password;
}
