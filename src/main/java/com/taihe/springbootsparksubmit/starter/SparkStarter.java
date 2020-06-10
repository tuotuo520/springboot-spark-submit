package com.taihe.springbootsparksubmit.starter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class SparkStarter {

    @Value("${sparkHome}")
    private String SPARK_HOME;

    @Value("${appName}")
    private String APP_NAME;

    @Value("${appResource}")
    private String APP_RESOURCE;
    @Value("${mainClass}")
    private String MAIN_CLASS;
    @Value("${uploadAppName}")
    private String UPLOAD_APP_NAME;
    @Value("${uploadMainClass}")
    private String UPLOAD_MAIN_CLASS;

    /**
     * 通过消息作为参数启动spark任务
     *
     * @param msg
     * @throws IOException
     */
    public void submitSqlTask(String msg) throws IOException {
        SparkAppHandle handler = new SparkLauncher()
//                .setAppName("SerachJob")
//                //spark home 路径
//                .setSparkHome("/opt/cloudera/parcels/CDH-6.0.1-1.cdh6.0.1.p0.590678/lib/spark")
                .setAppName(APP_NAME)
                //spark home 路径
                .setSparkHome(SPARK_HOME)
                .setMaster("yarn")
                .setConf("spark.driver.memory", "1g")
                .setConf("spark.executor.memory", "4g")
                .setConf("spark.executor.cores", "4")
                .setConf("spark.num.executors", "2")
                //关闭动态分配
//                .setConf("spark.dynamicAllocation.enabled", "true")
                .setConf("spark.driver.extraJavaOptions", " -Dfile.encoding=utf-8")
                .setConf("spark.executor.extraJavaOptions", "-Dfile.encoding=utf-8")
                .setAppResource(APP_RESOURCE)
                .setMainClass(MAIN_CLASS)
                //参数
                .addAppArgs(replaceStr(msg))
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


    /**
     * 上传任务完成后调用spark程序清洗上传文件的数据
     * @param tableId
     * @throws IOException
     */
    public void uploadTask(Integer tableId) throws IOException {
        SparkAppHandle handler = new SparkLauncher()
//                .setAppName("SerachJob")
//                //spark home 路径
//                .setSparkHome("/opt/cloudera/parcels/CDH-6.0.1-1.cdh6.0.1.p0.590678/lib/spark")
                .setAppName(UPLOAD_APP_NAME)
                //spark home 路径
                .setSparkHome(SPARK_HOME)
                .setMaster("yarn")
                .setConf("spark.driver.memory", "2g")
                .setConf("spark.executor.memory", "4g")
                .setConf("spark.executor.cores", "4")
                .setConf("spark.num.executors", "2")
                //关闭动态分配
//                .setConf("spark.dynamicAllocation.enabled", "true")
                .setConf("spark.driver.extraJavaOptions", " -Dfile.encoding=utf-8")
                .setConf("spark.executor.extraJavaOptions", "-Dfile.encoding=utf-8")
                .setAppResource(APP_RESOURCE)
                .setMainClass(UPLOAD_MAIN_CLASS)
                //参数
                .addAppArgs(String.valueOf(tableId))
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
    /**
     * 解决spark任务提交最后三个}}}只有一个的情况
     * 问题记录： https://blog.csdn.net/u010814849/article/details/78752074
     *
     * @param arg
     * @return
     */
    private String replaceStr(String arg) {
        return arg.replaceAll("}}", "} }");
    }
}
