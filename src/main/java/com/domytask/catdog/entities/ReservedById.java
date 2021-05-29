package com.domytask.catdog.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ReservedById {

    @Column
    private Long userId;

    @Column
    private Long taskId;
}
