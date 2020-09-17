package com.kanstantin.decathlon;

import com.kanstantin.decathlon.model.Competition;
import com.kanstantin.decathlon.model.Event;
import com.kanstantin.decathlon.model.Factor;
import com.kanstantin.decathlon.model.Participant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Application {
    private static Pattern PATTERN_MINUTES = Pattern.compile("\\s?+(\\d+):(\\d+\\.\\d+)\\s?+");
    private static List<Factor> factors;

    static {
        factors = Utils.readCSV(Application.class.getResourceAsStream("/factors.csv"), ",")
                .stream()
                .map(arr ->
                        new Factor(
                                arr[0],
                                Utils.parseNumeric(arr[1]).orElse(.0),
                                Utils.parseNumeric(arr[2]).orElse(.0),
                                Utils.parseNumeric(arr[3]).orElse(.0)))

                .collect(Collectors.toList());
    }

    public void run(String inputFile, String outputFile) {
        List<Participant> participants = loadParticipants(inputFile);

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

    private List<Participant> loadParticipants(String inputFile) {
        List<String[]> lines = Utils.readCSV(inputFile, ";");
        return  lines
                .stream()
                .map(arr -> {
                    Participant p = new Participant();
                    // arr[0] element index = 0 is the name of the athlete
                    p.setName(arr[0]);
                    int totalScore = 0;
                    // arr[1..11] these elements contain athlete's performance figures
                    for (int i = 1; i < arr.length; i++) {
                        // the list of events is zero-based
                        int eventIndex = i - 1;
                        // pick up the factor from the list
                        Factor f = factors.get(eventIndex);
                        // create an event and set it's name
                        Event event = new Event();
                        event.setName(f.getName());
                        // parse athlete's performance figure for the event
                        double eventPerformance = parseEventPerformance(arr[i]);
                        event.setPerformance(eventPerformance);
                        // calculate athlete's score for the event
                        int score = calculateEventScore(eventIndex, eventPerformance);
                        event.setScore(score);
                        // process totalScore
                        totalScore += score;
                        // add event record to the list of athlete's events
                        p.getEvents().add(event);
                    }

                    p.setTotalScore(totalScore);
                    return p;
                })
                .sorted(Comparator.comparing(Participant::getTotalScore).reversed())
                .collect(Collectors.toList());
    }

    protected static int calculateEventScore(int eventIndex, double performanceValue) {
        Factor f = factors.get(eventIndex);
        return (int) (f.getFigA() *
                Math.pow(
                        Math.abs(performanceValue - f.getFigB()),
                        f.getFigC()
                ));
    }

    protected static double parseEventPerformance(String rawValue) {
        Matcher matcher = PATTERN_MINUTES.matcher(rawValue);
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1)) * 60 + Double.parseDouble(matcher.group(2));
        } else {
            return Utils.parseNumeric(rawValue).orElse(.0);
        }
    }
}
