package com.domytask.catdog.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Value("${availableBalance:0.00}")
    private Float availableBalance;

    @Value("${onHold:0.00}")
    private Float onHold;

    @OneToOne(mappedBy = "wallet")
    @JsonBackReference
    private UserEntity owner;
}
