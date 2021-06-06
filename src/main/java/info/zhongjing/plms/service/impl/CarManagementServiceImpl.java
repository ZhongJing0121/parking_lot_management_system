package info.zhongjing.plms.service.impl;

import info.zhongjing.plms.commonutils.DateTimeFormatUtils;
import info.zhongjing.plms.commonutils.ai.LicensePlate;
import info.zhongjing.plms.entity.AllRecord;
import info.zhongjing.plms.entity.InGarage;
import info.zhongjing.plms.entity.StallMessage;
import info.zhongjing.plms.entity.Vip;
import info.zhongjing.plms.mapper.AllRecordMapper;
import info.zhongjing.plms.mapper.InGarageMapper;
import info.zhongjing.plms.mapper.StallMessageMapper;
import info.zhongjing.plms.mapper.VipMapper;
import info.zhongjing.plms.service.CarManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/3/12 10:02 上午 </p>
 */
@Service
@Slf4j
public class CarManagementServiceImpl implements CarManagementService {

    @Autowired
    InGarageMapper inGarageMapper;

    @Autowired
    StallMessageMapper stallMessageMapper;

    @Autowired
    AllRecordMapper allRecordMapper;

    @Autowired
    VipMapper vipMapper;

    @Override
    public boolean enterIntoCarport(String plateNumber) {
        // 将传入数据封装成一个对象
        InGarage inGarage = new InGarage();
        // 记录车牌
        inGarage.setPlateNumber(plateNumber);
        // 记录驶入时间
        inGarage.setStartTime(DateTimeFormatUtils.getNowDateTime());

        // 判断是否有空闲车位
        // 查询车位信息
        StallMessage stallMessage = stallMessageMapper.get();
        // 查询vip总数
        Integer vipCount = vipMapper.count();
        // 如果已占用车位大于等于总车位数，则表示车位已满，车辆无法入库
        if (stallMessage.getOccupy() >= stallMessage.getTotality()) {
            return false;
        }

        // 有空闲车位，则可以入库
        try {
            // 保存驶入车辆信息到数据库
            boolean flag1 = inGarageMapper.add(inGarage);
            // 更新已占用车位数量
            boolean flag2 = stallMessageMapper.updateOccupy(stallMessage.getOccupy() + 1);

            // 日志
            log.info("车辆驶入，车牌号：" + inGarage.getPlateNumber() + "，驶入时间：" + inGarage.getStartTime());

            return flag1 && flag2;
        } catch (Exception e) {
            // 走到这一步一般都是相同车牌的车辆重复进入
            log.error("出错，错误类型：" + e.getClass());
            log.error("具体报错信息：" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean outIntoCarport(AllRecord allRecord) {

        // 删除已在车库中的信息
        boolean flag1 = inGarageMapper.removeById(allRecord.getId());

        // 减少占用车位数量
        StallMessage stallMessage = stallMessageMapper.get();
        stallMessageMapper.updateOccupy(stallMessage.getOccupy() - 1);

        // 将记录写入所有车辆记录中
        boolean flag2 = allRecordMapper.add(allRecord);

        // 日志
        log.info("车辆驶出，" + allRecord);

        return flag1 && flag2;
    }

    @Override
    public List<InGarage> listIngarage(String plateNumber) {
        return inGarageMapper.listAll(plateNumber);
    }

    @Override
    public AllRecord getDetailedList(Integer id) {
        // 判断是否是会员
        InGarage inGarage = inGarageMapper.getById(id);
        Integer count = vipMapper.countByPlateNumber(inGarage.getPlateNumber());

        // 计算停车时长
        // 获取车辆驶入的时间
        String startTime = inGarage.getStartTime();
        // 获取车辆驶出的时间
        String endTime = DateTimeFormatUtils.getNowDateTime();

        AllRecord allRecord = new AllRecord();

        // 不是会员，计算费用
        if (count == 0) {
            // 获取临时车收费标准
            StallMessage stallMessage = stallMessageMapper.get();
            Double salary = stallMessage.getSalary();

            // 获取驶入与驶出的间隔时间
            Map<String, Integer> intervalTime = DateTimeFormatUtils.intervalTime(startTime, endTime);
            int intervalDay = intervalTime.get("day");
            int intervalHour = intervalTime.get("hour");
            int intervalMinute = intervalTime.get("minute");

            // 计费规则
            if (intervalMinute >= 3) {
                intervalHour++;
            }
            intervalMinute = 0;

            if (intervalHour >= 5) {
                intervalDay++;
                intervalHour = 0;
            }

            // 计费
            double charge = intervalDay * salary * 5 + intervalHour * salary;

            allRecord.setCharge(charge);

        } else {
            // 是会员

            // 获取车牌号对应的vip
            List<Vip> vips = vipMapper.getByPlatenumberAndPhoneNum(inGarage.getPlateNumber(), "");

            // 这里可能会有bug，不过车牌输入完整的话不会出现这种情况，因为数据库里面vip车牌号有唯一约束
            Vip vip = vips.get(0);

            // 用我自己写的工具类判断过期了没
            boolean expired = DateTimeFormatUtils.isBefore(vip.getEndTime());

            if (!expired) {
                // 如果没有过期，不收钱
                log.info("没有过期，不收钱");
                allRecord.setCharge(0.0);
            } else {
                // 如果过期了，按临时车计费（早干啥了？）
                log.info("过期了，按临时车收费");
                // 把会员删了
                vipMapper.deleteById(vip.getId());
                // 如果vip到期的时间晚于驶入车库的时间，按vip到期时间算
                if (!DateTimeFormatUtils.isa(vip.getEndTime(), inGarage.getStartTime())) {
                    // 把停车的开始时间设置成会员的到期时间
                    inGarageMapper.setStartTimeById(vip.getEndTime(), id);
                }
                // 如果vip到期的时间早于驶入车库的时间，按驶入车库的时间来算
                // 递归调用本方法
                return getDetailedList(id);
            }

        }

        allRecord.setId(inGarage.getId());
        allRecord.setPlateNumber(inGarage.getPlateNumber());
        allRecord.setStartTime(startTime);
        allRecord.setEndTime(endTime);

        return allRecord;
    }

    @Override
    public String getSeizureRate() {
        StallMessage stallMessage = stallMessageMapper.get();
        double seizureRate = new Double(stallMessage.getOccupy()) / new Double(stallMessage.getTotality());

        int result = (int) (seizureRate * 100);
        return result + "%";
    }

    public String identifyTheLicensePlate(@RequestParam MultipartFile file) {

        if (file.isEmpty()) {
            return "文件为空";
        }

        String result = "";

        // 创建输入输出流
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // 指定上传的位置
            String path = "/Users/zhangfan/IdeaProjects/parking_lot_management_system/src/main/resources/static/images/car_num/";
            // 获取文件的输入流
            inputStream = file.getInputStream();
            // 获取时间戳
            String time = System.currentTimeMillis() + "";
            // 获取上传时的文件名
            String fileName = file.getOriginalFilename();
            // 设置路径和文件名
            File targetFile = new File(path + time + "-" + fileName);

            // 判断文件父目录是否存在
            if (!targetFile.getParentFile().exists()) {
                //不存在就创建一个
                targetFile.getParentFile().mkdir();
            }

            // 获取文件的输出流
            outputStream = new FileOutputStream(targetFile);

            // 使用资源访问器FileCopyUtils的copy方法拷贝文件
            FileCopyUtils.copy(inputStream, outputStream);

            // 获取到车牌号
            result = LicensePlate.licensePlate(path + time + "-" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("车牌识别返回的JSON：" + result);

        return result;
    }
}
