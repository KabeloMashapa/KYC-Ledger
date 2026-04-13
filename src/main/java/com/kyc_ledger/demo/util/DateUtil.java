package com.kyc_ledger.demo.util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;

public class DateUtil {

    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FULL_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String formatDateTime(LocalDateTime dateTime) {
        if(dateTime == null) return "N/A";
        return dateTime.format(DISPLAY_FORMATTER);
    }
    public static String formatDate(LocalDateTime dateTime) {
        if(dateTime == null) return "N/A";
        return dateTime.format(DATE_FORMATTER);
    }
    public static String formatFullDateTime(LocalDateTime dateTime) {
        if(dateTime == null) return "N/A";
        return dateTime.format(FULL_FORMATTER);
    }
    // Calculate KYC expiry date
    public static LocalDateTime calculateKycExpiry() {
        return LocalDateTime.now().plusYears(1);
    }
    public static boolean isExpired(LocalDateTime expiryDate) {
        if(expiryDate == null) return false ;
        return LocalDateTime.now().isAfter(expiryDate);

    }
    public static int calculateAge(String dateOfBirth) {
        LocalDate dob = LocalDate.parse(dateOfBirth,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Period.between(dob,LocalDate.now()).getYears();
    }
    public static boolean isAdult(String dateOfBirth) {
        return calculateAge(dateOfBirth) >= 18 ;
    }
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(FULL_FORMATTER);
    }

}
