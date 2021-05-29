package com.domytask.catdog.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PetsEnum {

    @JsonProperty("Dog")
    DOG("Dog"),
    @JsonProperty("Cat")
    CAT("Cat");

    private String name;

    @JsonCreator
    private PetsEnum(String name) {
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

    public static PetsEnum fromValue(final JsonNode jsonNode) {

        for (PetsEnum type : PetsEnum.values()) {
            if (type.name.equals(jsonNode.get("name").asText())) {
                return type;
            }
        }
        return null;
    }
}
