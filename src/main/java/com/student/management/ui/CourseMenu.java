package com.student.management.ui;

import com.student.management.model.Course;
import com.student.management.model.Student;
import com.student.management.service.CourseService;
import com.student.management.service.EnrollmentService;
import com.student.management.service.StudentService;
import com.student.management.util.InputValidator;

import java.util.List;

public class CourseMenu {
    private CourseService courseService;
    private StudentService studentService;
    private EnrollmentService enrollmentService;

    public CourseMenu() {
        this.courseService = new CourseService();
        this.studentService = new StudentService();
        this.enrollmentService = new EnrollmentService();
    }

    public void displayCourseMenu() {
        while (true) {
            printCourseMenuHeader();
            
            System.out.println("║     ╔═══════════════════════════════════════════════════════════════╗       ║");
            System.out.println("║     ║               📖 COURSE MANAGEMENT                            ║       ║");
            System.out.println("║     ╠═══════════════════════════════════════════════════════════════╣       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [1] ➕ Add New Course                                  ║       ║");
            System.out.println("║     ║        [2] 📋 View All Courses                                ║       ║");
            System.out.println("║     ║        [3] 🔍 Search Course                                   ║       ║");
            System.out.println("║     ║        [4] ✏️  Update Course                                   ║       ║");
            System.out.println("║     ║        [5] 🗑️  Delete Course                                   ║       ║");
            System.out.println("║     ║        [6] 👥 View Enrolled Students                          ║       ║");
            System.out.println("║     ║        [0] 🔙 Back to Main Menu                               ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ╚═══════════════════════════════════════════════════════════════╝       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();

            int choice = InputValidator.getIntInput("  👉 Enter your choice (0-6): ");

            switch (choice) {
                case 1:
                    addNewCourse();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    searchCourse();
                    break;
                case 4:
                    updateCourse();
                    break;
                case 5:
                    deleteCourse();
                    break;
                case 6:
                    viewEnrolledStudents();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\n  ❌ Invalid choice! Please select 0-6.");
                    InputValidator.pressEnterToContinue();
            }
        }
    }

    public void displayEnrollmentMenu() {
        while (true) {
            printEnrollmentMenuHeader();
            
            System.out.println("║     ╔═══════════════════════════════════════════════════════════════╗       ║");
            System.out.println("║     ║              📝 ENROLLMENT MANAGEMENT                         ║       ║");
            System.out.println("║     ╠═══════════════════════════════════════════════════════════════╣       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [1] ➕ Enroll Student in Course                        ║       ║");
            System.out.println("║     ║        [2] 📋 View All Enrollments                            ║       ║");
            System.out.println("║     ║        [3] 📝 Update Grade                                    ║       ║");
            System.out.println("║     ║        [4] ✅ Mark Course Completed                           ║       ║");
            System.out.println("║     ║        [5] ❌ Drop Enrollment                                 ║       ║");
            System.out.println("║     ║        [0] 🔙 Back to Main Menu                               ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ╚═══════════════════════════════════════════════════════════════╝       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();

            int choice = InputValidator.getIntInput("  👉 Enter your choice (0-5): ");

            switch (choice) {
                case 1:
                    enrollStudentInCourse();
                    break;
                case 2:
                    viewAllEnrollments();
                    break;
                case 3:
                    updateGrade();
                    break;
                case 4:
                    markCourseCompleted();
                    break;
                case 5:
                    dropEnrollment();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\n  ❌ Invalid choice! Please select 0-5.");
                    InputValidator.pressEnterToContinue();
            }
        }
    }

    private void addNewCourse() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ➕ ADD NEW COURSE                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        Course course = new Course();
        
        course.setCourseCode(InputValidator.getCourseCodeInput("  Enter Course Code (e.g., CS101): "));
        course.setCourseName(InputValidator.getStringInput("  Enter Course Name: "));
        course.setDescription(InputValidator.getStringInput("  Enter Description: "));
        course.setCredits(InputValidator.getIntInput("  Enter Credits (1-6): ", 1, 6));
        course.setInstructor(InputValidator.getStringInput("  Enter Instructor Name: "));
        course.setMaxStudents(InputValidator.getIntInput("  Enter Maximum Students (10-200): ", 10, 200));

        System.out.println("\n  📋 Course Details:");
        System.out.println("  ─────────────────────────────────────");
        System.out.println("  Course Code  : " + course.getCourseCode());
        System.out.println("  Course Name  : " + course.getCourseName());
        System.out.println("  Description  : " + course.getDescription());
        System.out.println("  Credits      : " + course.getCredits());
        System.out.println("  Instructor   : " + course.getInstructor());
        System.out.println("  Max Students : " + course.getMaxStudents());
        System.out.println("  ─────────────────────────────────────");

        if (InputValidator.getConfirmation("\n  ✅ Confirm adding this course?")) {
            courseService.addCourse(course);
        } else {
            System.out.println("  ❌ Course addition cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void viewAllCourses() {
        System.out.println();
        courseService.displayAllCourses();
        InputValidator.pressEnterToContinue();
    }

    private void searchCourse() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🔍 SEARCH COURSE                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("  Search by:");
        System.out.println("  [1] Course Code");
        System.out.println("  [2] Course Name");
        System.out.println("  [3] Course ID");
        
        int choice = InputValidator.getIntInput("\n  👉 Enter your choice: ");

        switch (choice) {
            case 1:
                String courseCode = InputValidator.getStringInput("  Enter Course Code: ");
                Course courseByCode = courseService.getCourseByCode(courseCode.toUpperCase());
                if (courseByCode != null) {
                    courseService.displayCourseDetails(courseByCode);
                } else {
                    System.out.println("\n  ❌ No course found with Code: " + courseCode);
                }
                break;
            case 2:
                String name = InputValidator.getStringInput("  Enter Course Name to search: ");
                List<Course> courses = courseService.searchCourses(name);
                if (courses.isEmpty()) {
                    System.out.println("\n  ❌ No courses found with name containing: " + name);
                } else {
                    System.out.println("\n  Found " + courses.size() + " course(s):");
                    for (Course c : courses) {
                        courseService.displayCourseDetails(c);
                    }
                }
                break;
            case 3:
                int courseId = InputValidator.getIntInput("  Enter Course ID: ");
                Course courseById = courseService.getCourseById(courseId);
                if (courseById != null) {
                    courseService.displayCourseDetails(courseById);
                } else {
                    System.out.println("\n  ❌ No course found with ID: " + courseId);
                }
                break;
            default:
                System.out.println("  ❌ Invalid choice!");
        }

        InputValidator.pressEnterToContinue();
    }

    private void updateCourse() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ✏️  UPDATE COURSE                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        String courseCode = InputValidator.getStringInput("  Enter Course Code to update: ");
        Course course = courseService.getCourseByCode(courseCode.toUpperCase());

        if (course == null) {
            System.out.println("\n  ❌ No course found with Code: " + courseCode);
            InputValidator.pressEnterToContinue();
            return;
        }

        courseService.displayCourseDetails(course);

        System.out.println("\n  What do you want to update?");
        System.out.println("  [1] Course Name");
        System.out.println("  [2] Description");
        System.out.println("  [3] Credits");
        System.out.println("  [4] Instructor");
        System.out.println("  [5] Max Students");
        System.out.println("  [6] Status");
        System.out.println("  [0] Cancel");

        int choice = InputValidator.getIntInput("\n  👉 Enter your choice: ");

        switch (choice) {
            case 1:
                course.setCourseName(InputValidator.getStringInput("  Enter new Course Name: "));
                break;
            case 2:
                course.setDescription(InputValidator.getStringInput("  Enter new Description: "));
                break;
            case 3:
                course.setCredits(InputValidator.getIntInput("  Enter new Credits (1-6): ", 1, 6));
                break;
            case 4:
                course.setInstructor(InputValidator.getStringInput("  Enter new Instructor: "));
                break;
            case 5:
                int newMax = InputValidator.getIntInput("  Enter new Max Students: ", course.getEnrolledStudents(), 200);
                course.setMaxStudents(newMax);
                break;
            case 6:
                String[] statuses = {"ACTIVE", "INACTIVE"};
                course.setStatus(InputValidator.getStatusInput("  Enter new Status (ACTIVE/INACTIVE): ", statuses));
                break;
            case 0:
                System.out.println("  ❌ Update cancelled.");
                InputValidator.pressEnterToContinue();
                return;
            default:
                System.out.println("  ❌ Invalid choice!");
                InputValidator.pressEnterToContinue();
                return;
        }

        if (InputValidator.getConfirmation("\n  ✅ Confirm update?")) {
            courseService.updateCourse(course);
        } else {
            System.out.println("  ❌ Update cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void deleteCourse() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🗑️  DELETE COURSE                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        String courseCode = InputValidator.getStringInput("  Enter Course Code to delete: ");
        Course course = courseService.getCourseByCode(courseCode.toUpperCase());

        if (course == null) {
            System.out.println("\n  ❌ No course found with Code: " + courseCode);
            InputValidator.pressEnterToContinue();
            return;
        }

        courseService.displayCourseDetails(course);

        if (course.getEnrolledStudents() > 0) {
            System.out.println("\n  ⚠️  WARNING: This course has " + course.getEnrolledStudents() + " enrolled students!");
            System.out.println("  All enrollments will be deleted!");
        }
        
        if (InputValidator.getConfirmation("\n  ❗ Are you sure you want to delete this course?")) {
            courseService.deleteCourse(course.getCourseId());
        } else {
            System.out.println("  ❌ Deletion cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void viewEnrolledStudents() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              👥 VIEW ENROLLED STUDENTS IN COURSE              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        String courseCode = InputValidator.getStringInput("  Enter Course Code: ");
        Course course = courseService.getCourseByCode(courseCode.toUpperCase());

        if (course == null) {
            System.out.println("\n  ❌ No course found with Code: " + courseCode);
        } else {
            enrollmentService.displayCourseEnrollments(course.getCourseId());
        }

        InputValidator.pressEnterToContinue();
    }

    private void enrollStudentInCourse() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               ➕ ENROLL STUDENT IN COURSE                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        // Show available courses
        System.out.println("  📖 Available Courses:");
        courseService.displayAllCourses();

        // Show active students
        System.out.println("\n  👨‍🎓 Active Students:");
        studentService.displayAllStudents();

        String rollNumber = InputValidator.getStringInput("\n  Enter Student Roll Number: ");
        Student student = studentService.getStudentByRollNumber(rollNumber.toUpperCase());

        if (student == null) {
            System.out.println("\n  ❌ No student found with Roll Number: " + rollNumber);
            InputValidator.pressEnterToContinue();
            return;
        }

        String courseCode = InputValidator.getStringInput("  Enter Course Code: ");
        Course course = courseService.getCourseByCode(courseCode.toUpperCase());

        if (course == null) {
            System.out.println("\n  ❌ No course found with Code: " + courseCode);
            InputValidator.pressEnterToContinue();
            return;
        }

        System.out.println("\n  📋 Enrollment Details:");
        System.out.println("  ─────────────────────────────────────");
        System.out.println("  Student : " + student.getFullName() + " (" + student.getRollNumber() + ")");
        System.out.println("  Course  : " + course.getCourseName() + " (" + course.getCourseCode() + ")");
        System.out.println("  ─────────────────────────────────────");

        if (InputValidator.getConfirmation("\n  ✅ Confirm enrollment?")) {
            enrollmentService.enrollStudent(student.getStudentId(), course.getCourseId());
        } else {
            System.out.println("  ❌ Enrollment cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void viewAllEnrollments() {
        System.out.println();
        enrollmentService.displayAllEnrollments();
        InputValidator.pressEnterToContinue();
    }

    private void updateGrade() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   📝 UPDATE GRADE                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        enrollmentService.displayAllEnrollments();

        int enrollmentId = InputValidator.getIntInput("\n  Enter Enrollment ID to update: ");
        String grade = InputValidator.getGradeInput("  Enter new Grade");

        if (InputValidator.getConfirmation("\n  ✅ Confirm grade update?")) {
            enrollmentService.updateGrade(enrollmentId, grade);
        } else {
            System.out.println("  ❌ Update cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void markCourseCompleted() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               ✅ MARK COURSE AS COMPLETED                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        enrollmentService.displayAllEnrollments();

        int enrollmentId = InputValidator.getIntInput("\n  Enter Enrollment ID: ");
        String grade = InputValidator.getGradeInput("  Enter Final Grade");

        if (InputValidator.getConfirmation("\n  ✅ Confirm completion?")) {
            enrollmentService.completeEnrollment(enrollmentId, grade);
        } else {
            System.out.println("  ❌ Operation cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void dropEnrollment() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ❌ DROP ENROLLMENT                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        enrollmentService.displayAllEnrollments();

        int enrollmentId = InputValidator.getIntInput("\n  Enter Enrollment ID to drop: ");

        if (InputValidator.getConfirmation("\n  ⚠️ Are you sure you want to drop this enrollment?")) {
            enrollmentService.dropEnrollment(enrollmentId);
        } else {
            System.out.println("  ❌ Drop cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void printCourseMenuHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) System.out.println();
        
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🎓 STUDENT MANAGEMENT SYSTEM                              ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");
    }

    private void printEnrollmentMenuHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) System.out.println();
        
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🎓 STUDENT MANAGEMENT SYSTEM                              ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");
    }
}