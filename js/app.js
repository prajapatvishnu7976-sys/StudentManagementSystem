// ===== UTILITY FUNCTIONS (MUST BE AT TOP) =====

// Validate Email
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Validate Phone (10 digits)
function validatePhone(phone) {
    const re = /^[0-9]{10}$/;
    return re.test(phone);
}

// Format Date
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-IN', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

// Show Toast Notification
function showToast(message, type = 'success') {
    // Remove existing toasts
    const existingToasts = document.querySelectorAll('.custom-toast');
    existingToasts.forEach(t => t.remove());
    
    const toast = document.createElement('div');
    toast.className = `alert alert-${type} alert-dismissible fade show position-fixed custom-toast`;
    toast.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px; box-shadow: 0 5px 15px rgba(0,0,0,0.2);';
    toast.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    document.body.appendChild(toast);
    
    // Auto remove after 3 seconds
    setTimeout(() => {
        if (toast.parentNode) {
            toast.remove();
        }
    }, 3000);
}

// ===== DATABASE INITIALIZATION =====

function initializeDatabase() {
    console.log('Initializing database...');
    
    // Check if data exists, if not create sample data
    if (!localStorage.getItem('students')) {
        const sampleStudents = [
            {
                studentId: 1,
                rollNumber: '2024001',
                firstName: 'Rahul',
                lastName: 'Sharma',
                email: 'rahul.sharma@email.com',
                phone: '9876543210',
                dateOfBirth: '2005-03-15',
                gender: 'Male',
                address: 'Delhi, India',
                enrollmentDate: '2024-01-15',
                status: 'ACTIVE'
            },
            {
                studentId: 2,
                rollNumber: '2024002',
                firstName: 'Priya',
                lastName: 'Singh',
                email: 'priya.singh@email.com',
                phone: '9876543211',
                dateOfBirth: '2005-07-22',
                gender: 'Female',
                address: 'Mumbai, India',
                enrollmentDate: '2024-01-15',
                status: 'ACTIVE'
            },
            {
                studentId: 3,
                rollNumber: '2024003',
                firstName: 'Amit',
                lastName: 'Kumar',
                email: 'amit.kumar@email.com',
                phone: '9876543212',
                dateOfBirth: '2005-01-10',
                gender: 'Male',
                address: 'Bangalore, India',
                enrollmentDate: '2024-01-15',
                status: 'ACTIVE'
            },
            {
                studentId: 4,
                rollNumber: '2024004',
                firstName: 'Sneha',
                lastName: 'Patel',
                email: 'sneha.patel@email.com',
                phone: '9876543213',
                dateOfBirth: '2005-05-18',
                gender: 'Female',
                address: 'Ahmedabad, India',
                enrollmentDate: '2024-01-16',
                status: 'ACTIVE'
            },
            {
                studentId: 5,
                rollNumber: '2024005',
                firstName: 'Vikram',
                lastName: 'Reddy',
                email: 'vikram.reddy@email.com',
                phone: '9876543214',
                dateOfBirth: '2004-11-25',
                gender: 'Male',
                address: 'Hyderabad, India',
                enrollmentDate: '2024-01-16',
                status: 'ACTIVE'
            }
        ];
        localStorage.setItem('students', JSON.stringify(sampleStudents));
        console.log('Sample students created');
    }

    if (!localStorage.getItem('courses')) {
        const sampleCourses = [
            {
                courseId: 1,
                courseCode: 'CS101',
                courseName: 'Introduction to Programming',
                description: 'Basic programming concepts using Java',
                credits: 4,
                instructor: 'Dr. Rajesh Verma',
                maxStudents: 60,
                enrolledStudents: 3,
                status: 'ACTIVE'
            },
            {
                courseId: 2,
                courseCode: 'CS102',
                courseName: 'Data Structures',
                description: 'Arrays, Lists, Trees, Graphs',
                credits: 4,
                instructor: 'Dr. Sneha Patel',
                maxStudents: 50,
                enrolledStudents: 2,
                status: 'ACTIVE'
            },
            {
                courseId: 3,
                courseCode: 'MATH101',
                courseName: 'Engineering Mathematics',
                description: 'Calculus and Linear Algebra',
                credits: 3,
                instructor: 'Prof. Anil Kumar',
                maxStudents: 80,
                enrolledStudents: 2,
                status: 'ACTIVE'
            },
            {
                courseId: 4,
                courseCode: 'CS201',
                courseName: 'Database Management',
                description: 'SQL, NoSQL, Database Design',
                credits: 4,
                instructor: 'Dr. Vikram Singh',
                maxStudents: 45,
                enrolledStudents: 1,
                status: 'ACTIVE'
            },
            {
                courseId: 5,
                courseCode: 'CS301',
                courseName: 'Web Development',
                description: 'HTML, CSS, JavaScript, React',
                credits: 4,
                instructor: 'Prof. Meera Sharma',
                maxStudents: 40,
                enrolledStudents: 0,
                status: 'ACTIVE'
            }
        ];
        localStorage.setItem('courses', JSON.stringify(sampleCourses));
        console.log('Sample courses created');
    }

    if (!localStorage.getItem('enrollments')) {
        const sampleEnrollments = [
            {
                enrollmentId: 1,
                studentId: 1,
                courseId: 1,
                enrollmentDate: '2024-01-20',
                grade: 'A',
                status: 'ENROLLED'
            },
            {
                enrollmentId: 2,
                studentId: 1,
                courseId: 3,
                enrollmentDate: '2024-01-20',
                grade: 'B+',
                status: 'ENROLLED'
            },
            {
                enrollmentId: 3,
                studentId: 2,
                courseId: 2,
                enrollmentDate: '2024-01-21',
                grade: 'A-',
                status: 'ENROLLED'
            },
            {
                enrollmentId: 4,
                studentId: 2,
                courseId: 1,
                enrollmentDate: '2024-01-21',
                grade: 'B',
                status: 'ENROLLED'
            },
            {
                enrollmentId: 5,
                studentId: 3,
                courseId: 1,
                enrollmentDate: '2024-01-22',
                grade: 'A+',
                status: 'ENROLLED'
            },
            {
                enrollmentId: 6,
                studentId: 3,
                courseId: 2,
                enrollmentDate: '2024-01-22',
                grade: 'N/A',
                status: 'ENROLLED'
            },
            {
                enrollmentId: 7,
                studentId: 4,
                courseId: 3,
                enrollmentDate: '2024-01-23',
                grade: 'B+',
                status: 'ENROLLED'
            },
            {
                enrollmentId: 8,
                studentId: 5,
                courseId: 4,
                enrollmentDate: '2024-01-23',
                grade: 'N/A',
                status: 'ENROLLED'
            }
        ];
        localStorage.setItem('enrollments', JSON.stringify(sampleEnrollments));
        console.log('Sample enrollments created');
    }

    // Set counters
    if (!localStorage.getItem('studentIdCounter')) {
        localStorage.setItem('studentIdCounter', '6');
    }
    if (!localStorage.getItem('courseIdCounter')) {
        localStorage.setItem('courseIdCounter', '6');
    }
    if (!localStorage.getItem('enrollmentIdCounter')) {
        localStorage.setItem('enrollmentIdCounter', '9');
    }
    
    console.log('Database initialized successfully!');
}

// ===== RESET DATABASE (For Testing) =====
function resetDatabase() {
    if (confirm('Are you sure you want to reset all data? This cannot be undone!')) {
        localStorage.clear();
        initializeDatabase();
        location.reload();
    }
}

// ===== INITIALIZE ON PAGE LOAD =====
document.addEventListener('DOMContentLoaded', function() {
    initializeDatabase();
});

// Also run immediately in case DOMContentLoaded already fired
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeDatabase);
} else {
    initializeDatabase();
}