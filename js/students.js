// ===== Student Management Functions =====

// Get all students
function getAllStudents() {
    const students = localStorage.getItem('students');
    return students ? JSON.parse(students) : [];
}

// Get student by ID
function getStudentById(studentId) {
    const students = getAllStudents();
    return students.find(s => s.studentId === parseInt(studentId));
}

// Get student by Roll Number
function getStudentByRollNumber(rollNumber) {
    const students = getAllStudents();
    return students.find(s => s.rollNumber.toLowerCase() === rollNumber.toLowerCase());
}

// Add new student
function addStudent(studentData) {
    const students = getAllStudents();
    
    // Check if roll number already exists
    if (getStudentByRollNumber(studentData.rollNumber)) {
        showToast('❌ Roll Number already exists!', 'danger');
        return false;
    }
    
    // Check if email already exists
    if (students.find(s => s.email.toLowerCase() === studentData.email.toLowerCase())) {
        showToast('❌ Email already exists!', 'danger');
        return false;
    }
    
    // Get next ID
    let studentIdCounter = parseInt(localStorage.getItem('studentIdCounter')) || 1;
    studentData.studentId = studentIdCounter;
    
    // Add student
    students.push(studentData);
    localStorage.setItem('students', JSON.stringify(students));
    localStorage.setItem('studentIdCounter', (studentIdCounter + 1).toString());
    
    showToast('✅ Student added successfully!', 'success');
    return true;
}

// Update student
function updateStudent(studentId, studentData) {
    const students = getAllStudents();
    const index = students.findIndex(s => s.studentId === parseInt(studentId));
    
    if (index === -1) {
        showToast('❌ Student not found!', 'danger');
        return false;
    }
    
    // Keep the same ID and roll number
    studentData.studentId = parseInt(studentId);
    studentData.rollNumber = students[index].rollNumber;
    studentData.enrollmentDate = students[index].enrollmentDate;
    
    students[index] = studentData;
    localStorage.setItem('students', JSON.stringify(students));
    
    showToast('✅ Student updated successfully!', 'success');
    return true;
}

// Delete student
function deleteStudent(studentId) {
    // First, delete all enrollments of this student
    const enrollments = getAllEnrollments();
    const updatedEnrollments = enrollments.filter(e => e.studentId !== parseInt(studentId));
    localStorage.setItem('enrollments', JSON.stringify(updatedEnrollments));
    
    // Then delete the student
    const students = getAllStudents();
    const updatedStudents = students.filter(s => s.studentId !== parseInt(studentId));
    localStorage.setItem('students', JSON.stringify(updatedStudents));
    
    showToast('✅ Student deleted successfully!', 'success');
    return true;
}

// Search students by name
function searchStudentsByName(name) {
    const students = getAllStudents();
    const searchTerm = name.toLowerCase();
    return students.filter(s => 
        s.firstName.toLowerCase().includes(searchTerm) || 
        s.lastName.toLowerCase().includes(searchTerm) ||
        (s.firstName + ' ' + s.lastName).toLowerCase().includes(searchTerm)
    );
}

// Get active students
function getActiveStudents() {
    const students = getAllStudents();
    return students.filter(s => s.status === 'ACTIVE');
}

// Get student statistics
function getStudentStats() {
    const students = getAllStudents();
    return {
        total: students.length,
        active: students.filter(s => s.status === 'ACTIVE').length,
        inactive: students.filter(s => s.status === 'INACTIVE').length,
        graduated: students.filter(s => s.status === 'GRADUATED').length,
        male: students.filter(s => s.gender === 'Male').length,
        female: students.filter(s => s.gender === 'Female').length
    };
}

// Display student in table
function displayStudentInTable(student) {
    const statusClass = student.status === 'ACTIVE' ? 'success' : 
                       student.status === 'INACTIVE' ? 'warning' : 'secondary';
    
    return `
        <tr data-student-id="${student.studentId}">
            <td><strong>${student.rollNumber}</strong></td>
            <td>${student.firstName} ${student.lastName}</td>
            <td>${student.email}</td>
            <td>${student.phone}</td>
            <td>${student.gender}</td>
            <td><span class="badge bg-${statusClass}">${student.status}</span></td>
            <td>
                <button class="btn btn-sm btn-info" onclick="viewStudent(${student.studentId})">
                    <i class="fas fa-eye"></i>
                </button>
                <button class="btn btn-sm btn-warning" onclick="editStudent(${student.studentId})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-danger" onclick="confirmDeleteStudent(${student.studentId})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `;
}

// Validate student form
function validateStudentForm(formData) {
    const errors = [];
    
    if (!formData.rollNumber || formData.rollNumber.trim() === '') {
        errors.push('Roll Number is required');
    }
    
    if (!formData.firstName || formData.firstName.trim() === '') {
        errors.push('First Name is required');
    }
    
    if (!formData.lastName || formData.lastName.trim() === '') {
        errors.push('Last Name is required');
    }
    
    if (!formData.email || !validateEmail(formData.email)) {
        errors.push('Valid Email is required');
    }
    
    if (!formData.phone || !validatePhone(formData.phone)) {
        errors.push('Valid 10-digit Phone Number is required');
    }
    
    if (!formData.dateOfBirth) {
        errors.push('Date of Birth is required');
    }
    
    if (!formData.gender) {
        errors.push('Gender is required');
    }
    
    if (errors.length > 0) {
        showToast('❌ ' + errors.join('<br>'), 'danger');
        return false;
    }
    
    return true;
}

// Export student data to CSV
function exportStudentsToCSV() {
    const students = getAllStudents();
    
    if (students.length === 0) {
        showToast('❌ No students to export!', 'warning');
        return;
    }
    
    let csv = 'Roll Number,First Name,Last Name,Email,Phone,DOB,Gender,Address,Enrollment Date,Status\n';
    
    students.forEach(student => {
        csv += `${student.rollNumber},${student.firstName},${student.lastName},${student.email},${student.phone},${student.dateOfBirth},${student.gender},"${student.address}",${student.enrollmentDate},${student.status}\n`;
    });
    
    // Create download link
    const blob = new Blob([csv], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `students_${new Date().toISOString().split('T')[0]}.csv`;
    a.click();
    window.URL.revokeObjectURL(url);
    
    showToast('✅ Students exported successfully!', 'success');
}

// Get student's enrolled courses count
function getStudentEnrolledCoursesCount(studentId) {
    const enrollments = getAllEnrollments();
    return enrollments.filter(e => e.studentId === parseInt(studentId) && e.status === 'ENROLLED').length;
}

// Calculate student's GPA
function calculateStudentGPA(studentId) {
    const enrollments = getAllEnrollments().filter(e => e.studentId === parseInt(studentId));
    
    if (enrollments.length === 0) return 'N/A';
    
    const gradePoints = {
        'A+': 10, 'A': 9, 'A-': 8.5,
        'B+': 8, 'B': 7, 'B-': 6.5,
        'C+': 6, 'C': 5, 'C-': 4.5,
        'D': 4, 'F': 0, 'N/A': null
    };
    
    let totalPoints = 0;
    let totalCourses = 0;
    
    enrollments.forEach(e => {
        if (e.grade && gradePoints[e.grade] !== null && gradePoints[e.grade] !== undefined) {
            totalPoints += gradePoints[e.grade];
            totalCourses++;
        }
    });
    
    if (totalCourses === 0) return 'N/A';
    
    return (totalPoints / totalCourses).toFixed(2);
}