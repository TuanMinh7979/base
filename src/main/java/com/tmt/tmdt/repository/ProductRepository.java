package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select name from Product where name like %:kw% ")
    List<String> getNamesByKw(@Param("kw") String kw);

    @Query("from Product where name like %:name% ")
    List<Product> getProductsByName(@Param("name") String name);


    @Query("from Product where category.id = :categoryId")
    List<Product> getProductsByCategory(@Param("categoryId") Long categoryId);


}
