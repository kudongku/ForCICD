package com.six.cicd_setting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class HiController {

    @GetMapping("/helloWorld/{hiStr}")
    @ResponseBody
    public String getHi(
        @PathVariable String hiStr
    ) {
        return "hello "+hiStr;
    }

}