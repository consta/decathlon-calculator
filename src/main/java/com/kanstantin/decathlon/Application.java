package com.kanstantin.decathlon;

import com.kanstantin.decathlon.model.Event;
import com.kanstantin.decathlon.model.Factor;
import com.kanstantin.decathlon.model.Participant;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Application {
    private static Logger log = Logger.getLogger(Application.class.getName());
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

    public void run() {
        List<Participant> participants = Utils
                .readCSV(this.getClass().getResourceAsStream("/results.csv"), ";")
                .stream()
                .map(arr -> {
                    Participant p = new Participant();
                    p.setName(arr[0]);

                    for (int i = 1; i < arr.length; i++) {
                        int eventIndex = i - 1;
                        Factor f = factors.get(eventIndex);
                        Event event = new Event();
                        event.setName(f.getName());
                        double eventPerformance = parseEventPerformance(arr[i]);
                        event.setPerformance(eventPerformance);
                        event.setScore(calculateEventScore(eventIndex, eventPerformance));
                        p.getEvents().add(event);
                    }

                    return p;
                })
                .collect(Collectors.toList());

        for (Participant p : participants) {
            System.out.printf("%s\t\t", p.getName());

            int totalScore = 0;
            p.getEvents().forEach(e -> System.out.printf("\t%d", e.getScore()));
            System.out.printf("\ttotal:\t%d\n", totalScore);
        }
        log.info("Done");
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
        }
        else {
            return Utils.parseNumeric(rawValue).orElse(.0);
        }
    }

}
