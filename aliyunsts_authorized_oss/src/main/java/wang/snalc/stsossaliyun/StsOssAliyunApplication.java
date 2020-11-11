package wang.snalc.stsossaliyun;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableSwagger2Doc
//@SpringBootApplication
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class StsOssAliyunApplication {

    public static void main(String[] args) {
        SpringApplication.run(StsOssAliyunApplication.class, args);
    }

}
