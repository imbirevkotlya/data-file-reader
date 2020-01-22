package com.epam.lemon.record;

/**
 * The class represents the mainframe record data structure.
 */
public class Record {

    private final byte[] data;

    public Record(byte[] data) {
        this.data = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new String(data);
    }
}
