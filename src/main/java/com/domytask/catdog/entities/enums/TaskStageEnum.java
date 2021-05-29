package com.domytask.catdog.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;

public enum TaskStageEnum {

    @JsonProperty("Stage 1")
    ONE("Stage 1"),
    @JsonProperty("Stage 2")
    TWO("Stage 2"),
    @JsonProperty("Stage 3")
    THREE("Stage 3");

    private String name;

    @JsonCreator
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

    public static TaskStageEnum fromValue(final JsonNode jsonNode) {

        for (TaskStageEnum type : TaskStageEnum.values()) {
            if (type.name.equals(jsonNode.get("name").asText())) {
                return type;
            }
        }
        return null;
    }
}
