# 🎓 Student Management System - Web Prototype

A modern, responsive web-based Student Management System with a professional dark theme interface.

![Version](https://img.shields.io/badge/version-2.0-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?logo=bootstrap&logoColor=white)

## 🌟 Features

### Student Management
- ✅ Add/Edit/Delete Students
- 🔍 Advanced Search & Filtering
- 📊 Student Statistics
- 📈 GPA Calculation
- 📥 CSV Export

### Course Management
- ✅ Add/Edit/Delete Courses
- 📚 Enrollment Tracking
- 👨‍🏫 Instructor Management
- 📊 Capacity Monitoring
- 📥 CSV Export

### Enrollment Management
- ✅ Enroll/Drop Students
- 🎓 Grade Assignment
- 📊 Enrollment Statistics
- 📈 Progress Tracking
- 📥 CSV Export

### Dashboard
- 📊 Real-time Statistics
- 📈 Visual Analytics
- 🚀 Quick Actions
- 📋 Recent Activities

## 🎨 Design Features

- 🌙 Professional Dark Theme
- 🎭 Smooth Animations
- 📱 Fully Responsive
- ✨ Glassmorphism UI
- 🎯 Intuitive Navigation
- 💫 Loading Animations

## 🛠️ Technologies

- **Frontend:** HTML5, CSS3, JavaScript (ES6+)
- **Framework:** Bootstrap 5.3
- **Icons:** Font Awesome 6.4
- **Fonts:** Poppins, Orbitron (Google Fonts)
- **Storage:** Browser LocalStorage

## 📁 Project Structure
StudentManagementWeb/
├── index.html # Dashboard
├── students.html # Student Management
├── courses.html # Course Management
├── enrollments.html # Enrollment Management
├── css/
│ └── style.css # All Styles
└── js/
├── app.js # Core Logic
├── students.js # Student Operations
├── courses.js # Course Operations
└── enrollments.js # Enrollment Operations


## 🚀 Quick Start

### Local Setup
1. Download/Clone the repository
2. Open `index.html` in your browser
3. System auto-initializes with sample data
4. No server required!

### GitHub Pages Deployment
1. Fork this repository
2. Go to Settings → Pages
3. Select `main` branch
4. Your site will be live at: `https://yourusername.github.io/StudentManagementSystem/`

## 💻 Browser Support

- ✅ Chrome (91+)
- ✅ Firefox (88+)
- ✅ Edge (90+)
- ✅ Safari (14+)

## 📊 Sample Data

The system includes sample data:
- 5 Students
- 5 Courses
- 8 Enrollments

## 🔧 Reset Database

Open browser console (F12) and run:
```javascript
localStorage.clear();
location.reload();
