package info.zhongjing.plms.service;

import info.zhongjing.plms.entity.AllRecord;
import info.zhongjing.plms.entity.InGarage;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description 车辆管理的Service层 </p>
 * @date 2021/3/12 10:01 上午 </p>
 */
public interface CarManagementService {

    /**
     * 车辆驶入
     * @param plateNumber 驶入车辆的车牌号
     * @return true成功，false失败，如果车库已满，则操作失败
     */
    boolean enterIntoCarport(String plateNumber);

    /**
     * 车辆驶出车库
     * @param allRecord 驶出车辆的id
     * @return true成功，false失败
     */
    boolean outIntoCarport(AllRecord allRecord);

    /**
     * 查询所有在库车辆的信息
     * @return 所有在库车辆信息的集合
     */
    List<InGarage> listIngarage(String plateNumber);

    /**
     * 列出某ID对应车辆的缴费清单
     * @param id 车辆id
     * @return 消费清单
     */
    AllRecord getDetailedList(Integer id);

    /**
     * 返回车位的占用率
     * @return 车位占用率
     */
    String getSeizureRate();

    /**
     * 车牌识别
     * @param file 图片
     * @return 车牌号
     */
    String identifyTheLicensePlate(MultipartFile file);
}
