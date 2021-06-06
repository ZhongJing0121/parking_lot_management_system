package info.zhongjing.plms.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author ZhongJing </p>
 * @Description 所有车辆进出记录 </p>
 * @date 2021/3/10 9:10 下午 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class AllRecord {

    /**
     * 主键ID，依赖于in_garage中id
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 车辆进入车库的时间
     */
    private String startTime;

    /**
     * 车辆驶出车库的时间
     */
    private String endTime;

    /**
     * 收取的费用
     */
    private Double charge;

}
