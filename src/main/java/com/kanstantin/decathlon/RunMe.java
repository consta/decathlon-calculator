package com.kanstantin.decathlon;

public class RunMe {
    public static final String DEFAULT_INPUT_CSV = "results.csv";

    public static void main(String[] args) {
        // obtain the name of source CSV if launched with "-f" option
        String inputFile = parseCliForFilename("-f", args);

        // obtain the name of output XML if launched with "-o" option
        String outputFile = parseCliForFilename("-o", args);

        if (inputFile == null) {
            inputFile = DEFAULT_INPUT_CSV;
        }

        //
        Application app = new Application();
        app.run(inputFile, outputFile);
    }

    /**
     * Very simple parser which goes through an array of command line parameters and retrieves the argument of
     * the option specified
     * @param cliSwitch CL option (-f or -o but could be anything)
     *                 - it just signals that the next element in the array should be retrieved
     * @param args source array of arguments
     * @return the argument of the given option, or null
     */
    protected static String parseCliForFilename(String cliSwitch, String[] args) {
        if (Utils.stringIsNotBlank(cliSwitch) && args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (cliSwitch.equals(Utils.trim(args[i])) && i < args.length - 1) {
                    String result = Utils.trim(args[i + 1]);
                    return Utils.stringIsNotBlank(result) ? result : null;
                }
            }
        }
        return null;
    }
}
