package com.kanstantin.decathlon.model;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResultTest {

    @Test
    public void testResultParsing() {
        assertEquals(1.12,   new Result("1.12").getValue(),     0.0001);
        assertEquals(0.00,   new Result("a1.12").getValue(),    0.0001);
        assertEquals(1.12,   new Result("0:01.12").getValue(),  0.0001);
        assertEquals(0.12,   new Result("0:00.12").getValue(),  0.0001);
        assertEquals(345.79, new Result("5:45.79").getValue(),  0.0001);
    }

}
