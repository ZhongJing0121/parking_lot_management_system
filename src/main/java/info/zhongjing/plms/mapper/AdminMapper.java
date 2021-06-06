package info.zhongjing.plms.mapper;

import info.zhongjing.plms.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZhongJing </p>
 * @Description 管理员mapper </p>
 * @date 2021/4/1 1:42 下午 </p>
 */
@Mapper
public interface AdminMapper {

    /**
     * 添加
     * @param admin 实体类信息
     * @return true成功，false失败
     */
    boolean add(Admin admin);

    /**
     * 修改（更新）
     * @param admin 实体类信息
     * @return true成功，false失败
     */
    boolean update(Admin admin);

    /**
     * 根据用户名查询对应用户
     * @param username 用户名
     * @return 如果存在，返回用户对象；如果不存在，返回null
     */
    Admin selectByUsername(String username);

    /**
     * 根据id查询对应用户
     * @param id 用户id
     * @return 如果存在，返回用户信息；如果不存在，返回null
     */
    Admin selectById(Integer id);

    /**
     * 查询用户名或邮箱是否已存在
     * @param username 用户名
     * @param email 邮箱
     * @return 返回0表示都不存在；返回大于0表示用户名或邮箱至少有一个已被使用
     */
    Integer selectByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    /**
     * 查询用户名与邮箱是否存在
     * @param username 用户名
     * @param email 邮箱
     * @return 大于0表示存在，等于0则表示不存在
     */
    Integer selectByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    /**
     * 更新用户头像地址
     * @param id 用户id
     * @param filePath 头像地址
     * @return true成功，false失败
     */
    boolean updateHeadPortraitById(@Param("id") Integer id, @Param("filePath") String filePath);

}
