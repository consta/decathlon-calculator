# Decathlon Competition Calculator

The program ranks Decathlon Competition participants according to calculated scores as per athletes performance figures they have shown while competing. This data (participants names and their performance figures) are read from CSV file which must comply agreed format.

### Prerequisites

You need JDK 8 and Maven 3.6+ in order to be able to build and run the program.

### Building and running tests

Unzip the archive with source code into the working folder and run:

```
mvn clean install
```

There should be executable JAR file assembled in target/ directory after the build.

## Running the program

The program can be executed after the build right in the project directory using the following command:

```
java -jar target/decathlon-1.0-SNAPSHOT.jar -f [SOURCE CSV FILE] -o [OUTPUT XML FILE]
```

Both options ('-f' and '-o') are optional.
If source CSV file is not specified, then the program attempts to read data from 'results.csv' file in the current directory.
In case '-o' option is ommited, it prints XML with results into standard output.


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

