package com.kanstantin.decathlon;


import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void testStringIsNotBlank() {
        assertTrue(Utils.stringIsNotBlank("aaa"));
        assertTrue(Utils.stringIsNotBlank("a"));
        assertTrue(Utils.stringIsNotBlank("a "));
        assertTrue(Utils.stringIsNotBlank(" a"));
        assertTrue(Utils.stringIsNotBlank(" a  "));
        assertFalse(Utils.stringIsNotBlank(""));
        assertFalse(Utils.stringIsNotBlank(" "));
        assertFalse(Utils.stringIsNotBlank("    "));
        assertFalse(Utils.stringIsNotBlank(null));
    }

    @Test
    public void testParseNumeric() {
        assertEquals(Double.valueOf(1.12), Utils.parseNumeric("1.12").orElse(.0));
        assertEquals(Double.valueOf(0.000001), Utils.parseNumeric("0.000001").orElse(.0));
        assertFalse(Utils.parseNumeric("").isPresent());
        assertFalse(Utils.parseNumeric("aaa").isPresent());
        assertFalse(Utils.parseNumeric(null).isPresent());
        assertFalse(Utils.parseNumeric("1,12").isPresent());
    }

    @Test
    public void testReadCSVEventsAsFile() {
        List<String[]> events = Utils.readCSV("factors.csv", ",");
        assertEquals(10, events.size());
    }

    @Test
    public void testReadCSVEventsAsInputStream() {
        InputStream is = this.getClass().getResourceAsStream("/factors.csv");
        List<String[]> events = Utils.readCSV(is, ",");
        assertEquals(10, events.size());
    }

    @Test
    public void testReadCSVResultsAsFile() {
        List<String[]> results = Utils.readCSV("results.csv", ";");
        assertEquals(4, results.size());
    }

    @Test
    public void testReadCSVResultsAsInputStream() {
        InputStream is = this.getClass().getResourceAsStream("/results.csv");
        List<String[]> results = Utils.readCSV(is, ";");
        assertEquals(4, results.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testReadCSVWrongFilename() {
        List<String[]> results = Utils.readCSV(" ", ";");
        assertEquals(4, results.size());
    }

    @Test(expected = RuntimeException.class)
    public void testReadCSVMissingFile() {
        List<String[]> results = Utils.readCSV("abc", ";");
        assertEquals(4, results.size());
    }
}
