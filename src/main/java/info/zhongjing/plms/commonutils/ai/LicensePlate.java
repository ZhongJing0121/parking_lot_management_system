package info.zhongjing.plms.commonutils.ai;

import info.zhongjing.plms.commonutils.ai.api.Base64Util;
import info.zhongjing.plms.commonutils.ai.api.FileUtil;
import info.zhongjing.plms.commonutils.ai.api.HttpUtil;

import java.net.URLEncoder;

/**
 * @author ZhongJing </p>
 * @Description </p>
 * @date 2021/3/15 5:36 下午 </p>
 */
public class LicensePlate {

    public static String licensePlate(String filePathStr) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(filePathStr);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = "[调用鉴权接口获取的token]";
//            String accessToken = "24.b1680d2455131610d17dff2dae5d49f2.2592000.1618393506.282335-23798087";
            // 这两个码已经被我注销了，应该是不可以用了。需要自己去百度AI开放平台注册使用，具体方法请百度
            String accessToken = AuthService.getAuth("PDF2b1y3tTcVR45QqffWLO8x", "uKpdkhv7dWk5l6iSi8g5wfzPdAbMBEuP");

            String result = HttpUtil.post(url, accessToken, param);
//            System.out.println(result);
            return result;
// https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=PDF2b1y3tTcVR45QqffWLO8x&client_secret=uKpdkhv7dWk5l6iSi8g5wfzPdAbMBEuP
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
