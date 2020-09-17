package com.kanstantin.decathlon;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RunMeTest {

    @Test
    public void testParseCliForNames() {
        assertEquals("filename",
                RunMe.parseCliForFilename("-f", new String[]{"-f", "filename"}));

        assertEquals("filename1",
                RunMe.parseCliForFilename("-f", new String[]{"", "-f", "filename1", "abc"}));

        assertEquals("filename2",
                RunMe.parseCliForFilename("-f", new String[]{"", " -f", " filename2  ", "abc"}));

        assertNull(RunMe.parseCliForFilename("-f", new String[]{"", "-f", " "}));
    }
}
