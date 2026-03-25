// ===== Enrollment Management Functions =====

// Get all enrollments
function getAllEnrollments() {
    const enrollments = localStorage.getItem('enrollments');
    return enrollments ? JSON.parse(enrollments) : [];
}

// Get enrollment by ID
function getEnrollmentById(enrollmentId) {
    const enrollments = getAllEnrollments();
    return enrollments.find(e => e.enrollmentId === parseInt(enrollmentId));
}

// Get enrollments by student ID
function getEnrollmentsByStudentId(studentId) {
    const enrollments = getAllEnrollments();
    return enrollments.filter(e => e.studentId === parseInt(studentId));
}

// Get enrollments by course ID
function getEnrollmentsByCourseId(courseId) {
    const enrollments = getAllEnrollments();
    return enrollments.filter(e => e.courseId === parseInt(courseId));
}

// Check if student is already enrolled
function isStudentEnrolled(studentId, courseId) {
    const enrollments = getAllEnrollments();
    return enrollments.some(e => 
        e.studentId === parseInt(studentId) && 
        e.courseId === parseInt(courseId) &&
        e.status === 'ENROLLED'
    );
}

// Enroll student in course
function enrollStudent(studentId, courseId) {
    // Check if already enrolled
    if (isStudentEnrolled(studentId, courseId)) {
        showToast('❌ Student is already enrolled in this course!', 'danger');
        return false;
    }
    
    // Check if course is full
    const course = getCourseById(courseId);
    if (!course) {
        showToast('❌ Course not found!', 'danger');
        return false;
    }
    
    if (course.enrolledStudents >= course.maxStudents) {
        showToast('❌ Course is full! Cannot enroll.', 'danger');
        return false;
    }
    
    // Check if student exists
    const student = getStudentById(studentId);
    if (!student) {
        showToast('❌ Student not found!', 'danger');
        return false;
    }
    
    // Create enrollment
    const enrollments = getAllEnrollments();
    let enrollmentIdCounter = parseInt(localStorage.getItem('enrollmentIdCounter')) || 1;
    
    const newEnrollment = {
        enrollmentId: enrollmentIdCounter,
        studentId: parseInt(studentId),
        courseId: parseInt(courseId),
        enrollmentDate: new Date().toISOString().split('T')[0],
        grade: 'N/A',
        status: 'ENROLLED'
    };
    
    enrollments.push(newEnrollment);
    localStorage.setItem('enrollments', JSON.stringify(enrollments));
    localStorage.setItem('enrollmentIdCounter', (enrollmentIdCounter + 1).toString());
    
    // Update course enrolled count
    updateEnrolledCount(courseId, true);
    
    showToast('✅ Student enrolled successfully!', 'success');
    return true;
}

// Drop enrollment
function dropEnrollment(enrollmentId) {
    const enrollments = getAllEnrollments();
    const enrollment = getEnrollmentById(enrollmentId);
    
    if (!enrollment) {
        showToast('❌ Enrollment not found!', 'danger');
        return false;
    }
    
    // Remove enrollment
    const updatedEnrollments = enrollments.filter(e => e.enrollmentId !== parseInt(enrollmentId));
    localStorage.setItem('enrollments', JSON.stringify(updatedEnrollments));
    
    // Update course enrolled count
    updateEnrolledCount(enrollment.courseId, false);
    
    showToast('✅ Enrollment dropped successfully!', 'success');
    return true;
}

// Update grade
function updateGrade(enrollmentId, grade) {
    const enrollments = getAllEnrollments();
    const index = enrollments.findIndex(e => e.enrollmentId === parseInt(enrollmentId));
    
    if (index === -1) {
        showToast('❌ Enrollment not found!', 'danger');
        return false;
    }
    
    enrollments[index].grade = grade;
    localStorage.setItem('enrollments', JSON.stringify(enrollments));
    
    showToast('✅ Grade updated successfully!', 'success');
    return true;
}

// Update enrollment status
function updateEnrollmentStatus(enrollmentId, status) {
    const enrollments = getAllEnrollments();
    const index = enrollments.findIndex(e => e.enrollmentId === parseInt(enrollmentId));
    
    if (index === -1) {
        showToast('❌ Enrollment not found!', 'danger');
        return false;
    }
    
    const oldStatus = enrollments[index].status;
    enrollments[index].status = status;
    localStorage.setItem('enrollments', JSON.stringify(enrollments));
    
    // Update course count if status changes
    if (oldStatus === 'ENROLLED' && status !== 'ENROLLED') {
        updateEnrolledCount(enrollments[index].courseId, false);
    } else if (oldStatus !== 'ENROLLED' && status === 'ENROLLED') {
        updateEnrolledCount(enrollments[index].courseId, true);
    }
    
    showToast('✅ Status updated successfully!', 'success');
    return true;
}

// Get enrollment statistics
function getEnrollmentStats() {
    const enrollments = getAllEnrollments();
    
    return {
        total: enrollments.length,
        enrolled: enrollments.filter(e => e.status === 'ENROLLED').length,
        completed: enrollments.filter(e => e.status === 'COMPLETED').length,
        dropped: enrollments.filter(e => e.status === 'DROPPED').length,
        graded: enrollments.filter(e => e.grade !== 'N/A').length
    };
}

// Get enrollment with full details
function getEnrollmentWithDetails(enrollment) {
    const student = getStudentById(enrollment.studentId);
    const course = getCourseById(enrollment.courseId);
    
    return {
        ...enrollment,
        rollNumber: student ? student.rollNumber : 'N/A',
        studentName: student ? `${student.firstName} ${student.lastName}` : 'Unknown',
        courseCode: course ? course.courseCode : 'N/A',
        courseName: course ? course.courseName : 'Unknown',
        credits: course ? course.credits : 0
    };
}

// Get all enrollments with details
function getAllEnrollmentsWithDetails() {
    const enrollments = getAllEnrollments();
    return enrollments.map(e => getEnrollmentWithDetails(e));
}

// Display enrollment in table
function displayEnrollmentInTable(enrollment) {
    const details = getEnrollmentWithDetails(enrollment);
    const statusClass = details.status === 'ENROLLED' ? 'success' : 
                        details.status === 'COMPLETED' ? 'primary' : 'danger';
    const gradeClass = details.grade === 'N/A' ? 'secondary' : 
                      details.grade.startsWith('A') ? 'success' :
                      details.grade.startsWith('B') ? 'info' :
                      details.grade.startsWith('C') ? 'warning' : 'danger';
    
    return `
        <tr data-enrollment-id="${details.enrollmentId}">
            <td>${details.enrollmentId}</td>
            <td><strong>${details.rollNumber}</strong></td>
            <td>${details.studentName}</td>
            <td><strong>${details.courseCode}</strong></td>
            <td>${details.courseName}</td>
            <td>${formatDate(details.enrollmentDate)}</td>
            <td><span class="badge bg-${gradeClass}">${details.grade}</span></td>
            <td><span class="badge bg-${statusClass}">${details.status}</span></td>
            <td>
                <button class="btn btn-sm btn-info" onclick="viewEnrollment(${details.enrollmentId})">
                    <i class="fas fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-warning" onclick="editGrade(${details.enrollmentId})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-danger" onclick="confirmDropEnrollment(${details.enrollmentId})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `;
}

// Export enrollments to CSV
function exportEnrollmentsToCSV() {
    const enrollments = getAllEnrollmentsWithDetails();
    
    if (enrollments.length === 0) {
        showToast('❌ No enrollments to export!', 'warning');
        return;
    }
    
    let csv = 'Enrollment ID,Roll Number,Student Name,Course Code,Course Name,Enrollment Date,Grade,Status\n';
    
    enrollments.forEach(e => {
        csv += `${e.enrollmentId},${e.rollNumber},"${e.studentName}",${e.courseCode},"${e.courseName}",${e.enrollmentDate},${e.grade},${e.status}\n`;
    });
    
    const blob = new Blob([csv], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `enrollments_${new Date().toISOString().split('T')[0]}.csv`;
    a.click();
    window.URL.revokeObjectURL(url);
    
    showToast('✅ Enrollments exported successfully!', 'success');
}

// Get grade options
function getGradeOptions() {
    return ['A+', 'A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'C-', 'D', 'F', 'N/A'];
}

// Get status options
function getStatusOptions() {
    return ['ENROLLED', 'COMPLETED', 'DROPPED'];
}

// Get student's courses with details
function getStudentCoursesWithDetails(studentId) {
    const enrollments = getEnrollmentsByStudentId(studentId);
    return enrollments.map(e => getEnrollmentWithDetails(e));
}

// Get course's students with details
function getCourseStudentsWithDetails(courseId) {
    const enrollments = getEnrollmentsByCourseId(courseId);
    return enrollments.map(e => getEnrollmentWithDetails(e));
}