package wang.snalc.stsossaliyun .model;

import lombok.*;
import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class BucketModel2 {

  // null
  @ApiModelProperty(name = "bucketId" ,value = "null")
  private Long bucketId;
  // null
  @ApiModelProperty(name = "bucketName" ,value = "null")
  private String bucketName;
  // null
  @ApiModelProperty(name = "endPoint" ,value = "null")
  private String endPoint;

  private String description;

}
