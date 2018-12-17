package com.xiao.money.service;

import com.xiao.money.dto.MoneyInfoDTO;

public interface MoneyService {

    /**
     * @Description 根据用户id查询用户经济信息
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/9 11:54
     * @Param [userId]
     * @Return com.xiao.money.dto.MoneyInfoDTO
     **/
    MoneyInfoDTO getMoneyInfoByUserId(String userId) throws Exception;

    /**
     * @Description 减少用户金元宝
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/9 11:54
     * @Param []
     * @Return int
     **/
    int subtractGoldIngot(MoneyInfoDTO moneyInfoDTO) throws Exception;

    /**
     * @Description 减少用户银元宝
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/9 11:55
     * @Param []
     * @Return int
     **/
    int subtractSilverIngot(MoneyInfoDTO moneyInfoDTO) throws Exception;

    /**
     * @Description 增加用户金元宝
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/9 11:54
     * @Param []
     * @Return int
     **/
    int addGoldIngot(MoneyInfoDTO moneyInfoDTO) throws Exception;

    /**
     * @Description 增加用户银元宝
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/9 11:55
     * @Param []
     * @Return int
     **/
    int addSilverIngot(MoneyInfoDTO moneyInfoDTO) throws Exception;
}
