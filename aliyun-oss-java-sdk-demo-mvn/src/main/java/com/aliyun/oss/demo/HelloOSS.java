/**
 * 示例说明
 * 
 * HelloOSS是OSS Java SDK的示例程序，您可以修改endpoint、accessKeyId、accessKeySecret、bucketName后直接运行。
 * 运行方法请参考README。
 * 
 * 本示例中的并不包括OSS Java SDK的所有功能，详细功能及使用方法，请参看“SDK手册 > Java-SDK”，
 * 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/preface.html?spm=5176.docoss/sdk/java-sdk/。
 * 
 * 调用OSS Java SDK的方法时，抛出异常表示有错误发生；没有抛出异常表示成功执行。
 * 当错误发生时，OSS Java SDK的方法会抛出异常，异常中包括错误码、错误信息，详细请参看“SDK手册 > Java-SDK > 异常处理”，
 * 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/exception.html?spm=5176.docoss/api-reference/error-response。
 * 
 * OSS控制台可以直观的看到您调用OSS Java SDK的结果，OSS控制台地址是：https://oss.console.aliyun.com/index#/。
 * OSS控制台使用方法请参看文档中心的“控制台用户指南”， 指南的来链接地址是：https://help.aliyun.com/document_detail/oss/getting-started/get-started.html?spm=5176.docoss/user_guide。
 * 
 * OSS的文档中心地址是：https://help.aliyun.com/document_detail/oss/user_guide/overview.html。
 * OSS Java SDK的文档地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/install.html?spm=5176.docoss/sdk/java-sdk。
 * 
 */

package com.aliyun.oss.demo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class HelloOSS {
    static Logger logger = Logger.getLogger(HelloOSS.class);

    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    private static String accessKeyId = "LTAI4GAXo5FfM3a1CHvuNnhv";
    private static String accessKeySecret = "2Z5LJ7BR9k7m81go6ym5aK0uGs1Z7f";

    // Bucket用来管理所存储Object的存储空间，详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Bucket命名规范如下：只能包括小写字母，数字和短横线（-），必须以小写字母或者数字开头，长度必须在3-63字节之间。
    private static String bucketName = "italy";

    // Object是OSS存储数据的基本单元，称为OSS的对象，也被称为OSS的文件。详细描述请参看“开发人员指南 > 基本概念 > OSS基本概念介绍”。
    // Object命名规范如下：使用UTF-8编码，长度必须在1-1023字节之间，不能以“/”或者“\”字符开头。
    private static String firstKey = "my-first-key";

    public static void main(String[] args) {

        // 日志配置，OSS Java SDK使用log4j记录错误信息。示例程序会在工程目录下生成“oss-demo.log”日志文件，默认日志级别是INFO。
        // 日志的配置文件是“conf/log4j.properties”，如果您不需要日志，可以没有日志配置文件和下面的日志配置。
        PropertyConfigurator.configure("conf/log4j.properties");

        logger.info("Started");

        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {

            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }

            // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            BucketInfo info = ossClient.getBucketInfo(bucketName);

            System.out.println("Bucket " + bucketName + "的信息如下："+info.getBucket().getIntranetEndpoint());
            System.out.println("\t数据中心：" + info.getBucket().getLocation());
            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
            System.out.println("\t用户标志：" + info.getBucket().getOwner());

            // 把字符串存入OSS，Object的名称为firstKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
//            InputStream is = new ByteArrayInputStream("Hello OSS".getBytes());
//            ossClient.putObject(bucketName, firstKey, is);
//            System.out.println("Object：" + firstKey + "存入OSS成功。");

            // 下载文件。详细请参看“SDK手册 > Java-SDK > 下载文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/download_object.html?spm=5176.docoss/sdk/java-sdk/manage_object
//            OSSObject ossObject = ossClient.getObject(bucketName, firstKey);
//            InputStream inputStream = ossObject.getObjectContent();
//            StringBuilder objectContent = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            while (true) {
//                String line = reader.readLine();
//                if (line == null)
//                    break;
//                objectContent.append(line);
//            }
//            inputStream.close();
//            System.out.println("Object：" + firstKey + "的内容是：" + objectContent);

            // 文件存储入OSS，Object的名称为fileKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            String fileKey = "italy1.jpg";
            // 创建上传文件的元信息，可以通过文件元信息设置HTTP header。
            ObjectMetadata meta = new ObjectMetadata();

//            String md5 = BinaryUtil.toBase64String(BinaryUtil.calculateMd5(content.getBytes()));
            // 开启文件内容MD5校验。开启后OSS会把您提供的MD5与文件的MD5比较，不一致则抛出异常。
//            meta.setContentMD5(md5);
            // 指定上传的内容类型。内容类型决定浏览器将以什么形式、什么编码读取文件。如果没有指定则根据文件的扩展名生成，如果没有扩展名则为默认值application/octet-stream。
            meta.setContentType("text/plain");
            // 设置内容被下载时的名称。
            meta.setContentDisposition("attachment; filename=\"DownloadFilename\"");
            // 设置上传文件的长度。如超过此长度，则会被截断，为设置的长度。如不足，则为上传文件的实际长度。
//            meta.setContentLength(content.length());
            // 设置内容被下载时网页的缓存行为。
            meta.setCacheControl("Download Action");
            // 设置缓存过期时间，格式是格林威治时间（GMT）。
            meta.setExpirationTime(DateUtil.parseIso8601Date("2022-10-12T00:00:00.000Z"));
            // 设置内容被下载时的编码格式。
            meta.setContentEncoding("utf-8");
            // 设置header。
//            meta.setHeader("<yourHeader>", "<yourHeaderValue>");
            ossClient.putObject(bucketName, fileKey, new File("italy.jpg"));
//            ossClient.putObject(bucketName, fileKey, new File("italy.jpg"), meta);
            System.out.println("Object：" + fileKey + "存入OSS成功。");

            // 查看Bucket中的Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
            System.out.println("您有以下Object：");
            for (OSSObjectSummary object : objectSummary) {
                System.out.println("\t" + object.getKey());
            }

            // 删除Object。详细请参看“SDK手册 > Java-SDK > 管理文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_object.html?spm=5176.docoss/sdk/java-sdk/manage_bucket
//            ossClient.deleteObject(bucketName, firstKey);
//            System.out.println("删除Object：" + firstKey + "成功。");
//            ossClient.deleteObject(bucketName, fileKey);
//            System.out.println("删除Object：" + fileKey + "成功。");

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        logger.info("Completed");
    }

}
