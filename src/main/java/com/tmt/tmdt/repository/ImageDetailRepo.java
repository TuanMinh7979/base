package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDetailRepo extends JpaRepository<Image, Long> {
}
