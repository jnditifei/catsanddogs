package com.domytask.catdog.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleEnum {

    @JsonProperty("Entry Level")
    ENTRY("Entry Level"),
    @JsonProperty("Moderator")
    MODERATOR("Moderator");

    private String name;

    @JsonCreator
    private RoleEnum(String name) {
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

    public static RoleEnum fromValue(final JsonNode jsonNode) {

        for (RoleEnum type : RoleEnum.values()) {
            if (type.name.equals(jsonNode.get("name").asText())) {
                return type;
            }
        }
        return null;
    }


}
