package com.epam.lemon.record;

public class Record {

    private final byte[] data;

    public Record(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new String(data);
    }
}
