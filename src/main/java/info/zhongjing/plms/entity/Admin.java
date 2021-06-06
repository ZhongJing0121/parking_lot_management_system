package info.zhongjing.plms.entity;

import lombok.*;

/**
 * @author ZhongJing </p>
 * @Description 管理员实体类 </p>
 * @date 2021/4/1 1:38 下午 </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Admin {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String headPortrait;

}
