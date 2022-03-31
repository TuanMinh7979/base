package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM roles WHERE name like %?1%",
            countQuery = "SELECT count(*) FROM roles WHERE name like %?1%",
            nativeQuery = true)
    Page getRolesByNameLike(String searchNameTerm, Pageable pageable);




    @Query("from Role where name= :name")
    Role getRoleByName(@Param("name") String name);
}