package com.student.management.ui;

import com.student.management.model.Student;
import com.student.management.service.EnrollmentService;
import com.student.management.service.StudentService;
import com.student.management.util.InputValidator;

import java.time.LocalDate;
import java.util.List;

public class StudentMenu {
    private StudentService studentService;
    private EnrollmentService enrollmentService;

    public StudentMenu() {
        this.studentService = new StudentService();
        this.enrollmentService = new EnrollmentService();
    }

    public void displayStudentMenu() {
        while (true) {
            printStudentMenuHeader();
            
            System.out.println("║     ╔═══════════════════════════════════════════════════════════════╗       ║");
            System.out.println("║     ║              👨‍🎓 STUDENT MANAGEMENT                           ║       ║");
            System.out.println("║     ╠═══════════════════════════════════════════════════════════════╣       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ║        [1] ➕ Add New Student                                 ║       ║");
            System.out.println("║     ║        [2] 📋 View All Students                               ║       ║");
            System.out.println("║     ║        [3] 🔍 Search Student                                  ║       ║");
            System.out.println("║     ║        [4] ✏️  Update Student                                  ║       ║");
            System.out.println("║     ║        [5] 🗑️  Delete Student                                  ║       ║");
            System.out.println("║     ║        [6] 📚 View Student's Courses                          ║       ║");
            System.out.println("║     ║        [0] 🔙 Back to Main Menu                               ║       ║");
            System.out.println("║     ║                                                               ║       ║");
            System.out.println("║     ╚═══════════════════════════════════════════════════════════════╝       ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
            System.out.println();

            int choice = InputValidator.getIntInput("  👉 Enter your choice (0-6): ");

            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    viewStudentCourses();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\n  ❌ Invalid choice! Please select 0-6.");
                    InputValidator.pressEnterToContinue();
            }
        }
    }

    private void addNewStudent() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ➕ ADD NEW STUDENT                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        Student student = new Student();
        
        student.setRollNumber(InputValidator.getRollNumberInput("  Enter Roll Number: "));
        student.setFirstName(InputValidator.getStringInput("  Enter First Name: "));
        student.setLastName(InputValidator.getStringInput("  Enter Last Name: "));
        student.setEmail(InputValidator.getEmailInput("  Enter Email: "));
        student.setPhone(InputValidator.getPhoneInput("  Enter Phone (10 digits): "));
        student.setDateOfBirth(InputValidator.getDateInput("  Enter Date of Birth"));
        student.setGender(InputValidator.getGenderInput("  Enter Gender"));
        student.setAddress(InputValidator.getStringInput("  Enter Address: "));
        student.setEnrollmentDate(LocalDate.now());
        student.setStatus("ACTIVE");

        System.out.println("\n  📋 Student Details:");
        System.out.println("  ─────────────────────────────────────");
        System.out.println("  Roll Number : " + student.getRollNumber());
        System.out.println("  Name        : " + student.getFullName());
        System.out.println("  Email       : " + student.getEmail());
        System.out.println("  Phone       : " + student.getPhone());
        System.out.println("  DOB         : " + student.getDateOfBirth());
        System.out.println("  Gender      : " + student.getGender());
        System.out.println("  Address     : " + student.getAddress());
        System.out.println("  ─────────────────────────────────────");

        if (InputValidator.getConfirmation("\n  ✅ Confirm adding this student?")) {
            studentService.addStudent(student);
        } else {
            System.out.println("  ❌ Student addition cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void viewAllStudents() {
        System.out.println();
        studentService.displayAllStudents();
        InputValidator.pressEnterToContinue();
    }

    private void searchStudent() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🔍 SEARCH STUDENT                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        System.out.println("  Search by:");
        System.out.println("  [1] Roll Number");
        System.out.println("  [2] Name");
        System.out.println("  [3] Student ID");
        
        int choice = InputValidator.getIntInput("\n  👉 Enter your choice: ");

        switch (choice) {
            case 1:
                String rollNumber = InputValidator.getStringInput("  Enter Roll Number: ");
                Student studentByRoll = studentService.getStudentByRollNumber(rollNumber.toUpperCase());
                if (studentByRoll != null) {
                    studentService.displayStudentDetails(studentByRoll);
                } else {
                    System.out.println("\n  ❌ No student found with Roll Number: " + rollNumber);
                }
                break;
            case 2:
                String name = InputValidator.getStringInput("  Enter Name to search: ");
                List<Student> students = studentService.searchStudents(name);
                if (students.isEmpty()) {
                    System.out.println("\n  ❌ No students found with name containing: " + name);
                } else {
                    System.out.println("\n  Found " + students.size() + " student(s):");
                    for (Student s : students) {
                        studentService.displayStudentDetails(s);
                    }
                }
                break;
            case 3:
                int studentId = InputValidator.getIntInput("  Enter Student ID: ");
                Student studentById = studentService.getStudentById(studentId);
                if (studentById != null) {
                    studentService.displayStudentDetails(studentById);
                } else {
                    System.out.println("\n  ❌ No student found with ID: " + studentId);
                }
                break;
            default:
                System.out.println("  ❌ Invalid choice!");
        }

        InputValidator.pressEnterToContinue();
    }

    private void updateStudent() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ✏️  UPDATE STUDENT                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        String rollNumber = InputValidator.getStringInput("  Enter Roll Number of student to update: ");
        Student student = studentService.getStudentByRollNumber(rollNumber.toUpperCase());

        if (student == null) {
            System.out.println("\n  ❌ No student found with Roll Number: " + rollNumber);
            InputValidator.pressEnterToContinue();
            return;
        }

        studentService.displayStudentDetails(student);

        System.out.println("\n  What do you want to update?");
        System.out.println("  [1] First Name");
        System.out.println("  [2] Last Name");
        System.out.println("  [3] Email");
        System.out.println("  [4] Phone");
        System.out.println("  [5] Address");
        System.out.println("  [6] Status");
        System.out.println("  [7] Update All Fields");
        System.out.println("  [0] Cancel");

        int choice = InputValidator.getIntInput("\n  👉 Enter your choice: ");

        switch (choice) {
            case 1:
                student.setFirstName(InputValidator.getStringInput("  Enter new First Name: "));
                break;
            case 2:
                student.setLastName(InputValidator.getStringInput("  Enter new Last Name: "));
                break;
            case 3:
                student.setEmail(InputValidator.getEmailInput("  Enter new Email: "));
                break;
            case 4:
                student.setPhone(InputValidator.getPhoneInput("  Enter new Phone: "));
                break;
            case 5:
                student.setAddress(InputValidator.getStringInput("  Enter new Address: "));
                break;
            case 6:
                String[] statuses = {"ACTIVE", "INACTIVE", "GRADUATED"};
                student.setStatus(InputValidator.getStatusInput("  Enter new Status (ACTIVE/INACTIVE/GRADUATED): ", statuses));
                break;
            case 7:
                student.setFirstName(InputValidator.getStringInput("  Enter First Name [" + student.getFirstName() + "]: "));
                student.setLastName(InputValidator.getStringInput("  Enter Last Name [" + student.getLastName() + "]: "));
                student.setEmail(InputValidator.getEmailInput("  Enter Email [" + student.getEmail() + "]: "));
                student.setPhone(InputValidator.getPhoneInput("  Enter Phone [" + student.getPhone() + "]: "));
                student.setAddress(InputValidator.getStringInput("  Enter Address: "));
                String[] allStatuses = {"ACTIVE", "INACTIVE", "GRADUATED"};
                student.setStatus(InputValidator.getStatusInput("  Enter Status (ACTIVE/INACTIVE/GRADUATED): ", allStatuses));
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
            studentService.updateStudent(student);
        } else {
            System.out.println("  ❌ Update cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void deleteStudent() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   🗑️  DELETE STUDENT                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        String rollNumber = InputValidator.getStringInput("  Enter Roll Number of student to delete: ");
        Student student = studentService.getStudentByRollNumber(rollNumber.toUpperCase());

        if (student == null) {
            System.out.println("\n  ❌ No student found with Roll Number: " + rollNumber);
            InputValidator.pressEnterToContinue();
            return;
        }

        studentService.displayStudentDetails(student);

        System.out.println("\n  ⚠️  WARNING: This will also delete all enrollments of this student!");
        
        if (InputValidator.getConfirmation("  ❗ Are you sure you want to delete this student?")) {
            if (InputValidator.getConfirmation("  ❗❗ This action cannot be undone. Confirm again?")) {
                studentService.deleteStudent(student.getStudentId());
            } else {
                System.out.println("  ❌ Deletion cancelled.");
            }
        } else {
            System.out.println("  ❌ Deletion cancelled.");
        }

        InputValidator.pressEnterToContinue();
    }

    private void viewStudentCourses() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║               📚 VIEW STUDENT'S ENROLLED COURSES              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        String rollNumber = InputValidator.getStringInput("  Enter Roll Number: ");
        Student student = studentService.getStudentByRollNumber(rollNumber.toUpperCase());

        if (student == null) {
            System.out.println("\n  ❌ No student found with Roll Number: " + rollNumber);
        } else {
            enrollmentService.displayStudentEnrollments(student.getStudentId());
        }

        InputValidator.pressEnterToContinue();
    }

    private void printStudentMenuHeader() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) System.out.println();
        
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🎓 STUDENT MANAGEMENT SYSTEM                              ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════╣");
    }
}