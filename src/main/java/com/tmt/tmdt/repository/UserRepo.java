package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, String> {


    @Query("select u from UserEntity u left join fetch u.roles r left join fetch r.permissions where u.username= :username")

    //Vảy là ta không thể lấy role hay permisson trực tiếp từ user, do Lazy nên khi cần ta viết query riêng.
    Optional<UserEntity> findByUsernameWithPermission(@Param("username") String username);

    boolean existsByUsername(String username);
}
