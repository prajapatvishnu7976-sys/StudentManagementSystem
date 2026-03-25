package com.student.management.service;

import com.student.management.dao.CourseDAO;
import com.student.management.dao.EnrollmentDAO;
import com.student.management.dao.StudentDAO;
import com.student.management.model.Course;
import com.student.management.model.Enrollment;
import com.student.management.model.Student;

import java.util.List;

public class EnrollmentService {
    private EnrollmentDAO enrollmentDAO;
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;

    public EnrollmentService() {
        this.enrollmentDAO = new EnrollmentDAO();
        this.studentDAO = new StudentDAO();
        this.courseDAO = new CourseDAO();
    }

    // Enroll student in a course
    public boolean enrollStudent(int studentId, int courseId) {
        // Check if student exists
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("❌ Error: Student not found!");
            return false;
        }

        // Check if course exists
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("❌ Error: Course not found!");
            return false;
        }

        // Check if student is active
        if (!"ACTIVE".equals(student.getStatus())) {
            System.out.println("❌ Error: Student is not active!");
            return false;
        }

        // Check if course is active and has space
        if (!"ACTIVE".equals(course.getStatus())) {
            System.out.println("❌ Error: Course is not active!");
            return false;
        }

        if (course.isFull()) {
            System.out.println("❌ Error: Course is full! No more seats available.");
            return false;
        }

        // Check if already enrolled
        if (enrollmentDAO.isStudentEnrolled(studentId, courseId)) {
            System.out.println("❌ Error: Student is already enrolled in this course!");
            return false;
        }

        // Create enrollment
        Enrollment enrollment = new Enrollment(studentId, courseId);
        boolean result = enrollmentDAO.enrollStudent(enrollment);

        if (result) {
            // Update enrolled count in course
            int newCount = enrollmentDAO.getEnrollmentCountByCourse(courseId);
            courseDAO.updateEnrolledCount(courseId, newCount);
            System.out.println("✅ Student enrolled successfully!");
            System.out.println("   Student: " + student.getFullName());
            System.out.println("   Course: " + course.getCourseName());
        }

        return result;
    }

    // Get enrollment by ID
    public Enrollment getEnrollmentById(int enrollmentId) {
        return enrollmentDAO.getEnrollmentById(enrollmentId);
    }

    // Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollmentDAO.getAllEnrollments();
    }

    // Get enrollments by student
    public List<Enrollment> getStudentEnrollments(int studentId) {
        return enrollmentDAO.getEnrollmentsByStudentId(studentId);
    }

    // Get enrollments by course
    public List<Enrollment> getCourseEnrollments(int courseId) {
        return enrollmentDAO.getEnrollmentsByCourseId(courseId);
    }

    // Update grade
    public boolean updateGrade(int enrollmentId, String grade) {
        boolean result = enrollmentDAO.updateGrade(enrollmentId, grade);
        if (result) {
            System.out.println("✅ Grade updated successfully!");
        }
        return result;
    }

    // Drop enrollment
    public boolean dropEnrollment(int enrollmentId) {
        Enrollment enrollment = enrollmentDAO.getEnrollmentById(enrollmentId);
        if (enrollment == null) {
            System.out.println("❌ Error: Enrollment not found!");
            return false;
        }

        boolean result = enrollmentDAO.dropEnrollment(enrollmentId);
        
        if (result) {
            // Update enrolled count in course
            int newCount = enrollmentDAO.getEnrollmentCountByCourse(enrollment.getCourseId());
            courseDAO.updateEnrolledCount(enrollment.getCourseId(), newCount);
            System.out.println("✅ Enrollment dropped successfully!");
        }

        return result;
    }

    // Mark as completed
    public boolean completeEnrollment(int enrollmentId, String grade) {
        boolean gradeResult = enrollmentDAO.updateGrade(enrollmentId, grade);
        boolean statusResult = enrollmentDAO.updateEnrollmentStatus(enrollmentId, "COMPLETED");
        
        if (gradeResult && statusResult) {
            System.out.println("✅ Course marked as completed!");
        }
        return gradeResult && statusResult;
    }

    // Display all enrollments
    public void displayAllEnrollments() {
        List<Enrollment> enrollments = getAllEnrollments();
        
        if (enrollments.isEmpty()) {
            System.out.println("\n📭 No enrollments found.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                          📝 ALL ENROLLMENTS                                                         ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-4s ║ %-10s ║ %-18s ║ %-8s ║ %-22s ║ %-10s ║ %-5s ║ %-10s ║%n", 
                         "ID", "ROLL NO", "STUDENT NAME", "CODE", "COURSE NAME", "DATE", "GRADE", "STATUS");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        
        for (Enrollment enrollment : enrollments) {
            System.out.printf("║ %-4d ║ %-10s ║ %-18s ║ %-8s ║ %-22s ║ %-10s ║ %-5s ║ %-10s ║%n",
                            enrollment.getEnrollmentId(),
                            enrollment.getRollNumber(),
                            truncate(enrollment.getStudentName(), 18),
                            enrollment.getCourseCode(),
                            truncate(enrollment.getCourseName(), 22),
                            enrollment.getEnrollmentDate(),
                            enrollment.getGrade(),
                            enrollment.getStatus());
        }
        
        System.out.println("��════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Total Enrollments: " + enrollments.size());
    }

    // Display student's enrollments
    public void displayStudentEnrollments(int studentId) {
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        List<Enrollment> enrollments = getStudentEnrollments(studentId);
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║            📚 ENROLLED COURSES FOR: " + String.format("%-40s", student.getFullName()) + "║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
        
        if (enrollments.isEmpty()) {
            System.out.println("║                        No courses enrolled.                                   ║");
        } else {
            System.out.printf("║ %-4s ║ %-10s ║ %-25s ║ %-12s ║ %-6s ║ %-10s ║%n", 
                             "ID", "CODE", "COURSE NAME", "DATE", "GRADE", "STATUS");
            System.out.println("��═══════════════════════════════════════════════════════════════════════════════╣");
            
            for (Enrollment enrollment : enrollments) {
                System.out.printf("║ %-4d ║ %-10s ║ %-25s ║ %-12s ║ %-6s ║ %-10s ║%n",
                                enrollment.getEnrollmentId(),
                                enrollment.getCourseCode(),
                                truncate(enrollment.getCourseName(), 25),
                                enrollment.getEnrollmentDate(),
                                enrollment.getGrade(),
                                enrollment.getStatus());
            }
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
    }

    // Display course's enrolled students
    public void displayCourseEnrollments(int courseId) {
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("❌ Course not found!");
            return;
        }

        List<Enrollment> enrollments = getCourseEnrollments(courseId);
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║            👥 ENROLLED STUDENTS IN: " + String.format("%-40s", course.getCourseName()) + "║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
        
        if (enrollments.isEmpty()) {
            System.out.println("║                        No students enrolled.                                  ║");
        } else {
            System.out.printf("║ %-4s ║ %-12s ║ %-25s ║ %-12s ║ %-6s ║ %-10s ║%n", 
                             "ID", "ROLL NO", "STUDENT NAME", "DATE", "GRADE", "STATUS");
            System.out.println("╠═══════════════════════════════════════════════════════════════════════════════╣");
            
            for (Enrollment enrollment : enrollments) {
                System.out.printf("║ %-4d ║ %-12s ║ %-25s ║ %-12s ║ %-6s ║ %-10s ║%n",
                                enrollment.getEnrollmentId(),
                                enrollment.getRollNumber(),
                                truncate(enrollment.getStudentName(), 25),
                                enrollment.getEnrollmentDate(),
                                enrollment.getGrade(),
                                enrollment.getStatus());
            }
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Total Enrolled: " + enrollments.size() + " / " + course.getMaxStudents());
    }

    // Helper method
    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}