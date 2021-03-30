/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package zhoubao.modules.oss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 文件上传Factory
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public  class OSSFactory {
    @Autowired
    Environment env;// 用于读取配置中的文件

    @Bean
    public  StorageService build(){
        String ossType=env.getProperty("zhoubao.oss-type");
        if(ossType.equalsIgnoreCase("minio")){
            String endpoint=env.getProperty("zhoubao.minio-config.endpoint");
            String accessKey=env.getProperty("zhoubao.minio-config.access-key");
            String secretKey=env.getProperty("zhoubao.minio-config.secret-key");
            return new MinioStorageService(endpoint,accessKey,secretKey);
        }
        else{
            String location=env.getProperty("zhoubao.location");
            return new LocalStorageService(location);
        }

    }

}
