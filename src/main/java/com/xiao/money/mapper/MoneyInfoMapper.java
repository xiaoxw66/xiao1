package com.xiao.money.mapper;

import com.xiao.money.dto.MoneyInfoDTO;
import org.apache.ibatis.annotations.Param;

public interface MoneyInfoMapper {

    MoneyInfoDTO getMoneyInfoByUserId(@Param("userId") String userId);

    int subtractGoldIngot(MoneyInfoDTO moneyInfoDTO);

    int subtractSilverIngot(MoneyInfoDTO moneyInfoDTO);

    int addGoldIngot(MoneyInfoDTO moneyInfoDTO);

    int addSilverIngot(MoneyInfoDTO moneyInfoDTO);
}
