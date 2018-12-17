package com.xiao.common.init;

import com.alibaba.fastjson.JSON;
import com.xiao.common.dto.CacheHelper;
import com.xiao.common.dto.Constants;
import com.xiao.common.util.ItemValidate;
import com.xiao.common.util.StringUtil;
import com.xiao.lottery.dto.PropDTO;
import com.xiao.lottery.mapper.LotteryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description 初始化道具抽奖奖池
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/7 22:17
 */
@Slf4j
@Component
public class InitLotteryPool implements ApplicationRunner {

    @Autowired
    private LotteryMapper lotteryMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initLotteryPool();
    }

    public void initLotteryPool() {
        // 从数据库中获取商品
        List<PropDTO> propDTOList = lotteryMapper.getAllProp();

        // 对商品按照中奖概率分组
        CacheHelper.PROP_GROUP_MAP = this.buildGroupMap(propDTOList);

        // 初始化彩票池
        generateLottery(CacheHelper.PROP_GROUP_MAP);

    }

    private void generateLottery(Map<String, List<PropDTO>> propGroupMap) {
        Random random = new Random(System.currentTimeMillis());
        // 存储随机生成的彩票池索引
        List<Integer> indexTemp = new ArrayList<>(Constants.LOTTERY_POOL_MAX);
        long startTime = System.currentTimeMillis();
        for (String probability : propGroupMap.keySet()) {
            Integer probabilityInt = Integer.valueOf(probability);
            Integer count = 0;
            while (true) {
                // 将生成的彩票数据
                // 当前概率的组生成完毕则下一个概率
                if (count >= probabilityInt) {
                    break;
                }
                Integer indexRandom = random.nextInt(Constants.LOTTERY_POOL_MAX);
                // 如果临时数组中已经有了该生成的数字,则重新生成
                if (indexTemp.contains(indexRandom)) {
                    continue;
                }
                // 没有则将生成的数字放入临时数组中
                indexTemp.add(indexRandom);
                // 并在最终的彩票池添加数据
                CacheHelper.LOTTERY_POOL_LIST.set(indexRandom, probability);
                // 当前概率生成数统计
                count++;
            }
        }
        // 以下只是日志
        log.info("生成彩票池耗时:{} 毫秒", System.currentTimeMillis() - startTime);
        log.info("道具组map结果为:{}", JSON.toJSONString(CacheHelper.PROP_GROUP_MAP));
        log.info("彩票池结果为:{}", JSON.toJSONString(CacheHelper.LOTTERY_POOL_LIST));
        this.lotteryPoolData();

    }

    private void lotteryPoolData() {
        Map<String, Integer> map = new HashMap<>();
        for (String tempPropDto : CacheHelper.LOTTERY_POOL_LIST) {
            // 按概率进行分组
            String key = StringUtil.getString(tempPropDto, Constants.NULL_STR);
            if (map.containsKey(key)) {
                Integer count = map.get(key);
                map.put(tempPropDto, ++count);
            } else {
                Integer count = 1;
                map.put(key, count);
            }
        }
        log.info("LOTTERY_POLL_LIST 汇总结果为:{}", JSON.toJSONString(map));
    }

    /**
     * @Description 对商品按照中奖概率分组
     * @Author xiaoxuewang_vendor
     * @Date 2018/12/7 18:46
     * @Param [propDTOList]
     * @Return
     **/
    private Map<String, List<PropDTO>> buildGroupMap(List<PropDTO> propDTOList) {
        Map<String, List<PropDTO>> propGroupMap = new HashMap<>();
        if (ItemValidate.isNotEmpty(propDTOList)) {
            for (PropDTO tempPropDto : propDTOList) {
                String probability = StringUtil.transformNullStr(tempPropDto.getProbability());
                // 按概率进行分组
                if (propGroupMap.containsKey(probability)) {
                    propGroupMap.get(probability).add(tempPropDto);
                } else {
                    List<PropDTO> propDTOListTemp = new ArrayList<>();
                    propDTOListTemp.add(tempPropDto);
                    propGroupMap.put(probability, propDTOListTemp);
                }
            }
        }
        return propGroupMap;
    }
}
