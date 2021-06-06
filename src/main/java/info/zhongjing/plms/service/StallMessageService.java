package info.zhongjing.plms.service;

import info.zhongjing.plms.entity.StallMessage;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/5/23 10:41 下午 </p>
 */
public interface StallMessageService {

    StallMessage showMessage();

    boolean update(StallMessage stallMessage);

}
