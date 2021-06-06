package info.zhongjing.plms.entity;

import lombok.*;

/**
 * @author ZhongJing </p>
 * @Description 车位信息 </p>
 * @date 2021/3/10 8:59 下午 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class StallMessage {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 车位总数
     */
    private Integer totality;

    /**
     * 占用中的车位数量
     */
    private Integer occupy;

    /**
     * 临时停车价格
     */
    private Double salary;

    /**
     * 会员收费价格
     */
    private Double vipSalary;

}
