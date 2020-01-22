package com.epam.lemon.dataset;

import com.epam.lemon.exception.RecordLengthException;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class QSAMDatasetTest {

    private static final Charset EBCDIC_CHARSET = Charset.forName("cp037");

    private static final String ASCII_FIRST_RECORD_VALUE = "99AAA";
    private static final String ASCII_SECOND_RECORD_VALUE = "98AAB";
    private static final String EBCDIC_FIRST_RECORD_VALUE = new String(new byte[] {57, 57, -63, -63, -63}, EBCDIC_CHARSET);
    private static final String EBCDIC_SECOND_RECORD_VALUE = new String(new byte[] {57, 56, -63, -63, -62}, EBCDIC_CHARSET);

    private static final int RECORD_LENGTH = 5;

    private static final String DEFAULT_ENCODING_TEST_FILE_PATH = "src/test/resources/ascii_TEST";
    private static final String WRONG_RECORDS_TEST_FILE_PATH = "src/test/resources/wrong_TEST";
    private static final String ASCII_TEST_FILE_PATH = "src/test/resources/ascii_TEST";
    private static final String EBCDIC_TEST_FILE_PATH = "src/test/resources/ebc_TEST";

    @Test
    public void iterateForRecords_defaultEncoding() throws IOException {
        QSAMDataset qsamDataset = createQSAMDatasetWithDefaultEncoding(DEFAULT_ENCODING_TEST_FILE_PATH);

        new DefaultEncodingRecordValueVerifier().verifyRecordValues(qsamDataset);
        verifyNoMoreRecords(qsamDataset);
    }

    private QSAMDataset createQSAMDatasetWithDefaultEncoding(String testFilePath) throws IOException {
        return new QSAMDataset(new FileInputStream(testFilePath).readAllBytes(), RECORD_LENGTH);
    }

    private void verifyNoMoreRecords(QSAMDataset qsamDataset) {
        Assert.assertFalse(qsamDataset.hasNext());
    }

    @Test
    public void iterateForRecords_asciiEncoding() throws IOException {
        QSAMDataset asciiDataset = createQSAMDatasetWithEncoding(ASCII_TEST_FILE_PATH, StandardCharsets.US_ASCII);

        new AsciiEncodingRecordValueVerifier().verifyRecordValues(asciiDataset);
        verifyNoMoreRecords(asciiDataset);
    }

    private QSAMDataset createQSAMDatasetWithEncoding(String testFilePath, Charset charset) throws IOException {
        return new QSAMDataset(new FileInputStream(testFilePath).readAllBytes(), RECORD_LENGTH, charset);
    }

    @Test
    public void iterateForRecords_ebcdicEncoding() throws IOException {
        QSAMDataset ebcdicDataset = createQSAMDatasetWithEncoding(EBCDIC_TEST_FILE_PATH, EBCDIC_CHARSET);

        new EbcdicEncodingRecordValueVerifier().verifyRecordValues(ebcdicDataset);
        verifyNoMoreRecords(ebcdicDataset);
    }

    @Test(expected = NoSuchElementException.class)
    public void iterateForRecordsAmount_illegalNextCall() throws IOException {
        QSAMDataset qsamDataset = createQSAMDatasetWithDefaultEncoding(DEFAULT_ENCODING_TEST_FILE_PATH);

        new DefaultEncodingRecordValueVerifier().verifyRecordValues(qsamDataset);
        tryGetNextRecord(qsamDataset);
    }

    private void tryGetNextRecord(QSAMDataset qsamDataset) throws NoSuchElementException {
        qsamDataset.next();
    }

    @Test(expected = RecordLengthException.class)
    public void iterateForRecordsAmount_wrongRecordFormat() throws IOException {
        createQSAMDatasetWithDefaultEncoding(WRONG_RECORDS_TEST_FILE_PATH);
    }

    private abstract static class AbstractRecordValueVerifier {

        void verifyRecordValues(QSAMDataset qsamDataset) {
            List<String> expectedRecordValues = expectedRecordValues();
            Assert.assertEquals(expectedRecordValues.size(), qsamDataset.getRecordsAmount().intValue());
            for (String expectedRecordValue : expectedRecordValues) {
                if (qsamDataset.hasNext()) {
                    Assert.assertEquals(expectedRecordValue, qsamDataset.next().toString());
                }
                else {
                    throw new AssertionError("Actual dataset has no record, but is should contains");
                }
            }
        }

        abstract List<String> expectedRecordValues();
    }

    private static class AsciiEncodingRecordValueVerifier extends AbstractRecordValueVerifier {

        @Override
        List<String> expectedRecordValues() {
            return new DefaultEncodingRecordValueVerifier().expectedRecordValues();
        }
    }

    private static class EbcdicEncodingRecordValueVerifier extends AbstractRecordValueVerifier {

        @Override
        List<String> expectedRecordValues() {
            ArrayList<String> expectedRecordValues = new ArrayList<>();
            expectedRecordValues.add(EBCDIC_FIRST_RECORD_VALUE);
            expectedRecordValues.add(EBCDIC_SECOND_RECORD_VALUE);
            return expectedRecordValues;
        }
    }

    private static class DefaultEncodingRecordValueVerifier extends AbstractRecordValueVerifier {

        @Override
        List<String> expectedRecordValues() {
            ArrayList<String> expectedRecordValues = new ArrayList<>();
            expectedRecordValues.add(ASCII_FIRST_RECORD_VALUE);
            expectedRecordValues.add(ASCII_SECOND_RECORD_VALUE);
            return expectedRecordValues;
        }
    }



}