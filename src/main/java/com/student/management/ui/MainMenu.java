package com.student.management.ui;

import com.student.management.util.InputValidator;

public class MainMenu {
    private StudentMenu studentMenu;
    private CourseMenu courseMenu;
    private ReportMenu reportMenu;

    public MainMenu() {
        this.studentMenu = new StudentMenu();
        this.courseMenu = new CourseMenu();
        this.reportMenu = new ReportMenu();
    }

    public void displayMainMenu() {
        while (true) {
            clearScreen();
            printHeader();
            
            System.out.println("║                                                                              ║");
            System.out.println("║     ╔═══════════════════════════════════════════════════════════════╗       ║");
            System.out.println("║     ║                    📚 MAIN MENU                               ║       ║");
            System.out.println("║     ╠═══════════════════════════════════════════════════════════════╣       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [1] 👨‍🎓 Student Management                             ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [2] 📖 Course Management                               ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [3] 📝 Enrollment Management                           ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [4] 📊 Reports & Statistics                            ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [0] 🚪 Exit System                                     ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ╚═══════════════════════════════════════════════════════════════╝       ║");
            System.out.println("║                                                                              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();

            int choice = InputValidator.getIntInput("  👉 Enter your choice (0-4): ");

            switch (choice) {
                case 1:
                    studentMenu.displayStudentMenu();
                    break;
                case 2:
                    courseMenu.displayCourseMenu();
                    break;
                case 3:
                    courseMenu.displayEnrollmentMenu();
                    break;
                case 4:
                    reportMenu.displayReportMenu();
                    break;
                case 0:
                    exitSystem();
                    return;
                default:
                    System.out.println("\n  ❌ Invalid choice! Please select 0-4.");
                    InputValidator.pressEnterToContinue();
            }
        }
    }

    private void printHeader() {
        System.out.println();
        System.out.println("��══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                              ║");
        System.out.println("║   ███████╗████████╗██╗   ██╗██████╗ ███████╗███╗   ██╗████████╗              ║");
        System.out.println("║   ██╔════╝╚══██╔══╝██║   ██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝              ║");
        System.out.println("║   ███████╗   ██║   ██║   ██║██║  ██║█████╗  ██╔██╗ ██║   ██║                 ║");
        System.out.println("║   ╚════██║   ██║   ██║   ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║                 ║");
        System.out.println("║   ███████║   ██║   ╚██████╔╝██████╔╝███████╗██║ ╚████║   ██║                 ║");
        System.out.println("║   ╚══════╝   ╚═╝    ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝                 ║");
        System.out.println("║                                                                              ║");
        System.out.println("║           ███╗   ███╗ █████╗ ███╗   ██╗ █████╗  ██████╗ ███████╗██████╗      ║");
        System.out.println("║           ████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝ ██╔════╝██╔══██╗     ║");
        System.out.println("║           ██╔████╔██║███████║██╔██╗ ██║███████║██║  ███╗█████╗  ██████╔╝     ║");
        System.out.println("║           ██║╚██╔╝██║██╔══██║██║╚██╗██║██╔══██║██║   ██║██╔══╝  ██╔══██╗     ║");
        System.out.println("║           ██║ ╚═╝ ██║██║  ██║██║ ╚████║██║  ██║╚██████╔╝███████╗██║  ██║     ║");
        System.out.println("║           ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝     ║");
        System.out.println("║                                                                              ║");
        System.out.println("║                    🎓 Welcome to Student Management System                   ║");
        System.out.println("║                         Version 1.0 | Made with ❤️                           ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");
    }

    private void exitSystem() {
        clearScreen();
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                              ║");
        System.out.println("║                                                                              ║");
        System.out.println("║              ████████╗██╗  ██╗ █████╗ ███╗   ██╗██╗  ██╗                     ║");
        System.out.println("║              ╚══██╔══╝██║  ██║██╔══██╗████╗  ██║██║ ██╔╝                     ║");
        System.out.println("║                 ██║   ███████║███████║██╔██╗ ██║█████╔╝                      ║");
        System.out.println("║                 ██║   ██╔══██║██╔══██║██║╚██╗██║██╔═██╗                      ║");
        System.out.println("║                 ██║   ██║  ██║██║  ██║██║ ╚████║██║  ██╗                     ║");
        System.out.println("║                 ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝                     ║");
        System.out.println("║                                                                              ║");
        System.out.println("║                     ██╗   ██╗ ██████╗ ██╗   ██╗██╗                           ║");
        System.out.println("║                     ╚██╗ ██╔╝██╔═══██╗██║   ██║██║                           ║");
        System.out.println("║                      ╚████╔╝ ██║   ██║██║   ██║██║                           ║");
        System.out.println("║                       ╚██╔╝  ██║   ██║██║   ██║╚═╝                           ║");
        System.out.println("║                        ██║   ╚██████╔╝╚██████╔╝██╗                           ║");
        System.out.println("║                        ╚═╝    ╚═════╝  ╚═════╝ ╚═╝                           ║");
        System.out.println("║                                                                              ║");
        System.out.println("║                    👋 See you again! Have a great day!                       ║");
        System.out.println("║                                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // Fallback for Windows
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}