package com.domytask.catdog.services.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorMsgDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String error;
    private String message;
    @JsonIgnore(value = true)
    private String localization;
    private String path;
    private int status;

    public ErrorMsgDTO(String message) {
        super();
        this.message = message;
    }

    /**
     * @param error
     * @param message
     * @param localization
     */
    public ErrorMsgDTO(String error, String message, String localization) {
        super();
        this.error = error;
        this.message = message;
        this.localization = localization;
    }

}