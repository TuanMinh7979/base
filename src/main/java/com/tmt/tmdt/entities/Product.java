package com.tmt.tmdt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    private String shortDescription;


    private String fullDescription;

//    private boolean enable;

    private Float discountPercent;

    //other way fetchlazy + orphan +cacasde All bc: image complete depend on product,
    // all operate on product affect to image
    //(*) refactor to FetchType.Lazy
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Image> images = new HashSet<>();


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



}
