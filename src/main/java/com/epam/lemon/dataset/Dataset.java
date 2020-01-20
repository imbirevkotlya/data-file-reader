package com.epam.lemon.dataset;

import com.epam.lemon.record.Record;

import java.util.Iterator;

public interface Dataset extends Iterator<Record> {

    Integer getRecordsAmount();

}
