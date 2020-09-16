package com.kanstantin.decathlon.model;

import com.kanstantin.decathlon.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Result {
    private static Pattern PATTERN_MINUTES = Pattern.compile("\\s?+(\\d+):(\\d+\\.\\d+)\\s?+");

    private double value;

    public Result(String rawValue) {
        Matcher matcher = PATTERN_MINUTES.matcher(rawValue);
        if (matcher.matches()) {
            this.value = Integer.parseInt(matcher.group(1)) * 60 + Double.parseDouble(matcher.group(2));
        }
        else {
            this.value = Utils.parseNumeric(rawValue).orElse(.0);
        }
    }

    public double getValue() {
        return value;
    }
}
