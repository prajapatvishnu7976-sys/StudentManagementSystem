package com.student.management.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");

    // Get current date
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    // Get current date as formatted string
    public static String getCurrentDateString() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    // Get current date time
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    // Format date for display
    public static String formatDate(LocalDate date) {
        if (date == null) return "N/A";
        return date.format(DISPLAY_FORMATTER);
    }

    // Format date for storage
    public static String formatDateForStorage(LocalDate date) {
        if (date == null) return null;
        return date.format(DATE_FORMATTER);
    }

    // Parse date from string
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    // Calculate age from date of birth
    public static int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) return 0;
        return (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
    }

    // Check if date is valid
    public static boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString, DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Check if date is in the past
    public static boolean isPastDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    // Check if date is in the future
    public static boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    // Get days between two dates
    public static long getDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}