# LibraryApp

A Spring Boot-based prototype for managing a library. This project handles books, authors, and publishers with CRUD operations and filtering capabilities.  


## Technologies

- Java 17  
- Spring Boot 
- Spring Data JPA  
- PostgreSQL  
- Lombok  
- Postman  


## Project Structure

com.example.libraryapp
│
├── controller 
├── service 
├── repository 
├── entity 
├── dto 

## Operation

1. **CRUD Operations**  
   - Create, read, update, delete books, authors, and publishers  

2. **Filtering**  
   - Books starting with 'A' (Stream API)  
   - Books published after 2023 (JPA query method)  
   - List books and authors from the top 2 publishers  
