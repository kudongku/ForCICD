package com.six.cicd_setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiRepo extends JpaRepository<HiEntity, Long> {

}
