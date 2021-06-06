package info.zhongjing.plms.controller;

import info.zhongjing.plms.commonutils.PageQueryUtils;
import info.zhongjing.plms.entity.AllRecord;
import info.zhongjing.plms.entity.InGarage;
import info.zhongjing.plms.service.CarManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description 车辆管理Controller </p>
 * @date 2021/3/12 6:38 下午 </p>
 */
@Slf4j
@RestController
@RequestMapping(value = "/car")
public class CarManagementController {

    @Autowired
    CarManagementService carManagementService;

    @GetMapping(value = "/main")
    public ModelAndView main(@RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                             @RequestParam(value = "plate_number", defaultValue = "")String plateNumber) {

        ModelAndView mav = new ModelAndView();

        // 获取所有车库内车辆数据
        List<InGarage> inGarages = carManagementService.listIngarage(plateNumber);

        // 对数据进行分页
        PageQueryUtils<InGarage> pageUtils = new PageQueryUtils<>(pageNum, pageSize, inGarages);
        pageUtils.page();

        // 获取车位占用率
        String seizureRate = carManagementService.getSeizureRate();

        // 放数据到request域
        mav.addObject("seizureRate", seizureRate);
        mav.addObject("pageUtils", pageUtils);
        mav.addObject("plateNumber", plateNumber);
        mav.setViewName("car_management/main");

        return mav;
    }

    /**
     * 车辆驶入
     * @param plateNumber 车牌号
     * @return true or false
     */
    @GetMapping(value = "/enter")
    public String enter(@RequestParam("plate_number")String plateNumber) {
        boolean flag = carManagementService.enterIntoCarport(plateNumber);
        return flag ? "true" : "false";
    }

    /**
     * 支付完，车辆出库
     * @param allRecord 车辆信息
     * @return true成功，false失败
     */
    @DeleteMapping(value = "/out")
    public boolean out(@RequestBody AllRecord allRecord) {
        boolean flag = carManagementService.outIntoCarport(allRecord);

        log.info(allRecord.toString());

        return flag;
    }

    /**
     * 获取指定id车辆的缴费清单
     * @param id 车辆id
     * @return 车辆的缴费清单
     */
    @GetMapping(value = "/getDetailedList")
    public AllRecord getDetailedList(Integer id) {
        AllRecord detailedList = carManagementService.getDetailedList(id);
        log.info("缴费清单：" + detailedList);
        return detailedList;
    }

    @PostMapping(value = "/identify_the_license_plate")
    public String identifyTheLicensePlate(@RequestParam MultipartFile file) {
        return carManagementService.identifyTheLicensePlate(file);
    }

}
