package pt.dbservices.utilities.assertion;

import pt.dbservices.utilities.reports.Results;

public class AssertUtils {

    public static void softAssertEqual(String actual, String expected, String keyword) {
        if (actual.equalsIgnoreCase(expected))
            Results.pass(keyword + " - is displayed as expected => " + expected);
        else
            Results.error(keyword + " : - is not expected. Expected => " + expected + ", Actual => " + actual, true);
    }
    public static void softAssertEqual(String actual, String expected) {
        if (!actual.equalsIgnoreCase(expected))
            Results.error("Content verification failed. Expected => " + expected + ", Actual => " + actual, true);
    }
    public static void softAssertTrue(boolean isTrue, String message) {
        if (isTrue) {
            Results.pass("Content verification passed.");
        } else {
            Results.error(message, true);
        }
    }

    public static void softAssertContains(String actual, String expected, String keyword) {
        boolean aContainsE = actual.toLowerCase().contains(expected.toLowerCase());
        boolean eContainsA = expected.toLowerCase().contains(actual.toLowerCase());
        if (aContainsE || eContainsA)
            Results.pass(keyword + " : Content verification passed. Expected => " + expected + ", Actual => " + actual);
        else
            Results.error(keyword + " : Content verification failed. Expected => " + expected + ", Actual => " + actual, true);
    }
}
