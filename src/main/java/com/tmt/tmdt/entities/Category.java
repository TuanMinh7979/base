package com.tmt.tmdt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter

public class Category extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String name;

    //without JsonIngnore->  not query but want render
//    @JsonIgnore
    //Nếu dùng EAGER thì json sẽ có luôn cả list product(trong TH bên product cũng là EAGER thì sẽ bị loop khi render json)
    //nếu không dùng thì mặc định json sẽ không có product( trong app ta có thể dùng getProduct() để lấy ra kể cả trong thymeleaf
    // file (//ngc lại trong hibernate sẽ bị failed to lazily initialize a collection of role” Hibernate exception) do hibernate
    // đã đóng session)
    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    private String code;
}
