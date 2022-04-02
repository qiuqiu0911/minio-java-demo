package cn.qiudev.demo.minio.util;

import cn.qiudev.demo.minio.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author qiuqiu
 */
@Component
@Slf4j
public class MinioUtil {

    public static MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    @PostConstruct
    public void init() {
        log.info("开始初始化");
        try {
            minioClient = MinioClient.builder().endpoint(minioConfig.getUrl())
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey()).build();
            if (!bucketExists(minioConfig.getBucketName())) {
                this.createBucket(minioConfig.getBucketName());
            }
        } catch (Exception e) {
            log.error("初始化失败");
            throw e;
        }
    }

    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    @SneakyThrows
    public void createBucket(String bucketName) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
    }

    @SneakyThrows
    public String uploadFile(String bucketName, String fileName, InputStream inputStream) {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName)
                .object(fileName)
                .stream(inputStream, inputStream.available(), -1).build());
        return getFileUrl(bucketName, fileName);
    }

    @SneakyThrows
    public String getFileUrl(String bucketName, String fileName) {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .method(Method.GET)
                .object(fileName).build());
    }
}
