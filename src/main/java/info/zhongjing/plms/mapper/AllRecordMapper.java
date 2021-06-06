package info.zhongjing.plms.mapper;

import info.zhongjing.plms.entity.AllRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description 所有车辆记录的Mapper </p>
 * @date 2021/3/11 8:51 下午 </p>
 */
@Mapper
public interface AllRecordMapper {

    /**
     * 添加记录
     * @return true成功，false失败
     */
    boolean add(AllRecord allRecord);

    /**
     * 查询所有记录
     * @return 记录集合
     */
    List<AllRecord> list();

    /**
     * 根据车牌号模糊查询
     * @param plateNumber 车牌号
     * @return 记录集合
     */
    List<AllRecord> getByplateNumber(String plateNumber);

}
