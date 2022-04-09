package com.tmt.tmdt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DeadlockLoserDataAccessException;

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

    @Lob
    private String shortDescription;

    @Lob
    private String fullDescription;

//    private boolean enable;

    private Float discountPercent;

    //other way fetchlazy + orphan +cacasde All bc: image complete depend on product,
    // all operate on product affect to image
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<Image> images = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
                    //not consist  delete
    })
    @JoinTable(name = "product_detail", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "detail_id"))
    private Set<Detail> details = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String code;


    @Transient
    public String defaultImage() {
        return "/resource/img/default.png";
    }

    //image is special case bc can not view the old file in update form now so can not use CascadeType.Persist
    //and do not use helper method

    public void addDetail(Detail detail) {
        details.add(detail);
        detail.getProduct().add(this);
    }

    public void removeDetail(Detail detail) {
        details.remove(detail);
        detail.getProduct().remove(this);
    }

}
