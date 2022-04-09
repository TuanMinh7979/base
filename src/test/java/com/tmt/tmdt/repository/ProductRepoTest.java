package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Product;
import com.tmt.tmdt.entities.Detail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ProductRepoTest {
    @Autowired
    private ProductRepo repo;

    @Autowired
    private DetailRepo detailRepo;

    @Test
    public void addProductDetail() {
        Product p = repo.findById(89L).get();
        //a
        Detail detail = new Detail("memmory", "128");
        p.addDetail(detail);
        repo.save(p);
    }

    @Test
    public void removeProductDetail() {
        Product p = repo.findById(89L).get();
        //a
        Detail detail = detailRepo.findById(1L).get();
        p.removeDetail(detail);
        repo.save(p);
    }

}