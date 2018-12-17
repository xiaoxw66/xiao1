package com.xiao.common.baseDto;

import com.xiao.lottery.dto.PropDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author xiaoxuewang_vendor
 * @Date 2018/12/5 23:43
 */
public class CacheHelper {

    public static List<String> LOTTERY_POOL_LIST = new ArrayList<>(Arrays.asList(new String[Constants.LOTTERY_POOL_MAX]));

    public static Map<String, List<PropDTO>> PROP_GROUP_MAP;

}
