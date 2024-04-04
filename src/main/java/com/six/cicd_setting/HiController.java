package com.six.cicd_setting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HiController {

    private final HiService hiService;

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
    @ResponseBody
    public String healthCheck() {
        return "ok";
    }

}
