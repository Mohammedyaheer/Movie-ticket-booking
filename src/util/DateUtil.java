package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    // Common date formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DISPLAY_DATE_FORMAT = "MMM dd, yyyy";
    public static final String DISPLAY_TIME_FORMAT = "hh:mm a";
    public static final String DISPLAY_DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm a";
    
    /**
     * Converts a string to LocalDate using the default format (yyyy-MM-dd)
     * @param dateString the date string to parse
     * @return LocalDate object
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDate stringToDate(String dateString) {
        return stringToDate(dateString, DATE_FORMAT);
    }
    
    /**
     * Converts a string to LocalDate using the specified format
     * @param dateString the date string to parse
     * @param format the format pattern
     * @return LocalDate object
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDate stringToDate(String dateString, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateString, formatter);
    }
    
    /**
     * Converts a string to LocalDateTime using the default format (yyyy-MM-dd HH:mm:ss)
     * @param dateTimeString the date-time string to parse
     * @return LocalDateTime object
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime stringToDateTime(String dateTimeString) {
        return stringToDateTime(dateTimeString, DATE_TIME_FORMAT);
    }
    
    /**
     * Converts a string to LocalDateTime using the specified format
     * @param dateTimeString the date-time string to parse
     * @param format the format pattern
     * @return LocalDateTime object
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime stringToDateTime(String dateTimeString, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    
    /**
     * Converts a LocalDate to string using the default format (yyyy-MM-dd)
     * @param date the LocalDate object to format
     * @return formatted date string
     */
    public static String dateToString(LocalDate date) {
        return dateToString(date, DATE_FORMAT);
    }
    
    /**
     * Converts a LocalDate to string using the specified format
     * @param date the LocalDate object to format
     * @param format the format pattern
     * @return formatted date string
     */
    public static String dateToString(LocalDate date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }
    
    /**
     * Converts a LocalDateTime to string using the default format (yyyy-MM-dd HH:mm:ss)
     * @param dateTime the LocalDateTime object to format
     * @return formatted date-time string
     */
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTimeToString(dateTime, DATE_TIME_FORMAT);
    }
    
    /**
     * Converts a LocalDateTime to string using the specified format
     * @param dateTime the LocalDateTime object to format
     * @param format the format pattern
     * @return formatted date-time string
     */
    public static String dateTimeToString(LocalDateTime dateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }
    
    /**
     * Formats a LocalDateTime for display (MMM dd, yyyy hh:mm a)
     * @param dateTime the LocalDateTime object to format
     * @return formatted display string
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        return dateTimeToString(dateTime, DISPLAY_DATE_TIME_FORMAT);
    }
    
    /**
     * Formats a LocalDate for display (MMM dd, yyyy)
     * @param date the LocalDate object to format
     * @return formatted display string
     */
    public static String formatForDisplay(LocalDate date) {
        return dateToString(date, DISPLAY_DATE_FORMAT);
    }
    
    /**
     * Checks if a date is today
     * @param date the date to check
     * @return true if the date is today, false otherwise
     */
    public static boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }
    
    /**
     * Checks if a date is in the past
     * @param date the date to check
     * @return true if the date is before today, false otherwise
     */
    public static boolean isPastDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Checks if a date is in the future
     * @param date the date to check
     * @return true if the date is after today, false otherwise
     */
    public static boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
    
    /**
     * Checks if a date-time is in the past
     * @param dateTime the date-time to check
     * @return true if the date-time is before now, false otherwise
     */
    public static boolean isPastDateTime(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }
    
    /**
     * Checks if a date-time is in the future
     * @param dateTime the date-time to check
     * @return true if the date-time is after now, false otherwise
     */
    public static boolean isFutureDateTime(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }
    
    /**
     * Adds days to a date
     * @param date the original date
     * @param days the number of days to add (can be negative to subtract)
     * @return the new date
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }
    
    /**
     * Adds hours to a date-time
     * @param dateTime the original date-time
     * @param hours the number of hours to add (can be negative to subtract)
     * @return the new date-time
     */
    public static LocalDateTime addHours(LocalDateTime dateTime, int hours) {
        return dateTime.plusHours(hours);
    }
    
    /**
     * Calculates the difference in days between two dates
     * @param start the start date
     * @param end the end date
     * @return the number of days between the two dates
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return Math.abs(java.time.temporal.ChronoUnit.DAYS.between(start, end));
    }
    
    /**
     * Calculates the difference in hours between two date-times
     * @param start the start date-time
     * @param end the end date-time
     * @return the number of hours between the two date-times
     */
    public static long hoursBetween(LocalDateTime start, LocalDateTime end) {
        return Math.abs(java.time.temporal.ChronoUnit.HOURS.between(start, end));
    }
    
    /**
     * Validates if a string is a valid date in the default format
     * @param dateString the date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateString) {
        return isValidDate(dateString, DATE_FORMAT);
    }
    
    /**
     * Validates if a string is a valid date in the specified format
     * @param dateString the date string to validate
     * @param format the format pattern
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateString, String format) {
        try {
            stringToDate(dateString, format);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Validates if a string is a valid date-time in the default format
     * @param dateTimeString the date-time string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDateTime(String dateTimeString) {
        return isValidDateTime(dateTimeString, DATE_TIME_FORMAT);
    }
    
    /**
     * Validates if a string is a valid date-time in the specified format
     * @param dateTimeString the date-time string to validate
     * @param format the format pattern
     * @return true if valid, false otherwise
     */
    public static boolean isValidDateTime(String dateTimeString, String format) {
        try {
            stringToDateTime(dateTimeString, format);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Gets the current date as a string in the default format
     * @return current date string
     */
    public static String getCurrentDateString() {
        return dateToString(LocalDate.now());
    }
    
    /**
     * Gets the current date-time as a string in the default format
     * @return current date-time string
     */
    public static String getCurrentDateTimeString() {
        return dateTimeToString(LocalDateTime.now());
    }
    
    /**
     * Gets the start of the day for a given date
     * @param date the date
     * @return the start of the day (00:00:00)
     */
    public static LocalDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay();
    }
    
    /**
     * Gets the end of the day for a given date
     * @param date the date
     * @return the end of the day (23:59:59.999999999)
     */
    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(23, 59, 59, 999999999);
    }
}