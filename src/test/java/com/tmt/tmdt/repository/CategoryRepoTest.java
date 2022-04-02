package com.tmt.tmdt.repository;

import com.tmt.tmdt.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;


    @Test
    public void addCategory() {
        Category category = new Category();
        category.setName("Not blank name");
        //must hard code , bec :generate code in service layer
        category.setCode("not-blank-name");
        Category savedCategory = categoryRepo.save(category);
        assertThat(savedCategory.getId() > 0);

    }
}