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
     * @param resourceName absolute or relative filename of CSV file
     * @param _delimiter   string value representing delimiter for this CSV file
     * @return List of splitted lines
     */
    public static List<String[]> readCSV(String resourceName, String _delimiter) {
        if (!Utils.stringIsNotBlank(resourceName)) {
            throw new IllegalStateException("Filename must not be blank");
        }

        final String delimiter = Utils.stringIsNotBlank(_delimiter) ? _delimiter : ";";

        final String absoluteFilename = Optional.ofNullable(RunMe.class.getClassLoader().getResource(resourceName))
                .orElseThrow(() -> new RuntimeException("Failed to obtain URL for " + resourceName))
                .getFile();

        try (Stream<String> stream = Files.lines(Paths.get(absoluteFilename))) {
            List<String[]> result = stream
                    .filter(isLineParseable)
                    .map(line -> line.split(delimiter))
                    .collect(Collectors.toList());
            log.fine("got " + result.size() + " lines from " + resourceName);
            return result;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to process file " + resourceName, ex);
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

    private static Predicate<String> isLineParseable =
            line -> Utils.stringIsNotBlank(line) && !line.trim().startsWith("#");

}
