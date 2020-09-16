package com.kanstantin.decathlon;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

    @Test
    public void testResultParsing() {
        assertEquals(1.12,   Application.parseEventPerformance("1.12"),     0.0001);
        assertEquals(0.00,   Application.parseEventPerformance("a1.12"),    0.0001);
        assertEquals(1.12,   Application.parseEventPerformance("0:01.12"),  0.0001);
        assertEquals(0.12,   Application.parseEventPerformance("0:00.12"),  0.0001);
        assertEquals(345.79, Application.parseEventPerformance("5:45.79"),  0.0001);
    }
}
