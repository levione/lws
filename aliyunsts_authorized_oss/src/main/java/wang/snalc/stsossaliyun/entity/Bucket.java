package wang.snalc.stsossaliyun.entity;

import lombok.*;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
@Table(name = "bucket")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bucketId;
    // null
    @ApiModelProperty(name = "bucketName", value = "null")
    private String bucketName;
    // null
    @ApiModelProperty(name = "endPoint", value = "null")
    private String endPoint;

    private String description;

    public void merge(Bucket t) {
        if (t.bucketId != null) {
            this.bucketId = t.bucketId;
        }
        if (t.bucketName != null) {
            this.bucketName = t.bucketName;
        }
        if (t.endPoint != null) {
            this.endPoint = t.endPoint;
        }
    }
}
