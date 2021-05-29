package com.domytask.catdog.services.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

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

    /**
     *
     */
    public ErrorMsgDTO() {
        super();
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error
     *            the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the localization
     */
    public String getLocalization() {
        return localization;
    }

    /**
     * @param localization
     *            the localization to set
     */
    public void setLocalization(String localization) {
        this.localization = localization;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

}