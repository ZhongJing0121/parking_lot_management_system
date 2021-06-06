package info.zhongjing.plms.service;

import info.zhongjing.plms.entity.AllRecord;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/5/12 7:57 下午 </p>
 */
public interface StatisticService {

    /**
     * 统计
     */
    List<AllRecord> statistic(String plateNumber, String startTime, String endTime);

}
