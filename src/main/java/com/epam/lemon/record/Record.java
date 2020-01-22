package com.epam.lemon.record;

import java.nio.charset.Charset;

/**
 * The class represents the mainframe record data structure.
 */
public class Record {

    private final byte[] data;
    private final Charset recordEncoding;

    public Record(byte[] data, Charset recordEncoding) {
        this.data = data;
        this.recordEncoding = recordEncoding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new String(data, recordEncoding);
    }
}
