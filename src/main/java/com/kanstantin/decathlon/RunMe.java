package com.kanstantin.decathlon;

import com.kanstantin.decathlon.model.Factor;
import com.kanstantin.decathlon.model.Participant;
import com.kanstantin.decathlon.model.Result;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RunMe {
    private static Logger log = Logger.getLogger(RunMe.class.getName());

    public static void main(String[] args) {
        log.info("Hello, Decathlon!");
        new RunMe().run();
    }

    public void run() {
        List<Participant> participants = Utils
                .readCSV(this.getClass().getResourceAsStream("/results.csv"), ";")
                .stream()
                .map(arr -> {
                    Participant p = new Participant();
                    p.setName(arr[0]);

                    for (int i = 1; i < arr.length; i++) {
                        p.getResults().add(new Result(arr[i]));
                    }

                    return p;
                })
                .collect(Collectors.toList());

        List<Factor> factors = Utils.readCSV(this.getClass().getResourceAsStream("/factors.csv"), ",")
                .stream()
                .map(arr ->
                        new Factor(
                                arr[0],
                                Utils.parseNumeric(arr[1]).orElse(.0),
                                Utils.parseNumeric(arr[2]).orElse(.0),
                                Utils.parseNumeric(arr[3]).orElse(.0)))

                .collect(Collectors.toList());

        for (Participant p : participants) {
            System.out.printf("%s\t\t", p.getName());

            int totalScore = 0;

            for (int f = 0; f < factors.size(); f++) {
                Factor factor = factors.get(f);
                int score = (int) (factor.getFigA() *
                        Math.pow(
                                Math.abs(p.getResults().get(f).getValue() - factor.getFigB()),
                                factor.getFigC()
                        ));
                System.out.printf("\t%d", score);
                totalScore += score;
            }
            System.out.printf("\ttotal:\t%d\n", totalScore);
        }
        log.info("Done");
    }
}
