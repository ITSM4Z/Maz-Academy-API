# 🎓 Maz Academy: E-Learning Platform API

> **🚧 ARCHITECTURE UPGRADE IN PROGRESS 🚧**
> *This project is currently undergoing a major migration from a core Java OOP console application to a production-grade Spring Boot REST API backed by PostgreSQL.*

An advanced, scalable E-Learning backend system designed to manage users, instructors, courses, and enrollments. 

## 🔄 Project Evolution

### Phase 1: Core Domain & Advanced OOP (Completed)
The foundation of this project was built using advanced Object-Oriented Programming principles and core Java data structures.
* **Robust Domain Model:** Designed a highly encapsulated architecture (`Admin`, `Course`, `Instructor`, `Lesson`, `Module`, `Student`) utilizing inheritance and polymorphism.
* **Advanced Data Structures:** Implemented `HashMap` for grade tracking, `TreeSet` with the `Comparable` interface for automated sorting (e.g., sorting students by GPA), and thread-safe collections (`Vector`) for user management.
* **Java Generics:** Engineered a custom generic `Catalogue<T>` class for reusable component rendering.
* **Custom UI Engine:** Built a dynamic console-based interactive menu system (`SystemHelper`) featuring complex input validation and text-based search algorithms.
* **Contract Enforcement:** Implemented clean interfaces (`Enrollable`, `Rateable`) and a custom checked exception hierarchy (`CourseFullException`, `UserNotFoundException`) for safe business logic.

### Phase 2: REST API & Persistence (Current Focus)
Currently migrating the core logic into a modern web framework to serve data to web and mobile clients.
* **Framework:** Refactoring into a **Spring Boot** application.
* **Database:** Replacing in-memory data structures with a **PostgreSQL** relational database using **Spring Data JPA** and Hibernate.
* **Architecture:** Implementing a layered REST architecture (Controllers, Services, Repositories, DTOs).

## 💻 Tech Stack

**Current & Target Technologies:**
* **Language:** Java
* **Framework:** Spring Boot, Spring Web
* **Database:** PostgreSQL, Hibernate (JPA)
* **Tools:** Maven, Lombok, Postman (for API testing)

## ⚙️ Core Features (API Endpoints Coming Soon)
* **User Management:** Role-based access using Single-Table Inheritance for Students, Instructors, and Admins.
* **Course Catalog:** Create, update, and search for courses categorized by `CourseLevel`.
* **Enrollment Engine:** Safe, transactional enrollment logic preventing overbooking and duplicate registrations.

## 🚀 How to Run (Local Development)
*Instructions will be updated upon the completion of the Phase 2 Spring Boot migration.*
