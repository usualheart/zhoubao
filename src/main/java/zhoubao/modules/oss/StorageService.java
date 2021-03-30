package zhoubao.modules.oss;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public abstract class StorageService {
    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return        返回http地址
     */
    public abstract String upload(byte[] data, String path);


    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     * @param MultipartFile  前端上传过来的MultipartFile对象
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    public  abstract String upload (MultipartFile multipartFile, String path);

}
