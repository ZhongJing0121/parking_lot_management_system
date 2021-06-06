package info.zhongjing.plms.controller;

import info.zhongjing.plms.entity.StallMessage;
import info.zhongjing.plms.service.StallMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/5/23 10:43 下午 </p>
 */
@RestController
@RequestMapping(value = "/stallMessage")
@Slf4j
public class StallMessageController {

    @Autowired
    StallMessageService stallMessageService;

    @GetMapping(value = "/main")
    public ModelAndView main() {
        ModelAndView mav = new ModelAndView();

        StallMessage stallMessage = stallMessageService.showMessage();

        mav.addObject("stallMessage", stallMessage);

        mav.setViewName("stall_message/main");

        return mav;
    }

    @PutMapping(value = "/update")
    public boolean update(@RequestBody StallMessage stallMessage) {
        log.info("stallMessage:" + stallMessage);
        return stallMessageService.update(stallMessage);
    }

}
