# Online Teaching System

A comprehensive web-based online teaching platform built with Vue.js frontend and Spring Boot + MyBatis backend, supporting both student and teacher functionalities.

## 🏗️ Architecture

This project consists of two main components:

### Backend (`onlineTeaching_system/`)
- **Framework**: Spring MVC + MyBatis + MySQL
- **Java Version**: 1.8
- **Build Tool**: Maven
- **Architecture**: SSM (Spring + SpringMVC + MyBatis)

### Frontend (`online_teaching/`)
- **Framework**: Vue.js 2.5.2
- **UI Library**: Element UI 2.13.0
- **Build Tool**: Webpack
- **Package Manager**: npm

## 📋 Features

### For Students
- 👤 **User Management**: Registration, login, profile management
- 📚 **Course Management**: View available courses, course selection
- 📝 **Homework System**: Submit assignments, view homework history
- ❓ **Q&A System**: Ask questions, view answers from teachers
- 📊 **Score Tracking**: View grades and performance metrics
- 📖 **Course Materials**: Access course content and materials

### For Teachers
- 👨‍🏫 **User Management**: Registration, login, profile management
- 📚 **Course Management**: Create and manage courses, course materials
- 📝 **Assignment Management**: Create homework, review submissions
- ❓ **Q&A Management**: Answer student questions
- 📊 **Grade Management**: Grade assignments, manage student scores
- 👥 **Student Management**: View enrolled students, track progress

## 🛠️ Technology Stack

### Backend Technologies
- **Spring Framework 4.3.9**: Core framework
- **Spring MVC**: Web framework
- **MyBatis 3.4.5**: ORM framework
- **MySQL**: Database
- **C3P0**: Connection pooling
- **Maven**: Dependency management
- **Jackson**: JSON processing
- **Log4j**: Logging

### Frontend Technologies
- **Vue.js 2.5.2**: Progressive JavaScript framework
- **Element UI 2.13.0**: UI component library
- **Vue Router 3.0.1**: Client-side routing
- **Axios 0.19.2**: HTTP client
- **Webpack 3.6.0**: Module bundler
- **Babel**: JavaScript compiler
- **SCSS**: CSS preprocessor
- **ESLint**: Code linting

## 📦 Installation & Setup

### Prerequisites
- Java 8+
- Node.js 6.0.0+
- npm 3.0.0+
- MySQL 5.7+
- Maven 3.0+

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Online_Teaching_System-main
   ```

2. **Database Setup**
   ```bash
   # Create database and import schema
   mysql -u root -p
   CREATE DATABASE online_teaching;
   USE online_teaching;
   SOURCE online_teaching.sql;
   ```

3. **Configure Database Connection**
   Edit `onlineTeaching_system/src/main/resources/jdbc.properties`:
   ```properties
   jdbc.driver=com.mysql.jdbc.Driver
   jdbc.url=jdbc:mysql://localhost:3306/online_teaching?useUnicode=true&characterEncoding=utf8
   jdbc.username=your_username
   jdbc.password=your_password
   ```

4. **Build and Run Backend**
   ```bash
   cd onlineTeaching_system
   mvn clean compile
   mvn tomcat:run
   # Or deploy the generated WAR file to Tomcat
   mvn package
   ```

### Frontend Setup

1. **Install Dependencies**
   ```bash
   cd online_teaching
   npm install
   ```

2. **Development Server**
   ```bash
   npm run dev
   # Access at http://localhost:8080
   ```

3. **Production Build**
   ```bash
   npm run build
   ```

## 📁 Project Structure

```
Online_Teaching_System-main/
├── README.md
├── online_teaching.sql          # Database schema
├── onlineTeaching_system/       # Backend (Java)
│   ├── pom.xml                  # Maven configuration
│   ├── src/main/
│   │   ├── java/com/
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dao/             # Data access layer
│   │   │   ├── model/           # Entity models
│   │   │   └── service/         # Business logic
│   │   ├── resources/
│   │   │   ├── mapper/          # MyBatis mappers
│   │   │   └── *.xml            # Configuration files
│   │   └── webapp/
│   └── target/                  # Build output
└── online_teaching/             # Frontend (Vue.js)
    ├── package.json             # npm configuration
    ├── src/
    │   ├── components/          # Vue components
    │   ├── views/               # Page components
    │   ├── router/              # Route configuration
    │   ├── api/                 # API calls
    │   └── utils/               # Utility functions
    ├── build/                   # Build configuration
    └── config/                  # Environment configuration
```

## 🚀 API Endpoints

### Authentication
- `POST /login` - User login (student/teacher)
- `POST /register` - User registration

### Student APIs
- `GET /student/courses` - Get available courses
- `POST /student/select-course` - Select a course
- `GET /student/homework` - Get homework assignments
- `POST /student/submit-homework` - Submit homework
- `GET /student/questions` - Get Q&A
- `POST /student/ask-question` - Ask a question
- `GET /student/scores` - Get grades

### Teacher APIs
- `GET /teacher/courses` - Get managed courses
- `POST /teacher/create-course` - Create new course
- `GET /teacher/students` - Get enrolled students
- `POST /teacher/create-homework` - Create homework
- `GET /teacher/homework-submissions` - Get submissions
- `POST /teacher/grade-homework` - Grade submissions
- `GET /teacher/questions` - Get student questions
- `POST /teacher/answer-question` - Answer questions

## 🧪 Testing

### Backend Testing
```bash
cd onlineTeaching_system
mvn test
```

### Frontend Testing
```bash
cd online_teaching
npm run test        # Run all tests
npm run unit        # Unit tests
npm run e2e         # End-to-end tests
npm run lint        # Code linting
```

## 🔧 Development

### Code Standards
- **Backend**: Follow Java coding standards, use proper documentation
- **Frontend**: ESLint configuration with Standard style guide
- **Database**: Use consistent naming conventions (snake_case)

### Git Workflow
1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📋 Database Schema

### Core Tables
- **student**: Student information
- **teacher**: Teacher information  
- **course**: Course details
- **student_selection**: Course enrollments
- **homework**: Assignment details
- **student_homework**: Homework submissions
- **question**: Q&A system
- **score**: Grade management
- **course_ware**: Course materials
- **curriculum**: Course curriculum
- **department**: Academic departments

## 🐛 Troubleshooting

### Common Issues

**Backend Issues:**
- **Database connection fails**: Check MySQL service and credentials
- **Maven build fails**: Verify Java version and Maven configuration
- **Port conflicts**: Change server port in configuration

**Frontend Issues:**
- **npm install fails**: Clear cache with `npm cache clean --force`
- **Build errors**: Check Node.js version compatibility
- **API connection issues**: Verify backend server is running

### Logs
- **Backend logs**: Check Tomcat logs and Log4j output
- **Frontend logs**: Check browser console and webpack output

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Contributors

- **Author**: zhangtao
- **Version**: 1.0.0

## 📞 Support

For issues and questions:
1. Check the troubleshooting section
2. Search existing issues
3. Create a new issue with detailed description

---

**Note**: This is an educational project designed for learning web development with Spring MVC and Vue.js frameworks.