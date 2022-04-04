package com.tmt.tmdt.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "imagedetails")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ImageDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String link;
    private String publicId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
