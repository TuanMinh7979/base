package com.tmt.tmdt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
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

    @NotBlank
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    //auto-generate from name
    private String code;



    @JsonIgnore
    @OneToOne
    @JoinColumn(name="parent_id")
    private Category parent;
    //one to one one one to many
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<Category> children = new HashSet<>();

    public Category(String name) {
        this.name = name;
    }
    public Category(String name, Category parent) {
        this.parent= parent;
        this.name = name;
    }



    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    

    public Category(Integer id) {
        this.id = id;
    }
}
