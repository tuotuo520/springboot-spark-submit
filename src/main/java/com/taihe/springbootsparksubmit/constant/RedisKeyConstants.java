package com.taihe.springbootsparksubmit.constant;

/**
 * @Author : Grayson
 * @Email : sunhui@yunliketech.com
 * @Date ：2020/4/20
 */
public class RedisKeyConstants {


    public static String getDatabaseKey(String key){
        return "taihe:platform:import:" + key;
    }



    public static String getAnalysisKey(String key){
        return "taihe:platform:analysis:" + key;
    }
}
