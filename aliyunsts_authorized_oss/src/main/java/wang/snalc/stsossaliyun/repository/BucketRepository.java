package wang.snalc.stsossaliyun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import wang.snalc.stsossaliyun.entity.Bucket;

import java.util.Optional;

@Component
public interface BucketRepository extends JpaRepository<Bucket,Long> {
    Optional<Bucket> findBucketByBucketName(String bucketName);
}
