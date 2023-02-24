package cn.qiudev.demo.minio.controller;

import cn.qiudev.demo.minio.util.MinioUtil;
import io.swagger.annotations.Api;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 测试入口
 *
 * @author qiuqiu
 */
@Api("demo")
@RequestMapping("demo")
@RestController
public class DemoController {

    @Resource
    private MinioUtil minioUtil;

    @PostMapping("upload")
    public String upload(MultipartFile file) throws Exception {
        return minioUtil.uploadFile("demo", file.getOriginalFilename(), file.getInputStream());
    }
    
    @PostMapping("test")
    public String test() throws Exception {
        return "hello";
    }
}
