package com.domytask.catdog.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;

public enum TaskStageEnum {

    ONE("Stage 1"), TWO("Stage 2"), THREE("Stage 3");

    private String name;

    private TaskStageEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return the name
     */
    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static TaskStageEnum fromValue(final JsonNode jsonNode) {

        for (TaskStageEnum type : TaskStageEnum.values()) {
            if (type.name.equals(jsonNode.get("name").asText())) {
                return type;
            }
        }
        return null;
    }
}
