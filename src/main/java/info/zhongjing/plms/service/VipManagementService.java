package info.zhongjing.plms.service;

import info.zhongjing.plms.entity.Vip;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/3/13 4:34 下午 </p>
 */
public interface VipManagementService {

    /**
     * 获取所有vip信息，如果有参数则是根据条件模糊查询
     * @param plateNumber 车牌号
     * @param phoneNum 手机号
     * @return vip集合
     */
    List<Vip> vipList(String plateNumber, String phoneNum);

    /**
     * 添加会员信息
     * @param vip 封装了会员信息的实体类
     * @return true成功，false失败
     */
    boolean addVip(Vip vip);

    /**
     * 根据id获取会员信息
     * @param id 会员的id
     * @return 会员信息
     */
    Vip getById(Integer id);

    /**
     * 修改会员信息
     * @param vip 会员信息
     * @return true成功，false失败
     */
    boolean update(Vip vip);

}
