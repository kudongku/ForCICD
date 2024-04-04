package com.six.cicd_setting;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiRepo extends JpaRepository<HiEntity, Long> {

    Optional<HiEntity> findByTitle(String title);
}
