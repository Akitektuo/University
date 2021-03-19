package ro.ubb.truckers.util;

import ro.ubb.truckers.domain.TruckersException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class StringValidator {
    private StringValidator() {
    }

    public static void requireArguments(List<String> arguments) {
        if (arguments.isEmpty()) {
            throw new TruckersException("This command needs arguments");
        }
    }

    public static void requireArgumentsSize(List<String> arguments, int expectedSize) {
        if (arguments.size() != expectedSize) {
            throw new TruckersException("This command needs exactly %s argument%s",
                    expectedSize, expectedSize == 1 ? "" : "s");
        }
    }

    public static int convertToInt(String fromString) {
        try {
            return Integer.parseInt(fromString);
        } catch (NumberFormatException exception) {
            throw new TruckersException("%s was expected to be of type integer", fromString);
        }
    }

    public static boolean convertToBoolean(String fromString) {
        var hasExpectedFormat = fromString.equalsIgnoreCase("true") ||
                fromString.equalsIgnoreCase("false");

        if (!hasExpectedFormat) {
            throw new TruckersException("%s was expected to be of type boolean", fromString);
        }

        return Boolean.parseBoolean(fromString);
    }

    public static LocalDate convertToDate(String fromString) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(fromString, formatter);
        } catch (DateTimeParseException exception) {
            throw new TruckersException("%s was expected to be of type date and to have the format of day.month.year", fromString);
        }
    }
}
