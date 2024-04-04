package com.six.cicd_setting;

import com.six.cicd_setting.constant.RedisService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class HiController {

    private final HiService hiService;
    private final S3Service s3Service;
    private final RedisService redisService;

    @GetMapping("/helloWorld/{hiStr}")
    public String createHi(
        @PathVariable String hiStr
    ) {
        return hiService.createHi(hiStr);
    }

    @GetMapping("/byeWorld/{hiStr}")
    public String getHi(
        @PathVariable String hiStr
    ) {
        return hiService.getHi(hiStr);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }

    @GetMapping("/redis/{testKey}/{testValue}")
    public String redisTest(
        @PathVariable String testKey,
        @PathVariable String testValue
    ) {
        return redisService.redisString(testKey, testValue);
    }

    @PostMapping("/s3")
    public String s3FileUpload(
        MultipartFile image
    ) throws IOException {
        return s3Service.upload(image, "image-test");
    }

}
