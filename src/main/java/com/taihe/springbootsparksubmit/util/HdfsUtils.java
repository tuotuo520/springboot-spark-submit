package com.taihe.springbootsparksubmit.util;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author : Grayson
 * @Email : sunhui@yunliketech.com
 * @Date ：2020/4/15
 */
@CommonsLog
@org.springframework.context.annotation.Configuration
public class HdfsUtils {
    static FileSystem fs = null;
    static Configuration conf;


    static {
        //Hadoop类库中提供的HDFS操作的核心API是FileSystem抽象类，该类提供了一系列方法来进行相关的操作
        //FileSystem （不能生成对象）是一个通用的文件系统API，是一个抽象类，该类提供了一系列方法来进行相关的操作
        //configuration对象是对文件系统中属性信息的封装，默认的属性来自hadoop配置文件core-site.xml中的配置
        //进行文件操作的基本流程：1.创建configuration对象 2.利用fileSystem的静态get方法获取FileSystem实例 3.调用FileSystem的方法进行实际的文件操作
        conf = new Configuration();
        //设置本程序所访问的HDFS服务的地址，如果不通过代码进行设置，会默认取Hadoop安装环境下core-site.xml文件中配置的fs.defaultFS的属性值
        conf.set("dfs.support.append", "true");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
        conf.setBoolean("fs.hdfs.impl.disable.cache", true);

    }

    /**
     * 上传文件到指定HDFS目录
     *
     * @param uploadFilePath
     * @param hdfsFilePath
     */
    public void uploadFileToHdfs(Path uploadFilePath, Path hdfsFilePath) throws IOException {
        fs = FileSystem.newInstance(URI.create("hdfs://192.168.0.241:8020/"), conf);
        if (!fs.exists(hdfsFilePath)) {
            log.info("---------------创建新文件夹：" + hdfsFilePath);
            fs.mkdirs(hdfsFilePath);
        }
        fs.copyFromLocalFile(uploadFilePath, hdfsFilePath);
        if (uploadFilePath.toString().contains(".txt")) {
            String newPathStr = hdfsFilePath.toString() + "/" + uploadFilePath.toString().substring(uploadFilePath.toString().lastIndexOf("/")+1);
            Path oldPath = new Path(newPathStr);
            Path newPath = new Path(newPathStr.replace(".txt", ".csv"));
            fs.rename(oldPath, newPath);
        }
        fs.close();
    }


    /**
     * 上传文件到指定HDFS目录
     *
     * @param uploadFilePath
     * @param hdfsFilePath
     */
    public void downloadHdfsFile(Path downloadFilePath, Path hdfsFilePath) throws IOException {
        fs = FileSystem.newInstance(URI.create("hdfs://192.168.0.241:8020/"), conf);
        FileStatus[] status = fs.listStatus(hdfsFilePath);
        String path = hdfsFilePath.toString().substring(hdfsFilePath.toString().lastIndexOf("/"));
        File file = new File(downloadFilePath.toString() + path);
        if (!file.exists()) {
            fs.copyToLocalFile(hdfsFilePath, downloadFilePath);
        }
        fs.close();
    }


}
