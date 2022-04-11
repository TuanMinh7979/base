package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {


    @Query("select u from UserEntity u left join fetch u.roles r left join fetch r.permissions where u.username= :username")
    Optional<UserEntity> findByUsernameWithPermission(@Param("username") String username);

    boolean existsByUsername(String username);

    @Query(value = "select * from users where username like ?1%",
            countQuery = "select count(id) from users where username like ?1%",
            nativeQuery = true)
    Page<UserEntity> getProductsByUserName(String username, Pageable pageable);

    @Query(value = "select u.*, r.name from users u inner join user_role ur" +
            " on u.id = ur.user_id inner join roles r on ur.role_id = r.id and r.id= ?1"
            , countQuery = "select count(u.id) from users u inner join user_role ur" +
            " on u.id = ur.user_id inner join roles r on ur.role_id = r.id and r.id= ?1"
            , nativeQuery = true)
    Page<UserEntity> getUserEntitysByRole(Long roleId, Pageable pageable);
}
