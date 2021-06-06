package info.zhongjing.plms.service.impl;

import info.zhongjing.plms.entity.AllRecord;
import info.zhongjing.plms.mapper.StatisticMapper;
import info.zhongjing.plms.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/5/12 7:57 下午 </p>
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    StatisticMapper statisticMapper;

    @Override
    public List<AllRecord> statistic(String plateNumber, String startTime, String endTime) {
        if ("".equals(startTime)) {
            startTime = "1000-01-01";
        }
        if ("".equals(endTime)) {
            endTime = "9999-12-31";
        }
        return statisticMapper.statistic(plateNumber, startTime, endTime);
    }
}
