package com.taihe.springbootsparksubmit.controller;

import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.starter.SparkStarter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author sunhui
 * @version V1.0
 * @Description 接口形式提交spark任务
 * @Package com.taihe.springbootsparksubmit.controller
 * @date 2020/3/20 15:08
 * @ClassName SubmitController
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/")
@Api(tags = "提交任务控制类")
@CommonsLog
public class SubmitController {

    private SparkStarter sparkStarter;
    @Autowired
    public void setSparkStarter(SparkStarter sparkStarter) {
        this.sparkStarter = sparkStarter;
    }

    @ResponseBody
    @ApiOperation("提交到spark")
    @PostMapping("/sendParamToSpark")
    public Result<T> sendParamToSpark(@RequestBody String arg) {
        try {
            log.info(arg);
            sparkStarter.submitSqlTask(arg);
        } catch (IOException e) {
            log.error("Io Exception");
            return Result.error("提交spark任务异常" + e);
        }
        return Result.ok();
    }

}
