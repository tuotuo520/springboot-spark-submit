package com.taihe.springbootsparksubmit.util;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    static Configuration conf = null;


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
            String newPathStr = hdfsFilePath.toString() + "/" + uploadFilePath.toString().substring(uploadFilePath.toString().lastIndexOf("/"));
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
    public Boolean downloadHdfsFile(Path downloadFilePath, Path hdfsFilePath) throws IOException {
        fs = FileSystem.newInstance(URI.create("hdfs://192.168.0.241:8020/"), conf);
        fs.copyToLocalFile(hdfsFilePath, downloadFilePath);
        fs.close();
        return true;
    }


    /**
     * 下载hdfs
     *
     * @param dirPath
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
    public ByteArrayOutputStream downloadDirectory(String dirPath) throws IOException, InterruptedException, URISyntaxException {

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.1.100:9000"), conf, "hadoop");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(out);
        compress(dirPath, zos, fs);
        zos.close();
        return out;
    }

    /**
     * 压缩目录
     * @param dirPath
     * @param zipOutputStream
     * @param fs
     * @throws IOException
     */
    public void compress(String dirPath, ZipOutputStream zipOutputStream, FileSystem fs) throws IOException {
        // 要下载的目录dirPath
        FileStatus[] fileStatulist = fs.listStatus(new Path(dirPath));
        for (int i = 0; i < fileStatulist.length; i++) {
            String fileName = fileStatulist[i].getPath().getName();
            if (fileStatulist[i].isFile()) {
                Path path = fileStatulist[i].getPath();
                FSDataInputStream inputStream = fs.open(path);
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                IOUtils.copyBytes(inputStream, zipOutputStream, 1024);
                inputStream.close();
            } else {
                zipOutputStream.putNextEntry(new ZipEntry(fileName + "/"));//因为是目录，所以加上/ 就会自动分层
                // 这里的substring是因为这个返回的是hdfs://192.168.1.100:9000XXXXX/xxx/xx一大堆，
                // 而我们要的是/data/img类似这样的目录。
                compress(fileStatulist[i].getPath().toString().substring(25), zipOutputStream, fs);
            }
        }
    }
}
