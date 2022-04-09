package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepo extends JpaRepository<Detail, Long> {

}
