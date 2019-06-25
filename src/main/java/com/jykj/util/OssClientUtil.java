package com.jykj.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class OssClientUtil {

    private static String aliyunEndpoint;
    private static String aliyunAccessKeyId;
    private static String aliyunAccessKeySecret;
    private static String aliyunBucketName;
    private static String firstKey;

    private static OSSClient ossClient = null;

    private OssClientUtil() { }

    public static OSSClient getInstance(){
        if (ossClient==null){
            ossClient = new OSSClient(aliyunEndpoint, aliyunAccessKeyId, aliyunAccessKeySecret);
        }
        return ossClient;
    }

    //得到bucket信息
    public static void getBucketInfo() {
        try {
            getInstance();
            if (ossClient.doesBucketExist(aliyunBucketName)) {
                log.info("您已经创建Bucket：" + aliyunBucketName + "。");
            }else {
                ossClient.createBucket(aliyunBucketName);
            }

            BucketInfo bucketInfo = ossClient.getBucketInfo(aliyunBucketName);
            log.info("Bucket " + aliyunBucketName + "的信息如下：");
            log.info("\t数据中心：" + bucketInfo.getBucket().getLocation());
            log.info("\t创建时间：" + bucketInfo.getBucket().getCreationDate());
            log.info("\t用户标志：" + bucketInfo.getBucket().getOwner());
        } catch (Exception e){
            log.error("操作异常");
        }
        System.out.println(ossClient);
    }

    //下载文件
    public static void downLoadFile(){
        try {
            getInstance();
            OSSObject ossObject = ossClient.getObject(aliyunBucketName, firstKey);
            InputStream inputStream = ossObject.getObjectContent();
            StringBuilder objectContent = new StringBuilder();
            BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String line = reader.readLine();
                if (line == null){
                    break;
                }
                objectContent.append(line);
            }
            inputStream.close();
        } catch (IOException e) {
            log.error("文件下载异常");
        }
    }

    //可查看文件信息
    public static void queryBucketInfo(){
        try {
            getInstance();
            ObjectListing objectListing = ossClient.listObjects(aliyunBucketName);
            List<OSSObjectSummary> objectSummaries= objectListing.getObjectSummaries();
            for (OSSObjectSummary ossObjectSummary: objectSummaries){
                log.info("/t" + GsonUtils.getJsonFromObject(ossObjectSummary));
            }
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    //上传文件、文件存储
    public static String uploadFile(MultipartFile file){
        String url = "";
        try {
            getInstance();
            String originalfileName = file.getOriginalFilename();
            String suffix = originalfileName.substring(originalfileName.lastIndexOf(".") + 1);
            String fileName = "img" + UUID.randomUUID().toString().replace("-", "") + "." + suffix;
            InputStream inputStream = file.getInputStream();

            OSSClient ossClient = OssClientUtil.getInstance();
            ossClient.putObject(aliyunBucketName, "img/" + fileName, inputStream);

            url = "http://"+ aliyunBucketName +"." + aliyunEndpoint +"/img/" + fileName;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return url;
    }

    //删除对象
    public static void deleteObject(String fileKey){
        try {
            //删除Object
            getInstance();
            ossClient.deleteObject(aliyunBucketName, firstKey);
            ossClient.deleteObject(aliyunBucketName, fileKey);
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    //删除存储空间
    public static void deleteBucket(){
        //删除存储空间
        try {
            getInstance();
            ossClient.deleteBucket(aliyunBucketName);
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Value("${aliyun.endpoint}")
    public void setAliyunEndpoint(String aliyunEndpoint) {
        OssClientUtil.aliyunEndpoint = aliyunEndpoint;
    }

    @Value("${aliyun.accessKeyId}")
    public void setAliyunAccessKeyId(String aliyunAccessKeyId) {
        OssClientUtil.aliyunAccessKeyId = aliyunAccessKeyId;
    }

    @Value("${aliyun.accessKeySecret}")
    public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
        OssClientUtil.aliyunAccessKeySecret = aliyunAccessKeySecret;
    }

    @Value("${aliyun.bucketName}")
    public void setAliyunBucketName(String aliyunBucketName) {
        OssClientUtil.aliyunBucketName = aliyunBucketName;
    }

    @Value("${firstKey}")
    public void setFirstKey(String firstKey) {
        OssClientUtil.firstKey = firstKey;
    }

}
