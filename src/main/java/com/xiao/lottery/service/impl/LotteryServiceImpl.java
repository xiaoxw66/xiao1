package com.xiao.lottery.service.impl;

import com.xiao.common.dto.CacheHelper;
import com.xiao.common.dto.Constants;
import com.xiao.lottery.dto.PropDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description // TODO
 * @Author yexiaomu
 * @Date 2018/11/30 0:24
 **/
@Slf4j
@Service
public class LotteryServiceImpl {

    private Random random = new Random();

    public PropDTO lotteryDraw1() {
        // 获取账号当前元宝数量
        // 抽奖
        PropDTO propDTO = this.lotteryDraw();

        // 事务控制
        return propDTO;

    }

    public PropDTO lotteryDraw() {
        // 随机抽奖号码,就是奖池list的索引
        int lotteryIndexNum = random.nextInt(Constants.LOTTERY_POOL_MAX);

        // 获取抽奖号码对应的概率
        String lotteryPoolProb = CacheHelper.LOTTERY_POOL_LIST.get(lotteryIndexNum);

        // 根据概率获取对应的道具列表
        List<PropDTO> propDTOList = CacheHelper.PROP_GROUP_MAP.get(lotteryPoolProb);

        // 当前概率的道具列表长度
        int propGroupSize = propDTOList.size();

        // 根据道具列表长度随机一个数据
        int propRandomNum = random.nextInt(propGroupSize);

        // 实际抽到的道具
        PropDTO propDTO = propDTOList.get(propRandomNum);

        return propDTO;
    }
}
