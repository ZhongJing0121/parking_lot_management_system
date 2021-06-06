package info.zhongjing.plms.mapper;

import info.zhongjing.plms.entity.StallMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/3/11 8:26 下午 </p>
 */
@Mapper
public interface StallMessageMapper {

    /**
     * 查询车位信息
     *
     * @return 车位的所有信息，包括车位总数，已用车位数量，车位收费标准（元/小时）,会员收费标准（元/月）
     */
    StallMessage get();

    /**
     * 修改车库中的车位总数（万一车库扩建的话）
     *
     * @param totality 要修改的数量
     * @return true成功，false失败
     */
    boolean updateTotality(Integer totality);

    /**
     * 修改已占用车位的数量
     *
     * @return true成功，false失败
     */
    boolean updateOccupy(Integer occupy);

    /**
     * 修改车库信息中临时车位收费标准（元/每小时）
     *
     * @return true成功，false失败
     */
    boolean updateSalary(Double salary);

    /**
     * 修改车库信息中vip收费标准（元/每月）
     *
     * @param vipSalary 新收费标准
     * @return true成功，false失败
     */
    boolean updateVipSalary(double vipSalary);

    /**
     * 修改停车场信息
     * @param stallMessage 停车场信息
     * @return true成功，false失败
     */
    boolean update(StallMessage stallMessage);

}
