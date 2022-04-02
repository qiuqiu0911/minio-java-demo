package cn.qiudev.demo.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiuqiu
 */
@ConfigurationProperties(prefix = "minio")
@Data
@Configuration
public class MinioConfig {

    /**
     * 访问地址
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 文件夹
     */
    private String bucketName;
}
