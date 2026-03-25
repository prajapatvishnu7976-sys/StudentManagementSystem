@echo off
cls
echo ========================================
echo   STUDENT MANAGEMENT SYSTEM
echo ========================================
echo.

REM Clean and create output directory
if exist out rmdir /s /q out
mkdir out

echo Compiling...
javac -encoding UTF-8 -cp "lib/*" -d out ^
src/main/java/com/student/management/model/Student.java ^
src/main/java/com/student/management/model/Course.java ^
src/main/java/com/student/management/model/Enrollment.java ^
src/main/java/com/student/management/database/DatabaseConnection.java ^
src/main/java/com/student/management/database/DatabaseInitializer.java ^
src/main/java/com/student/management/dao/StudentDAO.java ^
src/main/java/com/student/management/dao/CourseDAO.java ^
src/main/java/com/student/management/dao/EnrollmentDAO.java ^
src/main/java/com/student/management/util/InputValidator.java ^
src/main/java/com/student/management/util/DateUtil.java ^
src/main/java/com/student/management/service/StudentService.java ^
src/main/java/com/student/management/service/CourseService.java ^
src/main/java/com/student/management/service/EnrollmentService.java ^
src/main/java/com/student/management/ui/MainMenu.java ^
src/main/java/com/student/management/ui/StudentMenu.java ^
src/main/java/com/student/management/ui/CourseMenu.java ^
src/main/java/com/student/management/ui/ReportMenu.java ^
src/main/java/com/student/management/Main.java

if %errorlevel% neq 0 (
    echo.
    echo Compilation failed!
    pause
    exit /b
)

echo.
echo Compilation successful!
echo Running application...
echo.

java -cp "lib/*;out" com.student.management.Main

pause