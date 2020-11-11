package wang.snalc.stsossaliyun.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.snalc.stsossaliyun.entity.Bucket;
import wang.snalc.stsossaliyun.exception.ExceptionEnum;
import wang.snalc.stsossaliyun.exception.PublicException;
import wang.snalc.stsossaliyun.exception.ResultUnit;
import wang.snalc.stsossaliyun.repository.BucketRepository;
import wang.snalc.stsossaliyun.util.AliSTSUtil;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("oss")
public class OssController {
    @Autowired
    private AliSTSUtil aliSTSUtil;
//    private final
//    BucketRepository bucketRepository;

    @Autowired
//    public OssController(AliSTSUtil aliSTSUtil, BucketRepository bucketRepository) {
//        this.aliSTSUtil = aliSTSUtil;
//        this.bucketRepository = bucketRepository;
//    }

    @GetMapping("getToken")
    public ResponseEntity getToken() throws PublicException {
        Map map = aliSTSUtil.getToken();
        if (map != null) {
            return ResponseEntity.ok(ResultUnit.ok(map, "getToken"));
        } else {
            throw new PublicException(ExceptionEnum.PUBLIC_SERVICE_ERROR, "getToken");
        }
    }

    @GetMapping("getPicUrl")
    public ResponseEntity getPicUrl(String name, String bucketName) throws PublicException {
//        Optional<Bucket> bucketOptional = bucketRepository.findBucketByBucketName(bucketName);
//        if (bucketOptional.isPresent()) {
            String url = aliSTSUtil.getPicUrl(name, null);
            if (url != null) {
                return ResponseEntity.ok(ResultUnit.ok(url, "getToken"));
            } else {
                throw new PublicException(ExceptionEnum.PUBLIC_SERVICE_ERROR, "getToken");
            }
//        }else{
//            throw new PublicException(ExceptionEnum.PUBLIC_NOT_FOUND, "getToken");
//        }
    }
}
