package com.kanstantin.decathlon;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplicationTest {

    @Test
    public void testResultParsing() {
        assertEquals(1.12,   Application.parseEventPerformance("1.12"),     0.0001);
        assertEquals(0.00,   Application.parseEventPerformance("a1.12"),    0.0001);
        assertEquals(1.12,   Application.parseEventPerformance("0:01.12"),  0.0001);
        assertEquals(0.12,   Application.parseEventPerformance("0:00.12"),  0.0001);
        assertEquals(345.79, Application.parseEventPerformance("5:45.79"),  0.0001);
    }

    @Test
    public void testCalculateScore() {
        assertEquals(185, Application.calculateEventScore(0, 15.0));
        assertEquals(206, Application.calculateEventScore(1, 4.0));
        assertEquals(486, Application.calculateEventScore(2, 10.0));
        assertEquals(464, Application.calculateEventScore(3, 1.6));
        assertEquals(259, Application.calculateEventScore(4, 65.0));
        assertEquals(208, Application.calculateEventScore(5, 35.0));
        assertEquals(253, Application.calculateEventScore(6, 19.0));
        assertEquals(357, Application.calculateEventScore(7, 3.0));
        assertEquals(342, Application.calculateEventScore(8, 33.0));
        assertEquals(306, Application.calculateEventScore(9, 350.0));
    }
}
