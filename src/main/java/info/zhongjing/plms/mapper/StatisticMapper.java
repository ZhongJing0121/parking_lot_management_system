package info.zhongjing.plms.mapper;

import info.zhongjing.plms.entity.AllRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/5/12 8:08 下午 </p>
 */
@Mapper
public interface StatisticMapper {

    List<AllRecord> statistic(@Param("plateNumber") String plateNumber, @Param("startTime") String startTime, @Param("endTime") String endTime);

}
