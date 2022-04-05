package com.tmt.tmdt.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private BigDecimal price;
    private String mainImageLink;
    private String description;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
//    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Test> tests = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String code;

//    @Transient
//    private MultipartFile file;
//
//    @Transient
//    private MultipartFile[] files;

    @Transient
    public String defaultImage() {
        return "/resource/img/default.png";
    }


    public void addImage(Image img) {
        this.images.add(img);
        img.setProduct(this);
    }


    public void removeImage(Image img) {
        this.images.remove(img);
    }

    public void removeTest(Test img) {
//        img.setProduct(null);
        this.tests.remove(img);
    }


}
