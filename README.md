# 🎓 Maz Academy: E-Learning Platform API

> **🚧 ARCHITECTURE UPGRADE IN PROGRESS 🚧**
> *This project is currently undergoing a major migration from a core Java OOP application to a production-grade Spring Boot REST API backed by PostgreSQL.*

An advanced, scalable E-Learning backend system designed to manage users, instructors, courses, and enrollments. 

## 🔄 Project Evolution

### Phase 1: Core Domain & OOP Logic (Completed)
The foundation of this project was built using advanced Object-Oriented Programming principles in pure Java. 
* Designed a robust domain model (`Admin`, `Course`, `Instructor`, `Lesson`, `Module`, `Student`).
* Implemented clean interfaces (`Enrollable`, `Rateable`) to enforce contracts.
* Engineered a custom exception-handling system (`CourseFull`, `UserNotFound`, `AlreadyEnrolled`) to gracefully manage business logic errors.

### Phase 2: REST API & Persistence (Current Focus)
Currently migrating the core logic into a modern web framework to serve data to web and mobile clients.
* **Framework:** Refactoring into a **Spring Boot** application.
* **Database:** Replacing in-memory data structures with a **PostgreSQL** relational database using **Spring Data JPA**.
* **Architecture:** Implementing a layered architecture (Controllers, Services, Repositories, DTOs).

## 💻 Tech Stack

**Current & Target Technologies:**
* **Language:** Java
* **Framework:** Spring Boot, Spring Web
* **Database:** PostgreSQL, Hibernate (JPA)
* **Tools:** Maven, Lombok, Postman (for API testing)

## ⚙️ Core Features (API Endpoints Coming Soon)
* **User Management:** Role-based access for Students, Instructors, and Admins.
* **Course Catalog:** Create, update, and search for courses categorized by `CourseLevel`.
* **Enrollment Engine:** Safe, transactional enrollment logic utilizing custom exceptions to prevent overbooking.

## 🚀 How to Run (Local Development)
*Instructions will be updated upon the completion of the Phase 2 Spring Boot migration.*
