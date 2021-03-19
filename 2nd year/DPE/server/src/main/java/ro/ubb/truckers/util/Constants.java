package ro.ubb.truckers.util;

public class Constants {
    private Constants() {
    }

    public static final String FILE_PROPERTY_DELIMITER = "_;_";
    public static final String DEFAULT_FILE_NAME = "data";
    public static final String DEFAULT_XML_NAME = "data";

    public static final String SQL_URL = "jdbc:postgresql://rares.itay.me:5432/truckers";
    public static final String SQL_USERNAME = "postgres";
    public static final String SQL_PASSWORD = "";

    public static final String EXECUTE_QUERY_ERROR = "Could not execute query";
}
