package info.zhongjing.plms.mapper;

import info.zhongjing.plms.entity.InGarage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/3/11 8:23 下午 </p>
 */
@Mapper
public interface InGarageMapper {

    /**
     * 获取所有在车库中的车辆信息
     */
    List<InGarage> listAll(String plateNumber);

    /**
     * 根据车牌号模糊查询
     * @param plateNumber 车牌号
     * @return 一个InGarage集合，可能是一个可能是多个，如果输入的车牌号是完整的话，应该只有一个
     */
    List<InGarage> getByPlateNumber(String plateNumber);

    InGarage getById(Integer id);

    /**
     * 根据传入POJO添加车辆
     * @param inGarage 入库的车辆信息
     * @return true成功，false失败
     */
    boolean add(InGarage inGarage);

    /**
     * 根据id移除车库中指定车辆信息
     * @param id 车辆的id
     * @return true表示成功，false表示失败
     */
    boolean removeById(Integer id);

    /**
     * 设置车辆的驶入时间（主要用于会员车到期之后计费）
     * @param startTime 驶入时间
     */
    void setStartTimeById(String startTime, Integer id);

}
