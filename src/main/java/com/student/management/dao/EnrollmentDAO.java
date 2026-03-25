package com.student.management.dao;

import com.student.management.database.DatabaseConnection;
import com.student.management.model.Enrollment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    // Enroll student in course
    public boolean enrollStudent(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date, grade, status) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, enrollment.getStudentId());
            pstmt.setInt(2, enrollment.getCourseId());
            pstmt.setDate(3, Date.valueOf(enrollment.getEnrollmentDate()));
            pstmt.setString(4, enrollment.getGrade());
            pstmt.setString(5, enrollment.getStatus());
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error enrolling student: " + e.getMessage());
            return false;
        }
    }

    // Get enrollment by ID
    public Enrollment getEnrollmentById(int enrollmentId) {
        String sql = "SELECT e.*, s.roll_number, s.first_name || ' ' || s.last_name as student_name, " +
                    "c.course_code, c.course_name FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.student_id " +
                    "JOIN courses c ON e.course_id = c.course_id WHERE e.enrollment_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, enrollmentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractEnrollmentFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching enrollment: " + e.getMessage());
        }
        return null;
    }

    // Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT e.*, s.roll_number, s.first_name || ' ' || s.last_name as student_name, " +
                    "c.course_code, c.course_name FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.student_id " +
                    "JOIN courses c ON e.course_id = c.course_id ORDER BY e.enrollment_id";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                enrollments.add(extractEnrollmentFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching enrollments: " + e.getMessage());
        }
        return enrollments;
    }

    // Get enrollments by student ID
    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT e.*, s.roll_number, s.first_name || ' ' || s.last_name as student_name, " +
                    "c.course_code, c.course_name FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.student_id " +
                    "JOIN courses c ON e.course_id = c.course_id WHERE e.student_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                enrollments.add(extractEnrollmentFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching student enrollments: " + e.getMessage());
        }
        return enrollments;
    }

    // Get enrollments by course ID
    public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT e.*, s.roll_number, s.first_name || ' ' || s.last_name as student_name, " +
                    "c.course_code, c.course_name FROM enrollments e " +
                    "JOIN students s ON e.student_id = s.student_id " +
                    "JOIN courses c ON e.course_id = c.course_id WHERE e.course_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                enrollments.add(extractEnrollmentFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching course enrollments: " + e.getMessage());
        }
        return enrollments;
    }

    // Check if student is already enrolled
    public boolean isStudentEnrolled(int studentId, int courseId) {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE student_id = ? AND course_id = ? AND status = 'ENROLLED'";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking enrollment: " + e.getMessage());
        }
        return false;
    }

    // Update grade
    public boolean updateGrade(int enrollmentId, String grade) {
        String sql = "UPDATE enrollments SET grade = ? WHERE enrollment_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, grade);
            pstmt.setInt(2, enrollmentId);
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating grade: " + e.getMessage());
            return false;
        }
    }

    // Update enrollment status
    public boolean updateEnrollmentStatus(int enrollmentId, String status) {
        String sql = "UPDATE enrollments SET status = ? WHERE enrollment_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, enrollmentId);
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating enrollment status: " + e.getMessage());
            return false;
        }
    }

    // Drop enrollment
    public boolean dropEnrollment(int enrollmentId) {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, enrollmentId);
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error dropping enrollment: " + e.getMessage());
            return false;
        }
    }

    // Get enrollment count for a course
    public int getEnrollmentCountByCourse(int courseId) {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE course_id = ? AND status = 'ENROLLED'";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting enrollment count: " + e.getMessage());
        }
        return 0;
    }

    // Helper method
    private Enrollment extractEnrollmentFromResultSet(ResultSet rs) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
        enrollment.setStudentId(rs.getInt("student_id"));
        enrollment.setCourseId(rs.getInt("course_id"));
        
        Date enrollDate = rs.getDate("enrollment_date");
        if (enrollDate != null) {
            enrollment.setEnrollmentDate(enrollDate.toLocalDate());
        }
        
        enrollment.setGrade(rs.getString("grade"));
        enrollment.setStatus(rs.getString("status"));
        enrollment.setRollNumber(rs.getString("roll_number"));
        enrollment.setStudentName(rs.getString("student_name"));
        enrollment.setCourseCode(rs.getString("course_code"));
        enrollment.setCourseName(rs.getString("course_name"));
        
        return enrollment;
    }
}