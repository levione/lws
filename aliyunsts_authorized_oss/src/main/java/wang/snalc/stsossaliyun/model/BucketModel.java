package wang.snalc.stsossaliyun .model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class BucketModel {

  // null
  @ApiModelProperty(name = "bucketName" ,value = "null")
  private String bucketName;
  // null
  @ApiModelProperty(name = "endPoint" ,value = "null")
  private String endPoint;

  private String description;

}
