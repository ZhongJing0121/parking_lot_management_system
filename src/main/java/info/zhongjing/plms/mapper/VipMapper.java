package info.zhongjing.plms.mapper;

import info.zhongjing.plms.entity.Vip;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description 会员的Mapper </p>
 * @date 2021/3/11 8:53 下午 </p>
 */
@Mapper
public interface VipMapper {

    /**
     * 查询vip总数
     * @return 总数
     */
    Integer count();

    /**
     * 查询车牌是否在会员列表中
     * @param plateNumber 车牌号
     * @return 0不在，大于0在
     */
    Integer countByPlateNumber(String plateNumber);

    /**
     * 根据车牌号和手机号模糊查询，没传这两个就是全表查
     * @param plateNumber 车牌号
     * @param phoneNum 手机号
     * @return 返回一个List集合吧，我觉得只会查到1个，但是以防万一
     */
    List<Vip> getByPlatenumberAndPhoneNum(String plateNumber, String phoneNum);

    /**
     * 根据id查询对应会员信息
     * @return 会员信息
     */
    Vip getById(Integer id);

    /**
     * 根据传入实体类添加vip信息
     * @return true成功，false失败
     */
    boolean add(Vip vip);

    /**
     * 更新vip信息
     * @param vip 实体类信息
     * @return true成功，false失败
     */
    boolean update(Vip vip);

    /**
     * 根据传入id删除会员信息
     */
    void deleteById(Integer id);

}
