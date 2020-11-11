package wang.snalc.stsossaliyun.util;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.snalc.stsossaliyun.configuration.AliSTSConfiguration;
import wang.snalc.stsossaliyun.entity.Bucket;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AliSTSUtil {

    private final
    AliSTSConfiguration aliSTSConfiguration;

    private static String STSAccessKeyId;
    private static String STSAccessKeySecret;
    private static String STSSecurityToken;
    private static LocalDateTime Expiration;

    @Autowired
    public AliSTSUtil(AliSTSConfiguration aliSTSConfiguration) {
        this.aliSTSConfiguration = aliSTSConfiguration;
    }

    public Map<String, String> getToken() {
        if (Expiration != null) {
            if (Expiration.isBefore(LocalDateTime.now())) {
                refreshSTS();
            }
        } else {
            refreshSTS();
        }
        Map<String, String> map = new HashMap<>();
        map.put("AccessKeyId", STSAccessKeyId);
        map.put("Expiration", Expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        map.put("AccessKeySecret", STSAccessKeySecret);
        map.put("SecurityToken", STSSecurityToken);
        return map;
    }

    private void refreshSTS() {
        String endpoint = "sts.aliyuncs.com";
        String roleSessionName = "session-name";
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setSysEndpoint(endpoint);
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(aliSTSConfiguration.getRoleArn());
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy); // Optional
            request.setDurationSeconds(3600L); // Optional

            IClientProfile profile = DefaultProfile.getProfile(
                    "",
                    aliSTSConfiguration.getAccessKeyId(),
                    aliSTSConfiguration.getAccessKeySecret());
            //用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);

            final AssumeRoleResponse response = client.getAcsResponse(request);
            Expiration = LocalDateTime.now().plusSeconds(2500L);
            STSAccessKeyId = response.getCredentials().getAccessKeyId();
            STSAccessKeySecret = response.getCredentials().getAccessKeySecret();
            STSSecurityToken = response.getCredentials().getSecurityToken();
        } catch (ClientException e) {
            log.info("Failed");
            log.info("ErrorCode" + e.getErrCode());
            log.info("ErrorMessage" + e.getErrMsg());
            log.info("RequestId" + e.getRequestId());
        }
    }

    public String getPicUrl(String name, Bucket bucket) {
        getToken();
        OSS ossClient = new OSSClientBuilder().build("oss-cn-beijing.aliyuncs.com", STSAccessKeyId, STSAccessKeySecret, STSSecurityToken);
        Date expiration = new Date(System.currentTimeMillis() + 60 * 1000);
        URL url = ossClient.generatePresignedUrl("italy", name, expiration);
        ossClient.shutdown();
        return String.valueOf(url);
    }
}
