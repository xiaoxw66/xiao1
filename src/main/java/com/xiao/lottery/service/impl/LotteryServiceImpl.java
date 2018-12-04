package com.xiao.lottery.service.impl;

import com.xiao.lottery.dto.PropDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description // TODO
 * @Author yexiaomu
 * @Date 2018/11/30 0:24
 **/
@Service
public class LotteryServiceImpl {

    private static int[] JACKPOT = new int[1000];

    public void test() {

        // 从数据库中获取商品
        List<PropDTO> propDTOList = new ArrayList<>();

        // 对商品按照中奖概率分组


    }

}
