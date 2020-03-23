package com.taihe.springbootsparksubmit.starter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.io.IOException;

/**
 * @author sunhui
 * @version V1.0
 * @Description spark程序启动类
 * @Package com.taihe.springbootsparksubmit.starter
 * @date 2020/3/20 14:48
 * @ClassName SparkStarter
 */
@CommonsLog
public class SparkStarter {



    /**
     * 通过消息作为参数启动spark任务
     * @param msg
     * @throws IOException
     */
    public static void submitSqlTask(String msg) throws IOException {
        SparkAppHandle handler = new SparkLauncher()
                .setAppName("SerachJob")
                //spark home 路径
                .setSparkHome("/opt/cloudera/parcels/CDH-6.0.1-1.cdh6.0.1.p0.590678/lib/spark")
                .setMaster("yarn")
                .setConf("spark.driver-cores","1")
                .setConf("spark.driver.memory", "1g")
                .setConf("spark.executor.memory", "1g")
                .setConf("spark.executor.cores", "2")
                .setConf("spark.num.executors", "2")
                //关闭动态分配
                .setConf("spark.dynamicAllocation.enabled","false")
                .setConf("spark.driver.extraJavaOptions"," -Dfile.encoding=utf-8")
                .setConf("spark.executor.extraJavaOptions","-Dfile.encoding=utf-8")
                .setAppResource("/YunLiKe/jar/taihe/serach/serach-data-1.0-SNAPSHOT-jar-with-dependencies.jar")
                .setMainClass("com.taihe.serach.SerachJob")
                //参数
                .addAppArgs(msg)
                .setDeployMode("cluster")
                .startApplication(new SparkAppHandle.Listener() {
                    @Override
                    public void stateChanged(SparkAppHandle sparkAppHandle) {
                        log.info("********** submitSqlTask state  changed  **********");
                    }
                    @Override
                    public void infoChanged(SparkAppHandle sparkAppHandle) {
                        log.info("********** submitSqlTask info  changed  **********");
                    }
                });
    }
}
