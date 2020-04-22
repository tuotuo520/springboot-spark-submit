package com.taihe.springbootsparksubmit.controller;

import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.util.HdfsUtils;
import com.taihe.springbootsparksubmit.util.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.hadoop.fs.Path;
import org.apache.poi.ss.formula.functions.T;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author : Grayson
 * @Email : sunhui@yunliketech.com
 * @Date ：2020/4/16
 */
@CommonsLog
@RestController
@Api(tags = "上传下载文件控制类")
@RequestMapping("uploadOrDownload")
public class FileUploadAndDownloadController {

    private UploadUtils uploadUtils;
    @Autowired
    public void setUploadUtils (UploadUtils uploadUtils) {
        this.uploadUtils = uploadUtils;
    }

    /**
     * 实现文件上传
     */
    @PostMapping("fileUpload")
    @ApiOperation("文件上传到hdfs")
    @ApiImplicitParams({ //添加请求参数，可添加多个，paramType是请求的参数位置
            @ApiImplicitParam(name = "file" ,value = "文件信息" ,paramType = "header")
    })
    @ResponseBody
    public Result<T> fileUpload(@NotNull @RequestParam("fileName") MultipartFile file) throws IOException {
        return uploadUtils.uploadFile(file);
    }






}
