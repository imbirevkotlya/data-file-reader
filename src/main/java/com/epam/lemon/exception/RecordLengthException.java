package com.epam.lemon.exception;

public class RecordLengthException extends RuntimeException {
    public RecordLengthException(Integer expectedLength) {
        super("Record length is incorrect: should be " + expectedLength);
    }
}
