package info.zhongjing.plms.controller;

import info.zhongjing.plms.commonutils.PageQueryUtils;
import info.zhongjing.plms.entity.Vip;
import info.zhongjing.plms.service.VipManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/3/13 4:33 下午 </p>
 */
@Slf4j
@RestController
@RequestMapping(value = "/vip")
public class VipManagementController {

    @Autowired
    VipManagementService vipManagementService;

    @GetMapping(value = "/main")
    public ModelAndView main(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "plate_number", defaultValue = "") String plateNumber,
                             @RequestParam(value = "phone_num", defaultValue = "") String phoneNum) {
        ModelAndView mav = new ModelAndView();

        // 查询会员列表数据
        List<Vip> vips = vipManagementService.vipList(plateNumber, phoneNum);

        // 分页
        PageQueryUtils<Vip> pageQueryUtils = new PageQueryUtils<>(pageNum, pageSize, vips);

        // 传送数据
        mav.addObject("pageQueryUtils", pageQueryUtils);
        mav.addObject("plate_number", plateNumber);
        mav.addObject("phone_num", phoneNum);

        mav.setViewName("vip_management/main");

        return mav;
    }

    @GetMapping(value = "/toAdd")
    public ModelAndView toAdd() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("nowTime", LocalDate.now());

        mav.setViewName("vip_management/add");

        return mav;
    }

    @PostMapping(value = "/add")
    public ModelAndView add(Vip vip) {
        ModelAndView mav = new ModelAndView();

        boolean success = vipManagementService.addVip(vip);

        log.info("会员添加成功，会员信息：" + vip);

        mav.setViewName("redirect:/vip/main");

        return mav;
    }

    @GetMapping(value = "/toRenew")
    public ModelAndView toRenew(@RequestParam("id") Integer id) {
        ModelAndView mav = new ModelAndView();

        if (id == null) {
            mav.setViewName("redirect:/vip/main");
        } else {
            Vip vip = vipManagementService.getById(id);
            if (vip == null) {
                mav.setViewName("redirect:/vip/main");
            }
            mav.addObject("vip", vip);
            mav.setViewName("vip_management/renew");
        }

        return mav;
    }

    @PostMapping(value = "/renew")
    public ModelAndView renew(Vip vip)  {
        ModelAndView mav = new ModelAndView();

        log.info(vip.toString());

        boolean flag = vipManagementService.update(vip);

        if (flag) {
            mav.setViewName("redirect:/vip/main");
        } else {
            mav.setViewName("redirect:/vip/toRenew?id=" + vip.getId());
        }

        return mav;
    }

}
