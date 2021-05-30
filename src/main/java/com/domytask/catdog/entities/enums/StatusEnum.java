package com.domytask.catdog.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEnum {

    @JsonProperty("To do")
    TODO("To do"),
    @JsonProperty("Hold")
    HOLD("Hold"),
    @JsonProperty("Release")
    RELEASE("Release"),
    @JsonProperty("Fail")
    FAIL("Fail");

    private String name;

    @JsonCreator
    private StatusEnum(String name) {
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

    public static StatusEnum fromValue(final JsonNode jsonNode) {

        for (StatusEnum type : StatusEnum.values()) {
            if (type.name.equals(jsonNode.get("name").asText())) {
                return type;
            }
        }
        return null;
    }
}
