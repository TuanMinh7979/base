package com.tmt.tmdt.entities;

import com.tmt.tmdt.constant.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity implements Serializable {
    @Id
    private Long id;

    private String username;

    private String password;

    private Integer balance;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    // @JsonIgnore

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void redBalance(Integer amount) {
        this.balance -= amount;

    }


}