package com.taihe.springbootsparksubmit.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页请求")
public class PageRequest {

    @ApiModelProperty(value = "页码", example = "1")
    private int page = 1;

    @ApiModelProperty(value = "页纪录数", example = "10")
    private int limit = 10;
}
