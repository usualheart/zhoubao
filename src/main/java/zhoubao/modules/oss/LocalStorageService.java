package zhoubao.modules.oss;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import zhoubao.controller.WeekNo;
import zhoubao.domain.ZhouBao;

import java.io.*;
import java.util.Date;

public class LocalStorageService extends StorageService{
    String location;
    public LocalStorageService(String location){
        this.location=location;
    }
    @Override
    public String upload(byte[] data, String path) {
        File file  = new File(location+ path);
        file.getParentFile().mkdirs();// 创建文件目录
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        return null;
    }

    @Override
    public String upload(MultipartFile multipartFile, String path) {
        File file  = new File(location+ path);
        file.getParentFile().mkdirs();// 创建文件目录
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
