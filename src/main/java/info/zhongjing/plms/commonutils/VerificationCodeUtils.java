package info.zhongjing.plms.commonutils;

import java.util.Random;

/**
 * @author ZhongJing </p>
 * @Description 验证码工具类</p>
 * @date 2021/4/11 5:28 下午 </p>
 */
public class VerificationCodeUtils {

    /**
     * 生成一个6位数字的的验证码
     * @return 将验证码以字符串的形式返回
     */
    public static String generateCode() {
        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

}
