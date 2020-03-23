package com.taihe.springbootsparksubmit.controller;

import com.taihe.springbootsparksubmit.starter.SparkStarter;
import lombok.extern.apachecommons.CommonsLog;
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
@CommonsLog
public class SubmitController {


    @ResponseBody
    @PostMapping("/sendParamToSpark")
    public Object sendParamToSpark(@RequestBody String arg) {
        try {
            log.info(arg);
            SparkStarter.submitSqlTask(arg);
        } catch (IOException e) {
            log.error("Io Exception");
            return "Fail";
        }
        return "OK";
    }

    public static void main(String[] args) {
        String a = "\"{ \\\"type\\\":1, \\\"sourceInfo\\\":{ \\\"address\\\":\\\"jdbc:mysql://192.168.0.246:33306/wxyx?tinyInt1isBit=false&useUnicode=true&characterEncoding=UTF-8\\\", \\\"database\\\":\\\"wxyx\\\", \\\"tnames\\\":[\\\"c_user_main\\\",\\\"c_user_info\\\"], \\\"user\\\":\\\"root\\\", \\\"password\\\":\\\"thwl0755\\\" }, \\\"relationInfo\\\":{ \\\"relations\\\":[\\\"c_user_main left join c_user_info on c_user_main.id=c_user_info.main_uid\\\"], \\\"conditions\\\":[\\\"c_user_main.id>1100000\\\",\\\"c_user_main.sex=1\\\"] }, \\\"resultInfo\\\":{ \\\"fields\\\":[\\\"c_user_main.id\\\",\\\"c_user_main.sex\\\",\\\"c_user_main.phone\\\"] } }\"";
    }
}
