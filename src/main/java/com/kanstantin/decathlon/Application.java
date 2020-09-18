package com.kanstantin.decathlon;

import com.kanstantin.decathlon.model.Competition;
import com.kanstantin.decathlon.model.Participant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class Application {
    private DataSource dataSource;

    public Application(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run(String inputFile, String outputFile) {
        List<Participant> participants = dataSource.loadData();

        Competition competition = new Competition();
        competition.setName("Grand Decathlon Tour 2020");
        competition.setParticipants(participants);
        competition.doRankParticipants();

        String xml = XMLMapper.writeValue(competition);

        if (outputFile != null) {
            try (OutputStream os = new FileOutputStream(new File(outputFile));
                 PrintStream out = new PrintStream(os)){
                out.print(xml);
            }
            catch (IOException ex) {
                throw new RuntimeException("Error occurred while saving to file " + outputFile, ex);
            }
        }
        else {
            System.out.println(xml);
        }
    }
}
