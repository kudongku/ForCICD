package com.six.cicd_setting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HiService {

    private final HiRepo hiRepo;


    public String createHi(String hiStr) {
        HiEntity hiEntity = HiEntity.builder().title(hiStr).content(hiStr).build();
        hiRepo.save(hiEntity);
        return hiStr;
    }
}
