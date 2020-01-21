package com.epam.lemon.dataset;

import com.epam.lemon.exception.RecordLengthException;
import com.epam.lemon.record.Record;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The class represents the mainframe QSAM dataset, where the records iteration is sequential
 */
public class QSAMDataset implements Dataset {

    private final List<Record> records;
    private int recordCursor;

    public QSAMDataset(byte[] dataFile, Integer recordLength) {
        records = new ArrayList<>();
        recordCursor = 0;
        fillRecordsList(dataFile, recordLength);
    }

    private void fillRecordsList(byte[] dataFile, Integer recordLength) {
        ByteArrayInputStream dataFileInputStream = new ByteArrayInputStream(dataFile);
        Integer recordsAmount = determineRecordsAmount(recordLength, dataFileInputStream);
        for (int i = 0; i < recordsAmount; i++) {
            byte[] buffer = new byte[recordLength];
            int startPosition = 0;
            dataFileInputStream.readNBytes(buffer, startPosition, recordLength);
            records.add(new Record(buffer));
        }
    }

    private Integer determineRecordsAmount(Integer recordLength, ByteArrayInputStream dataFileInputStream) {
        int availableBytesAmount = dataFileInputStream.available();
        if (availableBytesAmount % recordLength != 0) {
            throw new RecordLengthException(recordLength);
        }
        return availableBytesAmount / recordLength;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getRecordsAmount() {
        return records.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return records.size() > recordCursor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Record next() {
        if (hasNext()) {
            return records.get(recordCursor++);
        }
        throw new NoSuchElementException();
    }
}
