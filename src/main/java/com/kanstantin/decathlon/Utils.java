package com.kanstantin.decathlon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private static Logger log = Logger.getLogger(Utils.class.getName());

    /**
     * Checks whether a string is empty or null
     * @param source
     * @return true if given string is not null and has non blank characters
     *         otherwise - false
     */
    public static boolean stringIsNotBlank(String source) {
        if (source == null) {
            return false;
        }
        return source.trim().length() > 0;
    }

    /**
     * Reads the content of CSV file, where columns are delimited by specified delimiter
     * Lines which start with "#" are treated as comments and is not loaded
     * @param filename
     * @param _delimiter
     * @return List of splitted lines
     */
    public static List<String[]> readCSV(String filename, String _delimiter) {
        if (!Utils.stringIsNotBlank(filename)) {
            throw new IllegalStateException("Filename must not be blank");
        }

        final String delimiter = Utils.stringIsNotBlank(_delimiter) ? _delimiter : ";";

        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            List<String[]> result =  stream
                    .filter(line -> Utils.stringIsNotBlank(line) && !line.trim().startsWith("#"))
                    .map(line -> line.split(delimiter))
                    .collect(Collectors.toList());
            log.info("got " + result.size() + " lines from " + filename);
            return result;
        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to process file " + filename, ex);
        }
    }
}
