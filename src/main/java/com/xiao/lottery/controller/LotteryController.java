package com.xiao.lottery.controller;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.xiao.common.dto.CacheHelper;
import com.xiao.common.dto.ResponseData;
import com.xiao.common.util.StringUtil;
import com.xiao.lottery.dto.PropDTO;
import com.xiao.lottery.service.impl.LotteryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/7 19:58
 */
@RestController
@Slf4j
@EnableAutoConfiguration
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private LotteryServiceImpl lotteryService;

    @RequestMapping(value = "/draw", method = RequestMethod.GET)
    public ResponseData<PropDTO> lotteryDraw() {
        List<String> lotteryRes = new ArrayList<>(1000);
        for (int x = 0; x < 1000; x++) {
            PropDTO propDTO = lotteryService.lotteryDraw1();
            log.info("第 {} 次抽到的道具为: {} ", x, propDTO.getPropName());
            lotteryRes.add(StringUtil.transformNullStr(propDTO.getProbability()));
        }
        this.lotteryPoolData(lotteryRes);
        return null;
    }

    private void lotteryPoolData(List<String> lotteryRes) {
        Map<String, Integer> sumMap = new HashMap<>();
        for (String tempPropDto : lotteryRes) {
            // 按概率进行分组
            String probability = StringUtil.getString(tempPropDto, "nullStr");
            if (!sumMap.containsKey(probability)) {
                Integer count = 1;
                sumMap.put(probability, count);
            } else {
                Integer count = sumMap.get(probability);
                sumMap.put(tempPropDto, ++count);
            }
        }
        log.info("lotteryRes 汇总结果为:{}", JSON.toJSONString(sumMap));
    }

}
