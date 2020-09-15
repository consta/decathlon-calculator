package com.kanstantin.decathlon;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
    public void testReadCSVEvents() {
        String eventsFilename = this.getClass().getClassLoader().getResource("events.csv").getFile();
        List<String[]> events = Utils.readCSV(eventsFilename, ";");
        assertTrue(events.size() == 10);
    }

    @Test
    public void testReadCSVResults() {
        String resultsFilename = this.getClass().getClassLoader().getResource("results.csv").getFile();
        List<String[]> results = Utils.readCSV(resultsFilename, ";");
        assertTrue(results.size() == 4);
    }

    @Test(expected = IllegalStateException.class)
    public void testReadCSVNullFilename() {
        List<String[]> results = Utils.readCSV(null, ";");
        assertTrue(results.size() == 4);
    }

    @Test(expected = IllegalStateException.class)
    public void testReadCSVWrongFilename() {
        List<String[]> results = Utils.readCSV(" ", ";");
        assertTrue(results.size() == 4);
    }

    @Test(expected = RuntimeException.class)
    public void testReadCSVMissingFile() {
        List<String[]> results = Utils.readCSV("abc", ";");
        assertTrue(results.size() == 4);
    }
}
