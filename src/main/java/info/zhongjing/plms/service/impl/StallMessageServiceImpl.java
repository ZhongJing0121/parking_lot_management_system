package info.zhongjing.plms.service.impl;

import info.zhongjing.plms.entity.StallMessage;
import info.zhongjing.plms.mapper.StallMessageMapper;
import info.zhongjing.plms.service.StallMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/5/23 10:42 下午 </p>
 */
@Service
public class StallMessageServiceImpl implements StallMessageService {

    @Autowired
    StallMessageMapper stallMessageMapper;

    @Override
    public StallMessage showMessage() {
        return stallMessageMapper.get();
    }

    @Override
    public boolean update(StallMessage stallMessage) {
        return stallMessageMapper.update(stallMessage);
    }
}
