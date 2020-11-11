package wang.snalc.stsossaliyun.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.snalc.stsossaliyun.entity.Bucket;
import wang.snalc.stsossaliyun.exception.ExceptionEnum;
import wang.snalc.stsossaliyun.exception.PublicException;
import wang.snalc.stsossaliyun.exception.ResultUnit;
import wang.snalc.stsossaliyun.model.BucketModel;
import wang.snalc.stsossaliyun.model.BucketModel2;
import wang.snalc.stsossaliyun.repository.BucketRepository;

import java.util.List;
import java.util.Optional;

/**
 * 多bucket对应模块的bucket管理接口
 */
@RestController
@RequestMapping("bucket")
public class BucketController {

//    @Autowired
//    private final  BucketRepository bucketRepository;

    @Autowired
    private final
    Gson gson;


    @Autowired
    public BucketController( Gson gson) {
//        this.bucketRepository = bucketRepository;
        this.gson = gson;
    }

    @PostMapping("add")
    public ResponseEntity add(BucketModel bucketModel) {
        Bucket bucket = gson.fromJson(gson.toJson(bucketModel), new TypeToken<Bucket>() {
        }.getType());
//        bucketRepository.save(bucket);
        return ResponseEntity.ok(ResultUnit.ok(bucket, "bucket/add"));
    }

    @PostMapping("delete")
    public ResponseEntity delete(Long id) {
//        bucketRepository.findById(id).ifPresent(bucketRepository::delete);
        return ResponseEntity.ok(ResultUnit.ok("", "bucket/delete"));
    }

    @PostMapping("update")
    public ResponseEntity update(BucketModel2 bucketModel2) throws PublicException {
        if (bucketModel2.getBucketId() == null) {
            throw new PublicException(ExceptionEnum.PUBLIC_BAD_REQUEST, "/update");
        } else {
            Optional<Bucket> bucketOptional = null;//bucketRepository.findById(bucketModel2.getBucketId());
            if (bucketOptional.isPresent()) {
                Bucket bucketForUpDate = gson.fromJson(gson.toJson(bucketModel2), new TypeToken<Bucket>() {
                }.getType());
                Bucket bucketForSave = bucketOptional.get();
                if (bucketForUpDate.getBucketName() != null) {
                    bucketForSave.setBucketName(bucketForUpDate.getBucketName());
                }
                if (bucketForUpDate.getEndPoint() != null) {
                    bucketForSave.setEndPoint(bucketForUpDate.getEndPoint());
                }
                if (bucketForUpDate.getDescription() != null) {
                    bucketForSave.setDescription(bucketForUpDate.getDescription());
                }
//                bucketRepository.save(bucketForSave);
                return ResponseEntity.ok(ResultUnit.ok(bucketForSave, "bucket/update"));
            } else {
                throw new PublicException(ExceptionEnum.PUBLIC_NOT_FOUND, "/update");
            }
        }
    }

//    @GetMapping("listByExample")
//    public ResponseEntity listByExample(BucketModel bucketModel, Integer page, Integer size) {
//        Bucket bucket = gson.fromJson(gson.toJson(bucketModel), new TypeToken<Bucket>() {
//        }.getType());
//        if (page != null && size != null) {
//            Page<Bucket> bucketPage = bucketRepository.findAll(Example.of(bucket), PageRequest.of(page - 1, size));
//            return ResponseEntity.ok(ResultUnit.ok(bucketPage, "bucket/listByExample"));
//        } else {
//            List<Bucket> bucketList = bucketRepository.findAll(Example.of(bucket));
//            return ResponseEntity.ok(ResultUnit.ok(bucketList, "bucket/listByExample"));
//        }
//    }

//    @GetMapping("selectById")
//    public ResponseEntity selectById(Long id) throws PublicException {
//        Optional<Bucket> bucketOptional = bucketRepository.findById(id);
//        if (bucketOptional.isPresent()) {
//            return ResponseEntity.ok(ResultUnit.ok(bucketOptional.get(), "bucket/selectById"));
//        } else {
//            throw new PublicException(ExceptionEnum.PUBLIC_NOT_FOUND, "/selectById");
//        }
//    }
}
