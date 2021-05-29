package com.domytask.catdog.entities;

import javax.persistence.*;

public class ReservedBy {

    @EmbeddedId
    private ReservedById id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "taskId")
    private TaskEntity taskEntity;
}
