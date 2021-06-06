package info.zhongjing.plms.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author ZhongJing </p>
 * @Description 在车库中的车辆信息 </p>
 * @date 2021/3/10 9:08 下午 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class InGarage {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 车辆入库的时间
     */
    private String startTime;

}
