package com.epam.lemon.exception;

/**
 * The exception class for situations, where the record length of the dataset is invalid.
 */
public class RecordLengthException extends RuntimeException {

    /**
     * Main exception constructor.
     * The following message, which will be generated will show the expected record length
     * @param expectedLength the expected length of the record
     */
    public RecordLengthException(Integer expectedLength) {
        super("Record length is incorrect: should be " + expectedLength);
    }
}
