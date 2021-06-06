package info.zhongjing.plms.controller;

import info.zhongjing.plms.commonutils.PageQueryUtils;
import info.zhongjing.plms.entity.AllRecord;
import info.zhongjing.plms.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/5/12 7:45 下午 </p>
 */
@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController {

    @Autowired
    StatisticService statisticService;

    @GetMapping(value = "/main")
    public ModelAndView main(@RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize,
                             @RequestParam(value = "plate_number", defaultValue = "")String plateNumber,
                             @RequestParam(value = "start_time", defaultValue = "")String startTime,
                             @RequestParam(value = "end_time", defaultValue = "")String endTime) {
        ModelAndView mav = new ModelAndView();

        // 查询统计数据
        List<AllRecord> allRecordList = statisticService.statistic(plateNumber, startTime, endTime);

        // 分页
        PageQueryUtils<AllRecord> pageQueryUtils = new PageQueryUtils<>(pageNum, pageSize, allRecordList);
        pageQueryUtils.page();

        // 将数据放到request域
        mav.addObject("pageQueryUtils", pageQueryUtils);
        mav.addObject("plate_number", plateNumber);
        mav.addObject("start_time", startTime);
        mav.addObject("end_time", endTime);

        mav.setViewName("statistic/main");

        return mav;
    }

}
