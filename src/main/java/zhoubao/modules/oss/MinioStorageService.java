package zhoubao.modules.oss;

import io.minio.MinioClient;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioStorageService extends StorageService{
    MinioClient minioClient;
    public MinioStorageService(String endpoint, String accessKey, String secretKey)  {
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        try {
            minioClient = new MinioClient(endpoint, accessKey, secretKey);
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String upload(byte[] data, String path) {
        try {
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists("zhoubao");
            if(isExist) {
                System.out.println("Bucket zhoubao already exists.");
            } else {
                // 创建一个名为zhoubao的存储桶，用于存储周报文件。
                minioClient.makeBucket("zhoubao");
            }
            // 使用putObject上传一个文件到存储桶中。
            minioClient.putObject("zhoubao",path,new ByteArrayInputStream(data),data.length,"application/pdf");
            System.out.println(path+" is successfully uploaded  to `zhoubao` bucket.");
        } catch(MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException | XmlPullParserException e) {
            System.out.println("Error occurred: " + e);
        }
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        return null;
    }
    @Override
    public String upload(MultipartFile multipartFile, String path) {
        try {
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists("zhoubao");
            if(isExist) {
                System.out.println("Bucket zhoubao already exists.");
            } else {
                // 创建一个名为zhoubao的存储桶，用于存储周报文件。
                minioClient.makeBucket("zhoubao");
            }
            // 使用putObject上传一个文件到存储桶中。
            minioClient.putObject("zhoubao",path,multipartFile.getInputStream(),multipartFile.getSize(),multipartFile.getContentType());
            System.out.println(path+" 已经成功上传到 `zhoubao` bucket.");
        } catch(MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException | XmlPullParserException e) {
            System.out.println("Error occurred: " + e);
        }
        return null;
    }

    public static void main(String []args){
        //测试minio上传功能
        MinioStorageService minioStorageService=new MinioStorageService("http://172.16.1.100:9000","minioadmin","minioadmin");
        byte[] bytes=new byte[]{0,0,0,0,0,1,1,1};
        //minioStorageService.upload(bytes,"user/666/pdf/778-10699_081200_dddd.pdf");
        try {
            String url=minioStorageService.minioClient.getObjectUrl("zhoubao","778-10699_081200_2018202064_CCBG.pdf");
            System.out.println(url);
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        }

    }
}
