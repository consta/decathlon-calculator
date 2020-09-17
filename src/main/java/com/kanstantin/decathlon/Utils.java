package com.kanstantin.decathlon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private static Logger log = Logger.getLogger(Utils.class.getName());

    /**
     * Checks whether a string is empty or null
     *
     * @param source a string containing source value
     * @return true if given string is not null and has non blank characters
     * otherwise - false
     */
    public static boolean stringIsNotBlank(String source) {
        if (source == null) {
            return false;
        }
        return source.trim().length() > 0;
    }

    /**
     * Removes leading and trailing spaces from a string
     * @param source string to be trimmed
     * @return trimmed string or null in case source string was null
     */
    public static String trim(String source) {
        return source != null ? source.trim() : null;
    }

    /**
     * Attempts to parse double value out from a string
     * @param source string containing figure
     * @return parsed value as an Optional set, or empty optional in case source string contains not eligible value
     */
    public static Optional<Double> parseNumeric(String source) {
        if (stringIsNotBlank(source)) {
            try {
                return Optional.of(Double.parseDouble(source.trim()));
            } catch (NumberFormatException ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Reads the content of CSV from a given resource, where columns are delimited by specified delimiter
     * Lines which start with "#" are treated as comments and is not loaded
     *
     * @param filename absolute or relative filename of CSV file
     * @param _delimiter   string value representing delimiter for this CSV file
     * @return List of splitted lines
     */
    public static List<String[]> readCSV(String filename, String _delimiter) {
        if (!Utils.stringIsNotBlank(filename)) {
            throw new IllegalStateException("Filename must not be blank");
        }

        final String delimiter = Utils.stringIsNotBlank(_delimiter) ? _delimiter : ";";

        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            List<String[]> result = stream
                    .filter(isLineParseable)
                    .map(line -> line.split(delimiter))
                    .collect(Collectors.toList());
            log.fine("got " + result.size() + " lines from " + filename);
            return result;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to process file " + filename, ex);
        }
    }

    /**
     * Reads the content of CSV from the given inputStream, where columns are delimited by specified delimiter
     * Lines which start with "#" are treated as comments and is not loaded
     *
     * @param inputStream the steam with CSV data
     * @param _delimiter  string value representing delimiter for this CSV file
     * @return List of splitted lines
     */
    public static List<String[]> readCSV(InputStream inputStream, String _delimiter) {
        if (inputStream == null) {
            throw new IllegalStateException("inputStream must not be null");
        }

        final String delimiter = Utils.stringIsNotBlank(_delimiter) ? _delimiter : ";";

        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .filter(isLineParseable)
                .map(line -> line.split(delimiter))
                .collect(Collectors.toList());
    }

    /**
     * Produces "place" or "ranking" string for the given position and amount of equally ranked participants
     *
     * For instance, two athletes share places "2" and "3" - in this case, input arguments to the method are (2, 2)
     * and the result is "2-3"
     *
     * Another example: three athletes showed the same result and are going to share places 4, 5, 6
     * Then, the input is (4, 3) and the result is "4-5-6"
     *
     * @param position of the starting rank
     * @param equalCount amount of athletes with equal results
     * @return "ranking" string like "1", or "1-2" etc
     */
    public static String ranking(int position, int equalCount) {
        if (position < 1 || equalCount < 1) {
            return "?";
        }

        if (equalCount == 1) {
            return String.format("%d", position);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = position; i < position + equalCount; i++) {
            if (i == position) {
                sb.append(i);
            }
            else {
                sb.append("-").append(i);
            }
        }
        return sb.toString();
    }

    private static Predicate<String> isLineParseable =
            line -> Utils.stringIsNotBlank(line) && !line.trim().startsWith("#");

}
