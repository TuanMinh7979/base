package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    @Query("select name from Category where name like %:name% ")
    List<String> getCategoryNamesByKw(@Param("name") String name);

    Category findByName(String name);

    @Query(value = "SELECT * FROM categories WHERE name like %?1%",
            countQuery = "SELECT count(*) FROM categories WHERE name like %?1%",
            nativeQuery = true)

    Page<Category> getCategoriesByNameLike( String name, Pageable pageable);


}
