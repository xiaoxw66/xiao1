package com.xiao.money.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiao.common.util.StringUtil;
import com.xiao.money.dto.MoneyInfoDTO;
import com.xiao.money.mapper.MoneyInfoMapper;
import com.xiao.money.service.MoneyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/9 10:46
 */
@Service
@Slf4j
public class MoneyServiceImpl implements MoneyService {

    @Autowired
    private MoneyInfoMapper moneyInfoMapper;

    @Override
    public MoneyInfoDTO getMoneyInfoByUserId(String userId) throws Exception {
        try {
            MoneyInfoDTO moneyInfoDTO = moneyInfoMapper.getMoneyInfoByUserId(userId);
            log.info("获取到用户经济结果userId:{}, moneyInfoDto: {}", userId, JSON.toJSONString(moneyInfoDTO));
            StringUtil.checkNull(moneyInfoDTO, null);
            return moneyInfoDTO;
        } catch (Exception e) {
            log.info("获取用户经济信息异常,userId:{}", userId, e);
            throw new Exception("获取用户经济信息异常");
        }
    }

    @Override
    public int subtractGoldIngot(MoneyInfoDTO moneyInfoDTO) throws Exception {
        return 0;
    }

    @Override
    public int subtractSilverIngot(MoneyInfoDTO moneyInfoDTO) throws Exception {
        return 0;
    }

    @Override
    public int addGoldIngot(MoneyInfoDTO moneyInfoDTO) throws Exception {
        return 0;
    }

    @Override
    public int addSilverIngot(MoneyInfoDTO moneyInfoDTO) throws Exception {
        return 0;
    }
}
