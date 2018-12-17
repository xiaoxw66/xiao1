package com.xiao.common.util;

import com.xiao.common.dto.CacheHelper;
import com.xiao.common.dto.Constants;
import com.xiao.lottery.dto.PropDTO;

import java.util.List;
import java.util.Random;

/**
 * @Description 抽奖
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/17 15:23
 */
public class LotteryUtil {

    private static Random random = new Random();

    /**
     * @Description 抽奖公共方法
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/17 15:24
     * @Param []
     * @Return com.xiao.lottery.dto.PropDTO
     **/
    public static PropDTO lotteryDraw() {
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
