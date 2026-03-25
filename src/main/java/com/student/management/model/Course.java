package com.student.management.model;

public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private String description;
    private int credits;
    private String instructor;
    private int maxStudents;
    private int enrolledStudents;
    private String status; // ACTIVE, INACTIVE

    // Constructors
    public Course() {
        this.status = "ACTIVE";
        this.enrolledStudents = 0;
    }

    public Course(String courseCode, String courseName, String description, 
                  int credits, String instructor, int maxStudents) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
        this.instructor = instructor;
        this.maxStudents = maxStudents;
        this.status = "ACTIVE";
        this.enrolledStudents = 0;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFull() {
        return enrolledStudents >= maxStudents;
    }

    public int getAvailableSeats() {
        return maxStudents - enrolledStudents;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-10s %-25s %-20s %-3d %-5d/%-5d %-10s", 
                           courseId, courseCode, courseName, instructor, 
                           credits, enrolledStudents, maxStudents, status);
    }
}