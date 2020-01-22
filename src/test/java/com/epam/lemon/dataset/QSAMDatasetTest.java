package com.epam.lemon.dataset;

import com.epam.lemon.exception.RecordLengthException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

public class QSAMDatasetTest {

    private static final String FIRST_RECORD_VALUE = "99AAA";
    private static final String SECOND_RECORD_VALUE = "98AAB";

    private static final int RECORDS_AMOUNT = 2;
    private static final int RECORD_LENGTH = 5;

    private static final String TEST_FILE_PATH = "src/test/resources/TEST";
    private static final String WRONG_RECORDS_TEST_FILE_PATH = "src/test/resources/wrong_TEST";

    @Test
    public void iterateForRecordsAmount() throws IOException {
        QSAMDataset qsamDataset = createQSAMDataset(TEST_FILE_PATH);

        assertDatasetRecordValues(qsamDataset);
        verifyNoMoreRecords(qsamDataset);
    }

    private QSAMDataset createQSAMDataset(String s) throws IOException {
        return new QSAMDataset(Files.readAllBytes(Path.of(s)), RECORD_LENGTH);
    }

    private void assertDatasetRecordValues(QSAMDataset actualDataset) {
        Integer recordsAmount = actualDataset.getRecordsAmount();
        Assert.assertEquals(RECORDS_AMOUNT, recordsAmount.intValue());
        Assert.assertEquals(FIRST_RECORD_VALUE, actualDataset.next().toString());
        Assert.assertEquals(SECOND_RECORD_VALUE, actualDataset.next().toString());
    }

    private void verifyNoMoreRecords(QSAMDataset qsamDataset) {
        Assert.assertFalse(qsamDataset.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void iterateForRecordsAmount_illegalNextCall() throws IOException {
        QSAMDataset qsamDataset = createQSAMDataset(TEST_FILE_PATH);

        assertDatasetRecordValues(qsamDataset);
        tryGetNextRecord(qsamDataset);
    }

    private void tryGetNextRecord(QSAMDataset qsamDataset) throws NoSuchElementException {
        qsamDataset.next();
    }

    @Test(expected = RecordLengthException.class)
    public void iterateForRecordsAmount_wrongRecordFormat() throws IOException {
        createQSAMDataset(WRONG_RECORDS_TEST_FILE_PATH);
    }
}