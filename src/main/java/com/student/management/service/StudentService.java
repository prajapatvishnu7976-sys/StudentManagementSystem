package com.student.management.service;

import com.student.management.dao.StudentDAO;
import com.student.management.model.Student;

import java.util.List;

public class StudentService {
    private StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    // Add new student
    public boolean addStudent(Student student) {
        // Check if roll number already exists
        if (studentDAO.getStudentByRollNumber(student.getRollNumber()) != null) {
            System.out.println("❌ Error: Roll number already exists!");
            return false;
        }
        
        boolean result = studentDAO.addStudent(student);
        if (result) {
            System.out.println("✅ Student added successfully!");
        }
        return result;
    }

    // Get student by ID
    public Student getStudentById(int studentId) {
        return studentDAO.getStudentById(studentId);
    }

    // Get student by Roll Number
    public Student getStudentByRollNumber(String rollNumber) {
        return studentDAO.getStudentByRollNumber(rollNumber);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    // Get active students
    public List<Student> getActiveStudents() {
        return studentDAO.getActiveStudents();
    }

    // Update student
    public boolean updateStudent(Student student) {
        boolean result = studentDAO.updateStudent(student);
        if (result) {
            System.out.println("✅ Student updated successfully!");
        }
        return result;
    }

    // Delete student
    public boolean deleteStudent(int studentId) {
        boolean result = studentDAO.deleteStudent(studentId);
        if (result) {
            System.out.println("✅ Student deleted successfully!");
        }
        return result;
    }

    // Search students
    public List<Student> searchStudents(String name) {
        return studentDAO.searchStudentsByName(name);
    }

    // Get total count
    public int getTotalStudentCount() {
        return studentDAO.getTotalStudentCount();
    }

    // Display all students in table format
    public void displayAllStudents() {
        List<Student> students = getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("\n📭 No students found in the database.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                    📚 ALL STUDENTS LIST                                                 ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-5s ║ %-12s ║ %-20s ║ %-25s ║ %-12s ║ %-10s ║ %-10s ║%n", 
                         "ID", "ROLL NO", "NAME", "EMAIL", "PHONE", "DOB", "STATUS");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        
        for (Student student : students) {
            System.out.printf("║ %-5d ║ %-12s ║ %-20s ║ %-25s ║ %-12s ║ %-10s ║ %-10s ║%n",
                            student.getStudentId(),
                            student.getRollNumber(),
                            truncate(student.getFullName(), 20),
                            truncate(student.getEmail(), 25),
                            student.getPhone(),
                            student.getDateOfBirth(),
                            student.getStatus());
        }
        
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Total Students: " + students.size());
    }

    // Display single student details
    public void displayStudentDetails(Student student) {
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║              📋 STUDENT DETAILS                       ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║  Student ID    : %-35d  ║%n", student.getStudentId());
        System.out.printf("║  Roll Number   : %-35s  ║%n", student.getRollNumber());
        System.out.printf("║  Name          : %-35s  ║%n", student.getFullName());
        System.out.printf("║  Email         : %-35s  ║%n", student.getEmail());
        System.out.printf("║  Phone         : %-35s  ║%n", student.getPhone());
        System.out.printf("║  Date of Birth : %-35s  ║%n", student.getDateOfBirth());
        System.out.printf("║  Gender        : %-35s  ║%n", student.getGender());
        System.out.printf("║  Address       : %-35s  ║%n", truncate(student.getAddress(), 35));
        System.out.printf("║  Enrolled On   : %-35s  ║%n", student.getEnrollmentDate());
        System.out.printf("║  Status        : %-35s  ║%n", student.getStatus());
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    // Helper method to truncate long strings
    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}