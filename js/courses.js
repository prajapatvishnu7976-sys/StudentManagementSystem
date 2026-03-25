// ===== Course Management Functions =====

// Get all courses
function getAllCourses() {
    const courses = localStorage.getItem('courses');
    return courses ? JSON.parse(courses) : [];
}

// Get course by ID
function getCourseById(courseId) {
    const courses = getAllCourses();
    return courses.find(c => c.courseId === parseInt(courseId));
}

// Get course by Code
function getCourseByCode(courseCode) {
    const courses = getAllCourses();
    return courses.find(c => c.courseCode.toLowerCase() === courseCode.toLowerCase());
}

// Add new course
function addCourse(courseData) {
    const courses = getAllCourses();
    
    // Check if course code already exists
    if (getCourseByCode(courseData.courseCode)) {
        showToast('❌ Course Code already exists!', 'danger');
        return false;
    }
    
    // Get next ID
    let courseIdCounter = parseInt(localStorage.getItem('courseIdCounter')) || 1;
    courseData.courseId = courseIdCounter;
    courseData.enrolledStudents = 0;
    
    // Add course
    courses.push(courseData);
    localStorage.setItem('courses', JSON.stringify(courses));
    localStorage.setItem('courseIdCounter', (courseIdCounter + 1).toString());
    
    showToast('✅ Course added successfully!', 'success');
    return true;
}

// Update course
function updateCourse(courseId, courseData) {
    const courses = getAllCourses();
    const index = courses.findIndex(c => c.courseId === parseInt(courseId));
    
    if (index === -1) {
        showToast('❌ Course not found!', 'danger');
        return false;
    }
    
    // Keep the same ID, code, and enrolled count
    courseData.courseId = parseInt(courseId);
    courseData.courseCode = courses[index].courseCode;
    courseData.enrolledStudents = courses[index].enrolledStudents;
    
    courses[index] = courseData;
    localStorage.setItem('courses', JSON.stringify(courses));
    
    showToast('✅ Course updated successfully!', 'success');
    return true;
}

// Delete course
function deleteCourse(courseId) {
    // Check if any students are enrolled
    const enrollments = getAllEnrollments();
    const courseEnrollments = enrollments.filter(e => e.courseId === parseInt(courseId));
    
    if (courseEnrollments.length > 0) {
        showToast('❌ Cannot delete! Students are enrolled in this course.', 'danger');
        return false;
    }
    
    // Delete the course
    const courses = getAllCourses();
    const updatedCourses = courses.filter(c => c.courseId !== parseInt(courseId));
    localStorage.setItem('courses', JSON.stringify(updatedCourses));
    
    showToast('✅ Course deleted successfully!', 'success');
    return true;
}

// Search courses by name
function searchCoursesByName(name) {
    const courses = getAllCourses();
    const searchTerm = name.toLowerCase();
    return courses.filter(c => 
        c.courseName.toLowerCase().includes(searchTerm) || 
        c.courseCode.toLowerCase().includes(searchTerm) ||
        c.instructor.toLowerCase().includes(searchTerm)
    );
}

// Get active courses
function getActiveCourses() {
    const courses = getAllCourses();
    return courses.filter(c => c.status === 'ACTIVE');
}

// Get available courses (with open seats)
function getAvailableCourses() {
    const courses = getActiveCourses();
    return courses.filter(c => c.enrolledStudents < c.maxStudents);
}

// Update enrolled students count
function updateEnrolledCount(courseId, increment = true) {
    const courses = getAllCourses();
    const index = courses.findIndex(c => c.courseId === parseInt(courseId));
    
    if (index !== -1) {
        if (increment) {
            courses[index].enrolledStudents++;
        } else {
            courses[index].enrolledStudents = Math.max(0, courses[index].enrolledStudents - 1);
        }
        localStorage.setItem('courses', JSON.stringify(courses));
    }
}

// Get course statistics
function getCourseStats() {
    const courses = getAllCourses();
    const totalSeats = courses.reduce((sum, c) => sum + c.maxStudents, 0);
    const filledSeats = courses.reduce((sum, c) => sum + c.enrolledStudents, 0);
    
    return {
        total: courses.length,
        active: courses.filter(c => c.status === 'ACTIVE').length,
        inactive: courses.filter(c => c.status === 'INACTIVE').length,
        totalSeats: totalSeats,
        filledSeats: filledSeats,
        availableSeats: totalSeats - filledSeats,
        averageEnrollment: courses.length > 0 ? Math.round(filledSeats / courses.length) : 0
    };
}

// Display course in table
function displayCourseInTable(course) {
    const statusClass = course.status === 'ACTIVE' ? 'success' : 'secondary';
    const enrollmentPercent = Math.round((course.enrolledStudents / course.maxStudents) * 100);
    const progressClass = enrollmentPercent > 80 ? 'danger' : enrollmentPercent > 50 ? 'warning' : 'success';
    
    return `
        <tr data-course-id="${course.courseId}">
            <td><strong>${course.courseCode}</strong></td>
            <td>${course.courseName}</td>
            <td>${course.instructor}</td>
            <td>${course.credits}</td>
            <td>
                <div class="d-flex align-items-center">
                    <div class="progress flex-grow-1 me-2" style="height: 8px;">
                        <div class="progress-bar bg-${progressClass}" style="width: ${enrollmentPercent}%"></div>
                    </div>
                    <small>${course.enrolledStudents}/${course.maxStudents}</small>
                </div>
            </td>
            <td><span class="badge bg-${statusClass}">${course.status}</span></td>
            <td>
                <button class="btn btn-sm btn-info" onclick="viewCourse(${course.courseId})">
                    <i class="fas fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-warning" onclick="editCourse(${course.courseId})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-danger" onclick="confirmDeleteCourse(${course.courseId})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `;
}

// Validate course form
function validateCourseForm(formData) {
    const errors = [];
    
    if (!formData.courseCode || formData.courseCode.trim() === '') {
        errors.push('Course Code is required');
    }
    
    if (!formData.courseName || formData.courseName.trim() === '') {
        errors.push('Course Name is required');
    }
    
    if (!formData.instructor || formData.instructor.trim() === '') {
        errors.push('Instructor Name is required');
    }
    
    if (!formData.credits || formData.credits < 1 || formData.credits > 6) {
        errors.push('Credits must be between 1 and 6');
    }
    
    if (!formData.maxStudents || formData.maxStudents < 1 || formData.maxStudents > 200) {
        errors.push('Max Students must be between 1 and 200');
    }
    
    if (errors.length > 0) {
        showToast('❌ ' + errors.join('<br>'), 'danger');
        return false;
    }
    
    return true;
}

// Export courses to CSV
function exportCoursesToCSV() {
    const courses = getAllCourses();
    
    if (courses.length === 0) {
        showToast('❌ No courses to export!', 'warning');
        return;
    }
    
    let csv = 'Course Code,Course Name,Description,Credits,Instructor,Max Students,Enrolled Students,Status\n';
    
    courses.forEach(course => {
        csv += `${course.courseCode},${course.courseName},"${course.description}",${course.credits},${course.instructor},${course.maxStudents},${course.enrolledStudents},${course.status}\n`;
    });
    
    const blob = new Blob([csv], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `courses_${new Date().toISOString().split('T')[0]}.csv`;
    a.click();
    window.URL.revokeObjectURL(url);
    
    showToast('✅ Courses exported successfully!', 'success');
}

// Check if course is full
function isCourseFull(courseId) {
    const course = getCourseById(courseId);
    return course ? course.enrolledStudents >= course.maxStudents : true;
}

// Get courses by instructor
function getCoursesByInstructor(instructorName) {
    const courses = getAllCourses();
    return courses.filter(c => c.instructor.toLowerCase().includes(instructorName.toLowerCase()));
}