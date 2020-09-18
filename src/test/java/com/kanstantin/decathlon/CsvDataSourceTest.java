package com.kanstantin.decathlon;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CsvDataSourceTest {
    @Test
    public void testResultParsing() {
        assertEquals(1.12,   CsvDataSource.parseEventPerformance("1.12"),     0.0001);
        assertEquals(0.00,   CsvDataSource.parseEventPerformance("a1.12"),    0.0001);
        assertEquals(1.12,   CsvDataSource.parseEventPerformance("0:01.12"),  0.0001);
        assertEquals(0.12,   CsvDataSource.parseEventPerformance("0:00.12"),  0.0001);
        assertEquals(345.79, CsvDataSource.parseEventPerformance("5:45.79"),  0.0001);
    }

    @Test
    public void testCalculateScore() {
        assertEquals(185, CsvDataSource.calculateEventScore(0, 15.0));
        assertEquals(206, CsvDataSource.calculateEventScore(1, 4.0));
        assertEquals(486, CsvDataSource.calculateEventScore(2, 10.0));
        assertEquals(464, CsvDataSource.calculateEventScore(3, 1.6));
        assertEquals(259, CsvDataSource.calculateEventScore(4, 65.0));
        assertEquals(208, CsvDataSource.calculateEventScore(5, 35.0));
        assertEquals(253, CsvDataSource.calculateEventScore(6, 19.0));
        assertEquals(357, CsvDataSource.calculateEventScore(7, 3.0));
        assertEquals(342, CsvDataSource.calculateEventScore(8, 33.0));
        assertEquals(306, CsvDataSource.calculateEventScore(9, 350.0));
    }
}
