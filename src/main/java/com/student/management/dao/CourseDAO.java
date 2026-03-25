package com.student.management.dao;

import com.student.management.database.DatabaseConnection;
import com.student.management.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Add new course
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (course_code, course_name, description, credits, " +
                    "instructor, max_students, enrolled_students, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getCourseName());
            pstmt.setString(3, course.getDescription());
            pstmt.setInt(4, course.getCredits());
            pstmt.setString(5, course.getInstructor());
            pstmt.setInt(6, course.getMaxStudents());
            pstmt.setInt(7, course.getEnrolledStudents());
            pstmt.setString(8, course.getStatus());
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding course: " + e.getMessage());
            return false;
        }
    }

    // Get course by ID
    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractCourseFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching course: " + e.getMessage());
        }
        return null;
    }

    // Get course by code
    public Course getCourseByCode(String courseCode) {
        String sql = "SELECT * FROM courses WHERE course_code = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, courseCode);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractCourseFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching course: " + e.getMessage());
        }
        return null;
    }

    // Get all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY course_id";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                courses.add(extractCourseFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching courses: " + e.getMessage());
        }
        return courses;
    }

    // Get active courses
    public List<Course> getActiveCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE status = 'ACTIVE' ORDER BY course_id";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                courses.add(extractCourseFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching active courses: " + e.getMessage());
        }
        return courses;
    }

    // Update course
    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET course_name = ?, description = ?, credits = ?, " +
                    "instructor = ?, max_students = ?, status = ? WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getDescription());
            pstmt.setInt(3, course.getCredits());
            pstmt.setString(4, course.getInstructor());
            pstmt.setInt(5, course.getMaxStudents());
            pstmt.setString(6, course.getStatus());
            pstmt.setInt(7, course.getCourseId());
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating course: " + e.getMessage());
            return false;
        }
    }

    // Update enrolled students count
    public boolean updateEnrolledCount(int courseId, int count) {
        String sql = "UPDATE courses SET enrolled_students = ? WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, count);
            pstmt.setInt(2, courseId);
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating enrolled count: " + e.getMessage());
            return false;
        }
    }

    // Delete course
    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting course: " + e.getMessage());
            return false;
        }
    }

    // Search courses by name
    public List<Course> searchCoursesByName(String name) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE course_name LIKE ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                courses.add(extractCourseFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching courses: " + e.getMessage());
        }
        return courses;
    }

    // Get total course count
    public int getTotalCourseCount() {
        String sql = "SELECT COUNT(*) FROM courses";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting course count: " + e.getMessage());
        }
        return 0;
    }

    // Helper method
    private Course extractCourseFromResultSet(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setCourseCode(rs.getString("course_code"));
        course.setCourseName(rs.getString("course_name"));
        course.setDescription(rs.getString("description"));
        course.setCredits(rs.getInt("credits"));
        course.setInstructor(rs.getString("instructor"));
        course.setMaxStudents(rs.getInt("max_students"));
        course.setEnrolledStudents(rs.getInt("enrolled_students"));
        course.setStatus(rs.getString("status"));
        return course;
    }
}