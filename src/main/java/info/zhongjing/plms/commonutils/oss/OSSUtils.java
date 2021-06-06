package info.zhongjing.plms.commonutils.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author ZhongJing </p>
 * @Description 自己写的阿里云OSS操作工具类</ p>
 * @date 2021/4/6 4:37 下午 </p>
 */
public class OSSUtils {

    /**
     * 上传文件到OSS服务器，只支持单一文件上传，大小不超过5G
     *
     * @return 成功返回文件的外网链接，失败返回null
     */
    public static String upload(MultipartFile file, String adminName) {
        try (
                // 获取文件输入流
                InputStream inputStream = file.getInputStream();
        ) {
            // 仓库地址
            String endpoint = "https://oss-cn-beijing.aliyuncs.com";
            // AK
            String accessKeyId = "LTAI5tDxrNk5MxDwJDyuucPA";
            String accessKeySecret = "ZsJusuwusxU5EOTKihZcQrcdSEvwHT";

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 获取后缀
            String suffix = "";
            int index = fileName.lastIndexOf(".");
            if (index != -1) {
                suffix = fileName.substring(index);
            }

            // 拼接文件全路径：用户名/avatar+后缀
            String filePath = adminName + "/" + "avatar" + suffix;

            // 上传
            ossClient.putObject("plms-images", filePath, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 返回文件拼接好的外网链接
            return "https://plms-images.oss-cn-beijing.aliyuncs.com/" + filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
