package com.student.management.ui;

import com.student.management.model.Course;
import com.student.management.model.Enrollment;
import com.student.management.model.Student;
import com.student.management.service.CourseService;
import com.student.management.service.EnrollmentService;
import com.student.management.service.StudentService;
import com.student.management.util.InputValidator;

import java.util.List;

public class ReportMenu {
    private StudentService studentService;
    private CourseService courseService;
    private EnrollmentService enrollmentService;

    public ReportMenu() {
        this.studentService = new StudentService();
        this.courseService = new CourseService();
        this.enrollmentService = new EnrollmentService();
    }

    public void displayReportMenu() {
        while (true) {
            printReportMenuHeader();
            
            System.out.println("║     ╔═══════════════════════════════════════════════════════════════╗       ║");
            System.out.println("║     ║              📊 REPORTS & STATISTICS                          ║       ║");
            System.out.println("║     ╠═══════════════════════════════════════════════════════════════╣       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [1] 📈 Dashboard Summary                               ║       ║");
            System.out.println("║     ║        [2] 👨‍🎓 Student Report                                  ║       ║");
            System.out.println("║     ║        [3] 📖 Course Report                                   ║       ║");
            System.out.println("║     ║        [4] 📝 Enrollment Report                               ║       ║");
            System.out.println("║     ║        [5] 🏆 Top Performing Students                         ║       ║");
            System.out.println("║     ║        [6] 📚 Most Popular Courses                            ║       ║");
            System.out.println("║     ║        [0] 🔙 Back to Main Menu                               ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ╚═══════════════════════════════════════════════════════════════╝       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();

            int choice = InputValidator.getIntInput("  👉 Enter your choice (0-6): ");

            switch (choice) {
                case 1:
                    showDashboard();
                    break;
                case 2:
                    showStudentReport();
                    break;
                case 3:
                    showCourseReport();
                    break;
                case 4:
                    showEnrollmentReport();
                    break;
                case 5:
                    showTopStudents();
                    break;
                case 6:
                    showPopularCourses();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\n  ❌ Invalid choice! Please select 0-6.");
                    InputValidator.pressEnterToContinue();
            }
        }
    }

    private void showDashboard() {
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              📈 DASHBOARD SUMMARY                                     ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                                       ║");
        
        int totalStudents = studentService.getTotalStudentCount();
        int totalCourses = courseService.getTotalCourseCount();
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        int totalEnrollments = enrollments.size();

        List<Student> activeStudents = studentService.getActiveStudents();
        List<Course> activeCourses = courseService.getActiveCourses();

        System.out.println("║     ╔═══════════════════════════════════════════════════════════════════════╗       ║");
        System.out.println("║     ║                        QUICK STATISTICS                               ║       ║");
        System.out.println("║     ╠═══════════════════════════════════════════════════════════════════════╣       ║");
        System.out.printf("║     ║     👨‍🎓 Total Students        :    %-6d                             ║       ║%n", totalStudents);
        System.out.printf("║     ║     ✅ Active Students        :    %-6d                             ║       ║%n", activeStudents.size());
        System.out.printf("║     ║     📖 Total Courses          :    %-6d                             ║       ║%n", totalCourses);
        System.out.printf("║     ║     ✅ Active Courses          :    %-6d                             ║       ║%n", activeCourses.size());
        System.out.printf("║     ║     📝 Total Enrollments      :    %-6d                             ║       ║%n", totalEnrollments);
        System.out.println("║     ╚═══════════════════════════════════════════════════════════════════════╝       ║");
        System.out.println("║                                                                                       ║");
        
        // Calculate some stats
        int completedEnrollments = 0;
        int enrolledCount = 0;
        for (Enrollment e : enrollments) {
            if ("COMPLETED".equals(e.getStatus())) completedEnrollments++;
            if ("ENROLLED".equals(e.getStatus())) enrolledCount++;
        }

        System.out.println("║     ╔═══════════════════════════════════════════════════════════════════════╗       ║");
        System.out.println("║     ║                     ENROLLMENT BREAKDOWN                              ║       ║");
        System.out.println("║     ╠═══════════════════════════════════════════════════════════════════════╣       ║");
        System.out.printf("║     ║     📗 Currently Enrolled     :    %-6d                             ║       ║%n", enrolledCount);
        System.out.printf("║     ║     ✅ Completed              :    %-6d                             ║       ║%n", completedEnrollments);
        System.out.printf("║     ║     ❌ Dropped                :    %-6d                             ║       ║%n", totalEnrollments - enrolledCount - completedEnrollments);
        System.out.println("║     ╚═══════════════════════════════════════════════════════════════════════╝       ║");
        System.out.println("║                                                                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════╝");

        InputValidator.pressEnterToContinue();
    }

    private void showStudentReport() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   👨‍🎓 STUDENT REPORT                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("  Report Options:");
        System.out.println("  [1] All Students List");
        System.out.println("  [2] Active Students Only");
        System.out.println("  [3] Individual Student Details");

        int choice = InputValidator.getIntInput("\n  👉 Enter your choice: ");

        switch (choice) {
            case 1:
                studentService.displayAllStudents();
                break;
            case 2:
                List<Student> activeStudents = studentService.getActiveStudents();
                System.out.println("\n  📋 ACTIVE STUDENTS (" + activeStudents.size() + "):");
                for (Student s : activeStudents) {
                    System.out.println("  " + s);
                }
                break;
            case 3:
                String rollNumber = InputValidator.getStringInput("  Enter Roll Number: ");
                Student student = studentService.getStudentByRollNumber(rollNumber.toUpperCase());
                if (student != null) {
                    studentService.displayStudentDetails(student);
                    System.out.println("\n  📚 Enrolled Courses:");
                    enrollmentService.displayStudentEnrollments(student.getStudentId());
                } else {
                    System.out.println("  ❌ Student not found!");
                }
                break;
            default:
                System.out.println("  ❌ Invalid choice!");
        }

        InputValidator.pressEnterToContinue();
    }

    private void showCourseReport() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   📖 COURSE REPORT                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("  Report Options:");
        System.out.println("  [1] All Courses List");
        System.out.println("  [2] Active Courses Only");
        System.out.println("  [3] Individual Course Details");
        System.out.println("  [4] Courses with Available Seats");

        int choice = InputValidator.getIntInput("\n  👉 Enter your choice: ");

        switch (choice) {
            case 1:
                courseService.displayAllCourses();
                break;
            case 2:
                List<Course> activeCourses = courseService.getActiveCourses();
                System.out.println("\n  📋 ACTIVE COURSES (" + activeCourses.size() + "):");
                for (Course c : activeCourses) {
                    System.out.println("  " + c);
                }
                break;
            case 3:
                String courseCode = InputValidator.getStringInput("  Enter Course Code: ");
                Course course = courseService.getCourseByCode(courseCode.toUpperCase());
                if (course != null) {
                    courseService.displayCourseDetails(course);
                    System.out.println("\n  👥 Enrolled Students:");
                    enrollmentService.displayCourseEnrollments(course.getCourseId());
                } else {
                    System.out.println("  ❌ Course not found!");
                }
                break;
            case 4:
                List<Course> allCourses = courseService.getAllCourses();
                System.out.println("\n  📋 COURSES WITH AVAILABLE SEATS:");
                System.out.println("  ─────────────────────────────────────────────────────────────");
                for (Course c : allCourses) {
                    if (c.getAvailableSeats() > 0 && "ACTIVE".equals(c.getStatus())) {
                        System.out.printf("  %-10s %-30s - %d seats available%n", 
                                        c.getCourseCode(), c.getCourseName(), c.getAvailableSeats());
                    }
                }
                break;
            default:
                System.out.println("  ❌ Invalid choice!");
        }

        InputValidator.pressEnterToContinue();
    }

    private void showEnrollmentReport() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   📝 ENROLLMENT REPORT                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        enrollmentService.displayAllEnrollments();

        // Summary
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        int enrolled = 0, completed = 0, dropped = 0;
        
        for (Enrollment e : enrollments) {
            switch (e.getStatus()) {
                case "ENROLLED": enrolled++; break;
                case "COMPLETED": completed++; break;
                case "DROPPED": dropped++; break;
            }
        }

        System.out.println("\n  📊 ENROLLMENT SUMMARY:");
        System.out.println("  ─────────────────────────────────────");
        System.out.printf("  ✅ Enrolled  : %d%n", enrolled);
        System.out.printf("  🎓 Completed : %d%n", completed);
        System.out.printf("  ❌ Dropped   : %d%n", dropped);
        System.out.println("  ─────────────────────────────────────");
        System.out.printf("  📌 Total     : %d%n", enrollments.size());

        InputValidator.pressEnterToContinue();
    }

    private void showTopStudents() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               🏆 TOP PERFORMING STUDENTS                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        
        System.out.println("  🥇 Students with A+ Grades:");
        System.out.println("  ─────────────────────────────────────────────────────────────");
        
        boolean found = false;
        for (Enrollment e : enrollments) {
            if ("A+".equals(e.getGrade()) || "A".equals(e.getGrade())) {
                System.out.printf("  ⭐ %s (%s) - %s - Grade: %s%n", 
                                e.getStudentName(), e.getRollNumber(), 
                                e.getCourseName(), e.getGrade());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("  No A+ or A grades found yet.");
        }

        System.out.println("\n  📊 Students with Most Course Enrollments:");
        System.out.println("  ─────────────────────────────────────────────────────────────");
        
        List<Student> students = studentService.getAllStudents();
        for (Student s : students) {
            List<Enrollment> studentEnrollments = enrollmentService.getStudentEnrollments(s.getStudentId());
            if (studentEnrollments.size() > 0) {
                System.out.printf("  📚 %s (%s) - %d course(s)%n", 
                                s.getFullName(), s.getRollNumber(), studentEnrollments.size());
            }
        }

        InputValidator.pressEnterToContinue();
    }

    private void showPopularCourses() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               📚 MOST POPULAR COURSES                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        List<Course> courses = courseService.getAllCourses();
        
        // Sort by enrolled students (simple bubble sort for demonstration)
        for (int i = 0; i < courses.size() - 1; i++) {
            for (int j = 0; j < courses.size() - i - 1; j++) {
                if (courses.get(j).getEnrolledStudents() < courses.get(j + 1).getEnrolledStudents()) {
                    Course temp = courses.get(j);
                    courses.set(j, courses.get(j + 1));
                    courses.set(j + 1, temp);
                }
            }
        }

        System.out.println("  📊 Courses Ranked by Enrollment:");
        System.out.println("  ─────────────────────────────────────────────────────────────────────────");
        System.out.printf("  %-5s %-10s %-30s %-20s %-10s%n", "RANK", "CODE", "COURSE NAME", "INSTRUCTOR", "ENROLLED");
        System.out.println("  ─────────────────────────────────────────────────────────────────────────");
        
        int rank = 1;
        for (Course c : courses) {
            String medal = "";
            if (rank == 1) medal = "🥇";
            else if (rank == 2) medal = "🥈";
            else if (rank == 3) medal = "🥉";
            else medal = "  ";
            
            System.out.printf("  %s %-3d %-10s %-30s %-20s %d/%d%n", 
                            medal, rank, c.getCourseCode(), 
                            truncate(c.getCourseName(), 30), 
                            truncate(c.getInstructor(), 20),
                            c.getEnrolledStudents(), c.getMaxStudents());
            rank++;
        }

        InputValidator.pressEnterToContinue();
    }

    private void printReportMenuHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) System.out.println();
        
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🎓 STUDENT MANAGEMENT SYSTEM                              ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");
    }

    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}