package com.domytask.catdog.services.exceptions;

public class InvalidEntityToPersistException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final ErrorMsgDTO errorDto;

    /**
     * @param error
     * @param message
     * @param localization
     */
    public InvalidEntityToPersistException(String error, String message, String localization) {
        super();
        this.errorDto = new ErrorMsgDTO(error, message, localization);
    }

    /**
     * @return errorDto
     */
    public ErrorMsgDTO getErrorDto() {
        return errorDto;
    }

}
