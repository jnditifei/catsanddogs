package com.domytask.catdog.entities;

import com.domytask.catdog.entities.enums.PetsEnum;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @NotNull
    private Boolean available;

    @Enumerated(EnumType.STRING)
    private PetsEnum answer;

    @NotNull
    private String imageURL;

    @NotNull
    private Float reward;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Boolean review;

    private String reviewComment;

    @Enumerated(EnumType.STRING)
    private TaskStageEnum taskStage;

    @ManyToMany(mappedBy = "reservedTask", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<UserEntity> taskDoers;

    public TaskEntity(Boolean available, String imageURl, Float reward, StatusEnum status) {
        this.available = available;
        this.imageURL = imageURl;
        this.reward = reward;
        this.status = status;
    }
}
