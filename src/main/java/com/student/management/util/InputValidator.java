package com.student.management.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {
    private static Scanner scanner = new Scanner(System.in);
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // Phone validation pattern (10 digits)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
    
    // Roll number pattern
    private static final Pattern ROLL_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");

    // Get valid integer input
    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
            }
        }
    }

    // Get valid integer within range
    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            int value = getIntInput(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("❌ Please enter a number between " + min + " and " + max);
        }
    }

    // Get non-empty string input
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("❌ Input cannot be empty!");
        }
    }

    // Get optional string input (can be empty)
    public static String getOptionalStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Get valid email input
    public static String getEmailInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String email = scanner.nextLine().trim().toLowerCase();
            if (EMAIL_PATTERN.matcher(email).matches()) {
                return email;
            }
            System.out.println("❌ Invalid email format! Example: example@email.com");
        }
    }

    // Get valid phone input
    public static String getPhoneInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String phone = scanner.nextLine().trim();
            if (PHONE_PATTERN.matcher(phone).matches()) {
                return phone;
            }
            System.out.println("❌ Invalid phone number! Please enter 10 digits.");
        }
    }

    // Get valid date input
    public static LocalDate getDateInput(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine().trim();
            try {
                LocalDate date = LocalDate.parse(dateStr, formatter);
                // Check if date is not in future
                if (date.isAfter(LocalDate.now())) {
                    System.out.println("❌ Date cannot be in the future!");
                    continue;
                }
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format! Use YYYY-MM-DD (e.g., 2005-03-15)");
            }
        }
    }

    // Get valid gender input
    public static String getGenderInput(String prompt) {
        while (true) {
            System.out.print(prompt + " (M/F/O): ");
            String input = scanner.nextLine().trim().toUpperCase();
            switch (input) {
                case "M": return "Male";
                case "F": return "Female";
                case "O": return "Other";
                default:
                    System.out.println("❌ Invalid input! Enter M (Male), F (Female), or O (Other)");
            }
        }
    }

    // Get valid roll number
    public static String getRollNumberInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String rollNumber = scanner.nextLine().trim().toUpperCase();
            if (ROLL_PATTERN.matcher(rollNumber).matches() && rollNumber.length() >= 4) {
                return rollNumber;
            }
            System.out.println("❌ Invalid roll number! Use alphanumeric characters (min 4 chars).");
        }
    }

    // Get valid course code
    public static String getCourseCodeInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String code = scanner.nextLine().trim().toUpperCase();
            if (code.length() >= 3 && code.length() <= 10) {
                return code;
            }
            System.out.println("❌ Course code should be 3-10 characters!");
        }
    }

    // Get valid grade input
    public static String getGradeInput(String prompt) {
        String[] validGrades = {"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F", "N/A"};
        while (true) {
            System.out.print(prompt + " (A+, A, A-, B+, B, B-, C+, C, C-, D, F): ");
            String grade = scanner.nextLine().trim().toUpperCase();
            for (String validGrade : validGrades) {
                if (validGrade.equals(grade)) {
                    return grade;
                }
            }
            System.out.println("❌ Invalid grade! Valid grades: A+, A, A-, B+, B, B-, C+, C, C-, D, F");
        }
    }

    // Get yes/no confirmation
    public static boolean getConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            } else if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            System.out.println("❌ Please enter Y (Yes) or N (No)");
        }
    }

    // Get valid status input
    public static String getStatusInput(String prompt, String[] validStatuses) {
        while (true) {
            System.out.print(prompt);
            String status = scanner.nextLine().trim().toUpperCase();
            for (String validStatus : validStatuses) {
                if (validStatus.equals(status)) {
                    return status;
                }
            }
            System.out.println("❌ Invalid status! Valid options: " + String.join(", ", validStatuses));
        }
    }

    // Wait for user to press Enter
    public static void pressEnterToContinue() {
        System.out.print("\n⏎ Press Enter to continue...");
        scanner.nextLine();
    }
}