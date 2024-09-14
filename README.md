# RestAssured_practice

## Project Overview

The **RestAssured_practice_Automation_Framework** is designed to automate API testing using Rest Assured and Behavior-Driven Development (BDD) methodology. The framework supports the creation and execution of various CRUD queries and test flows across multiple test cases. It provides a very good basis for API testing.

## Project Requirements

To ensure the framework functions correctly, please follow these requirements:

1. **Java SDK**:
   - The project is built with Java 21 SDK. Ensure you have Java 21 installed on your system. If using IntelliJ IDEA, make sure it supports Java 21. You can download it from JetBrains IntelliJ IDEA, ensuring compatibility with Java 21.

2. **Clone the Repository**:

   ```bash
   git clone <repo-url>
   cd RestAssured_practice
   cd ApiPractice  # Ensure this is the folder you open in the IDE

3. **Configure the IDE with Java 21:**:
   - 3.1. Right-click on the PomPractice folder > Open Module Settings > In the Modules section, verify that the language level is set to "21 - Unnamed variables and patterns".

   - 3.2. Click on the Project tab, verify that the SDK is set to "21 - Oracle OpenJDK 21" and that the Language Level is set to "21 - Unnamed variables and patterns".

   - 3.3. Click Ok.

4. **Maven**:

   - The project uses Maven for dependency management. Ensure Maven is installed and properly configured on your system.

   - To build the project and skip tests, use the following command:

   ```bash
      mvn clean install -DskipTests

5. **Branches**:
   -The project uses multiple branches for different purposes. The `main` branch is the primary branch where all the core code and the latest stable features are maintained



