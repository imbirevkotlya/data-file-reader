package com.epam.lemon.dataset;

import com.epam.lemon.record.Record;

import java.util.Iterator;

/**
 * The interface represents the mainframe dataset object, which can be iterated to access the certain record in it.
 */
public interface Dataset extends Iterator<Record> {

    /**
     * The method to count the all dataset records number, including already accessed records and still ton accessed too.
     * @return the number of the records in the dataset
     */
    Integer getRecordsAmount();

}
