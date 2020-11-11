package wang.snalc.stsossaliyun.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aliyun")
public class AliSTSConfiguration {

    /**
     * 阿里云授权账号的accessKeyId
     */
    private String accessKeyId;


    /**
     * 阿里云授权账号的accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 阿里云授权账号的roleArn
     */
    private String roleArn;
}
