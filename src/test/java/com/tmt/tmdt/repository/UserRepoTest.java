package com.tmt.tmdt.repository;

import com.tmt.tmdt.constant.UserStatus;
import com.tmt.tmdt.entities.Role;
import com.tmt.tmdt.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepoTest {
    @Autowired
    private UserRepo userRepo;

    private static BCryptPasswordEncoder passwordEcorder = new BCryptPasswordEncoder(12);

    @Test
    public void encodea() {
//        UserEntity user = new UserEntity();
//        user.setUsername("abc");
//        user.setEmail("abcvavsa");
//        user.setPassword(passwordEcorder.encode("123"));
//        user.setStatus(UserStatus.ENABLE);
//
//
//        userRepo.save(user);

    }

//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Test
//    public void testCreateUser(){
////        Role admin =entityManager.find(Role.class, 1);
//        Role editor =entityManager.find(Role.class, 3);
//        UserEntity userAdmin= new UserEntity("tuaneditor3","123","tuanmaila13");
////        userAdmin.getRoles().add(admin);
//        userAdmin.getRoles().add(editor);
//
//        for (Role role : userAdmin.getRoles()) {
//            userAdmin.getRoleNameList().add(role.getName());
//        }
//        userRepo.save(userAdmin);
//    }
//
//    @Test
//    public void testCreateUserW2Role(){
//        Role admin =entityManager.find(Role.class, 1);
//        Role editor =entityManager.find(Role.class, 3);
//        UserEntity userAdmin= new UserEntity("tuanadeditor","123","tuanmail");
//        userAdmin.getRoles().add(admin);
//        userAdmin.getRoles().add(editor);
//        userRepo.save(userAdmin);
//    }


}