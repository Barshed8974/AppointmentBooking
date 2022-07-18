package com.perennialsys.appointmentbooking.exception;

public class CustomException extends Exception{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
