package info.zhongjing.plms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("info.zhongjing.plms.mapper")
@ServletComponentScan
public class ParkingLotManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotManagementSystemApplication.class, args);
    }

}
