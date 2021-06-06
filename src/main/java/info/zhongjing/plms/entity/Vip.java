package info.zhongjing.plms.entity;

import lombok.*;

/**
 * @author ZhongJing </p>
 * @Description 会员信息 </p>
 * @date 2021/3/10 9:13 下午 </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Vip {

    /**
     * 会员ID
     */
    private Integer id;

    /**
     * 会员名称
     */
    private String username;

    /**
     * 注册手机号
     */
    private String phoneNum;

    /**
     * 会员车牌号
     */
    private String plateNumber;

    /**
     * 会员生效时间
     */
    private String startTime;

    /**
     * 会员到期时间
     */
    private String endTime;

    /**
     * 收取的费用
     */
    private Double charge;

}
