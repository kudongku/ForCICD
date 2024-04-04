package com.six.cicd_setting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HiService {

    private final HiRepo hiRepo;

    @Transactional(readOnly = true)
    public String getHi(String hiStr) {
        HiEntity hiEntity = hiRepo.findByTitle(hiStr)
            .orElseThrow(()-> new NullPointerException("없는 엔티티입니다."));
        return hiEntity.getContent();
    }

    public String createHi(String hiStr) {
        HiEntity hiEntity = HiEntity.builder().title(hiStr).content(hiStr).build();
        hiRepo.save(hiEntity);
        return hiStr;
    }
}
