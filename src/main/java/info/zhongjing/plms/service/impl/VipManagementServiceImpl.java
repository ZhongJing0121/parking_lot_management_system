package info.zhongjing.plms.service.impl;

import info.zhongjing.plms.commonutils.DateTimeFormatUtils;
import info.zhongjing.plms.entity.StallMessage;
import info.zhongjing.plms.entity.Vip;
import info.zhongjing.plms.mapper.StallMessageMapper;
import info.zhongjing.plms.mapper.VipMapper;
import info.zhongjing.plms.service.VipManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/3/13 4:34 下午 </p>
 */
@Service
public class VipManagementServiceImpl implements VipManagementService {

    @Autowired
    VipMapper vipMapper;

    @Autowired
    StallMessageMapper stallMessageMapper;

    @Override
    public List<Vip> vipList(String plateNumber, String phoneNum) {
        return vipMapper.getByPlatenumberAndPhoneNum(plateNumber, phoneNum);
    }

    @Override
    public boolean addVip(Vip vip) {
        // 获取车位信息（包括会员计费标准）
        StallMessage stallMessage = stallMessageMapper.get();

        // 计算费用
        Map<String, Integer> integerMap = DateTimeFormatUtils.intervalDate(vip.getStartTime(), vip.getEndTime());
        double daySalary = stallMessage.getVipSalary();

        double salary = daySalary * 30 * 12 * integerMap.get("year")
                + daySalary * 30 * integerMap.get("month")
                + daySalary * integerMap.get("day");

        // 将算出的费用保存
        vip.setCharge(salary);

        // 保存会员信息
        boolean success = vipMapper.add(vip);

        return success;
    }

    @Override
    public Vip getById(Integer id) {
        return vipMapper.getById(id);
    }

    @Override
    public boolean update(Vip vip) {
        // 获取车位信息（包括会员计费标准）
        StallMessage stallMessage = stallMessageMapper.get();

        // 计算新的费用
        Map<String, Integer> integerMap = DateTimeFormatUtils.intervalDate(vip.getStartTime(), vip.getEndTime());
        double daySalary = stallMessage.getVipSalary();

        double salary = daySalary * 30 * 12 * integerMap.get("year")
                + daySalary * 30 * integerMap.get("month")
                + daySalary * integerMap.get("day");

        vip.setCharge(salary);

        return vipMapper.update(vip);
    }
}
