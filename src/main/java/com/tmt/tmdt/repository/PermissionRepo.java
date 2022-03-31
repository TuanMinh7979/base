package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Integer> {

    @Query("from  Permission  where parent.id = :parent ")
    List<Permission> getPermissionsByParent(@Param("parent") Integer parentId);
}
