package com.student.management.service;

import com.student.management.dao.CourseDAO;
import com.student.management.dao.EnrollmentDAO;
import com.student.management.model.Course;

import java.util.List;

public class CourseService {
    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;

    public CourseService() {
        this.courseDAO = new CourseDAO();
        this.enrollmentDAO = new EnrollmentDAO();
    }

    // Add new course
    public boolean addCourse(Course course) {
        // Check if course code already exists
        if (courseDAO.getCourseByCode(course.getCourseCode()) != null) {
            System.out.println("❌ Error: Course code already exists!");
            return false;
        }
        
        boolean result = courseDAO.addCourse(course);
        if (result) {
            System.out.println("✅ Course added successfully!");
        }
        return result;
    }

    // Get course by ID
    public Course getCourseById(int courseId) {
        return courseDAO.getCourseById(courseId);
    }

    // Get course by code
    public Course getCourseByCode(String courseCode) {
        return courseDAO.getCourseByCode(courseCode);
    }

    // Get all courses
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    // Get active courses
    public List<Course> getActiveCourses() {
        return courseDAO.getActiveCourses();
    }

    // Update course
    public boolean updateCourse(Course course) {
        boolean result = courseDAO.updateCourse(course);
        if (result) {
            System.out.println("✅ Course updated successfully!");
        }
        return result;
    }

    // Delete course
    public boolean deleteCourse(int courseId) {
        boolean result = courseDAO.deleteCourse(courseId);
        if (result) {
            System.out.println("✅ Course deleted successfully!");
        }
        return result;
    }

    // Search courses
    public List<Course> searchCourses(String name) {
        return courseDAO.searchCoursesByName(name);
    }

    // Get total count
    public int getTotalCourseCount() {
        return courseDAO.getTotalCourseCount();
    }

    // Update enrolled count
    public void updateEnrolledCount(int courseId) {
        int count = enrollmentDAO.getEnrollmentCountByCourse(courseId);
        courseDAO.updateEnrolledCount(courseId, count);
    }

    // Check if course is available for enrollment
    public boolean isCourseAvailable(int courseId) {
        Course course = getCourseById(courseId);
        if (course == null) return false;
        return !course.isFull() && "ACTIVE".equals(course.getStatus());
    }

    // Display all courses in table format
    public void displayAllCourses() {
        List<Course> courses = getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("\n📭 No courses found in the database.");
            return;
        }

        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                      📖 ALL COURSES LIST                                          ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-4s ║ %-10s ║ %-25s ║ %-20s ║ %-4s ║ %-11s ║ %-8s ║%n", 
                         "ID", "CODE", "COURSE NAME", "INSTRUCTOR", "CR", "ENROLLED", "STATUS");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════════════════╣");
        
        for (Course course : courses) {
            String enrolled = course.getEnrolledStudents() + "/" + course.getMaxStudents();
            System.out.printf("║ %-4d ║ %-10s ║ %-25s ║ %-20s ║ %-4d ║ %-11s ║ %-8s ║%n",
                            course.getCourseId(),
                            course.getCourseCode(),
                            truncate(course.getCourseName(), 25),
                            truncate(course.getInstructor(), 20),
                            course.getCredits(),
                            enrolled,
                            course.getStatus());
        }
        
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Total Courses: " + courses.size());
    }

    // Display single course details
    public void displayCourseDetails(Course course) {
        if (course == null) {
            System.out.println("❌ Course not found!");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║               📖 COURSE DETAILS                       ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║  Course ID     : %-35d  ║%n", course.getCourseId());
        System.out.printf("║  Course Code   : %-35s  ║%n", course.getCourseCode());
        System.out.printf("║  Course Name   : %-35s  ║%n", course.getCourseName());
        System.out.printf("║  Description   : %-35s  ║%n", truncate(course.getDescription(), 35));
        System.out.printf("║  Instructor    : %-35s  ║%n", course.getInstructor());
        System.out.printf("║  Credits       : %-35d  ║%n", course.getCredits());
        System.out.printf("║  Enrolled      : %-35s  ║%n", course.getEnrolledStudents() + " / " + course.getMaxStudents());
        System.out.printf("║  Available     : %-35d  ║%n", course.getAvailableSeats());
        System.out.printf("║  Status        : %-35s  ║%n", course.getStatus());
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    // Helper method
    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}