package com.tmt.tmdt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //    @Column(nullable = false, unique = true)
    //it not work when db is created first

    //use unique in db , not blank and check nameExist in controller
    @NotBlank
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    //auto-generate from name
    private String code;
}
