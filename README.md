# Ship Management System

## About the Project
It is a backend application implemented in Java, designed to manage data structures (related to ships, fleets, or maritime logistics) and interface with a relational database.

## Tech Stack
* **Language:** Java
* **Build Tool:** Maven
* **Database:** SQL

## Project Structure
The repository is organized into the following components:
* `src/` - The main application source code containing business logic, models, and data access layers.
* `init.sql` - An SQL script containing commands to initialize the database tables and populate them with seed data.
* `pom.xml` - The Maven configuration file defining dependencies, plugins, and build properties.

## Getting Started & Deployment

### 1. Database Setup
Before running the application, you need to set up your database environment and execute the initialization script:
```bash
# Import the init.sql file into your local DBMS
# Alternative command via CLI:
mysql -u [username] -p [database_name] < init.sql
```

### 2. Build the Project
Java Development Kit (JDK) and Apache Maven should be installed on your system. Run the following command in the project root to clean and build the application:
```bash
mvn clean package
```

### 3. Run the Application
Once the build is completed, you can run the generated `.jar` file from the target directory:
```bash
java -jar target/ship-1.0-SNAPSHOT.jar
```
