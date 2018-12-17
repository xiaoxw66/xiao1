package com.xiao.lottery.service.impl;

import com.xiao.common.util.LotteryUtil;
import com.xiao.lottery.dto.PropDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description 抽奖处理
 * @Author yexiaomu
 * @Date 2018/11/30 0:24
 **/
@Slf4j
@Service
public class LotteryServiceImpl {


    public PropDTO lotteryDraw() {
        // 获取账号当前元宝数量
        // 抽奖
        PropDTO propDTO = LotteryUtil.lotteryDraw();

        // 事务控制
        return propDTO;

    }


}
