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
    public void testReadCSVEventsAsInputStream() {
        InputStream is = this.getClass().getResourceAsStream("/factors.csv");
        List<String[]> events = Utils.readCSV(is, ",");
        assertEquals(10, events.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testReadCSVWrongFilename() {
        List<String[]> results = Utils.readCSV(" ", ";");
        assertEquals(5, results.size());
    }

    @Test(expected = RuntimeException.class)
    public void testReadCSVMissingFile() {
        List<String[]> results = Utils.readCSV("abc", ";");
        assertEquals(5, results.size());
    }

    @Test
    public void testRanking() {
        assertEquals("1", Utils.ranking(1, 1));
        assertEquals("1-2", Utils.ranking(1, 2));
        assertEquals("3-4", Utils.ranking(3, 2));
        assertEquals("3-4-5", Utils.ranking(3, 3));
        assertEquals("3-4-5-6", Utils.ranking(3, 4));
        assertEquals("?", Utils.ranking(0, 4));
        assertEquals("?", Utils.ranking(1, 0));
        assertEquals("?", Utils.ranking(0, 0));
    }
}
