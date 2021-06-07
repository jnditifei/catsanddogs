package com.domytask.catdog.entities;

import com.domytask.catdog.entities.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    @NotNull
    @Pattern(message = "L'email n'est pas valide", regexp = "[^@]+@[^@]+")
    private String email;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="reservedBy",
            joinColumns= @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    List<TaskEntity> reservedTask;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name= "walletId")
    @JsonManagedReference
    private WalletEntity wallet;

    public UserEntity(String userName, String email, String password, RoleEnum role, WalletEntity wallet) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

}
