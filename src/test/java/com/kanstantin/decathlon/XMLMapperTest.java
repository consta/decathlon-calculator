package com.kanstantin.decathlon;


import com.kanstantin.decathlon.model.Competition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XMLMapperTest {

    @Test
    public void testXMLMapperBasic() {
        Competition competition = new Competition();
        competition.setName("the name");
        String result = XMLMapper.writeValue(competition);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><competition name=\"the name\"/>\n", result);
    }

}
