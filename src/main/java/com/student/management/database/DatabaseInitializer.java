package com.student.management.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        
        try (Statement stmt = conn.createStatement()) {
            
            // Students Table
            String createStudentsTable = """
                CREATE TABLE IF NOT EXISTS students (
                    student_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    roll_number VARCHAR(20) UNIQUE NOT NULL,
                    first_name VARCHAR(50) NOT NULL,
                    last_name VARCHAR(50) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    phone VARCHAR(15),
                    date_of_birth DATE,
                    gender VARCHAR(10),
                    address TEXT,
                    enrollment_date DATE DEFAULT CURRENT_DATE,
                    status VARCHAR(20) DEFAULT 'ACTIVE'
                )
            """;
            stmt.execute(createStudentsTable);

            // Courses Table
            String createCoursesTable = """
                CREATE TABLE IF NOT EXISTS courses (
                    course_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    course_code VARCHAR(20) UNIQUE NOT NULL,
                    course_name VARCHAR(100) NOT NULL,
                    description TEXT,
                    credits INTEGER DEFAULT 3,
                    instructor VARCHAR(100),
                    max_students INTEGER DEFAULT 50,
                    enrolled_students INTEGER DEFAULT 0,
                    status VARCHAR(20) DEFAULT 'ACTIVE'
                )
            """;
            stmt.execute(createCoursesTable);

            // Enrollments Table
            String createEnrollmentsTable = """
                CREATE TABLE IF NOT EXISTS enrollments (
                    enrollment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_id INTEGER NOT NULL,
                    course_id INTEGER NOT NULL,
                    enrollment_date DATE DEFAULT CURRENT_DATE,
                    grade VARCHAR(5) DEFAULT 'N/A',
                    status VARCHAR(20) DEFAULT 'ENROLLED',
                    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
                    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
                    UNIQUE(student_id, course_id)
                )
            """;
            stmt.execute(createEnrollmentsTable);

            System.out.println("✅ Database tables created successfully!");
            
        } catch (SQLException e) {
            System.err.println("❌ Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void insertSampleData() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        
        try (Statement stmt = conn.createStatement()) {
            
            // Sample Students
            stmt.execute("INSERT OR IGNORE INTO students (roll_number, first_name, last_name, email, phone, date_of_birth, gender, address) VALUES " +
                "('2024001', 'Rahul', 'Sharma', 'rahul.sharma@email.com', '9876543210', '2005-03-15', 'Male', 'Delhi'), " +
                "('2024002', 'Priya', 'Singh', 'priya.singh@email.com', '9876543211', '2005-07-22', 'Female', 'Mumbai'), " +
                "('2024003', 'Amit', 'Kumar', 'amit.kumar@email.com', '9876543212', '2005-01-10', 'Male', 'Bangalore')");

            // Sample Courses
            stmt.execute("INSERT OR IGNORE INTO courses (course_code, course_name, description, credits, instructor, max_students) VALUES " +
                "('CS101', 'Introduction to Programming', 'Basic programming concepts using Java', 4, 'Dr. Rajesh Verma', 60), " +
                "('CS102', 'Data Structures', 'Arrays, Lists, Trees, Graphs', 4, 'Dr. Sneha Patel', 50), " +
                "('MATH101', 'Engineering Mathematics', 'Calculus and Linear Algebra', 3, 'Prof. Anil Kumar', 80), " +
                "('CS201', 'Database Management', 'SQL, NoSQL, Database Design', 4, 'Dr. Vikram Singh', 45)");

            System.out.println("✅ Sample data inserted successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error inserting sample data: " + e.getMessage());
        }
    }
}