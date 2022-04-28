# **Chorint - Real Parser**

<hr>
<br>

![alt text](images/chorint.png)

The project **Chorint (Challenge Order and Integrations)** is a parser projet which has as main
objective to receive one or more folder path or file path to check and convert that files and folder
to many Jsons. This project was built using **Clean Architecture**.

### **Clean Architecture**

![alt text](images/clean.png)

This kind of design architecture has prioritized agile development and structure simplicity.  
This application was split in modules.

> 1. `Domains` - Module responsible to hold all objects entities and dtos (Data Transfer Objects).
> 2. `Gateways` - Module responsible to communicate with all external services, like ***APIs, Databases*** and others.
> 3. `Services` - Module which conteins entity's manipulation witch will be used by ***usecases***.
> 4. `Use Cases` - Module which is where all the project's business rules located.

## Technologies

- Java 17
- JUnit5 (unit tests)
- Mockito (mock provider)
- PiTest (muttation tests)
- Jacoco (tests coverage)

## Running Application

To run the ***Chorint Application*** is needed to be in project folder and run the following
commands;
> mvn clean package
>
> java -jar .\target\chorintApp-jar-with-dependencies.jar `[FOLDERS OR PATHS TO PROCESS]`

After running with success the ***Chorint Application***, the processed files will be saved
in `target/files` folder.

## Running Test

### Unit Tests

To run the unit tests is needed to run the following command;
> mvn clean test

### Mutation Tests

To run the mutation tests is needed to run the following command;
> mvn test-compile org.pitest:pitest-maven:mutationCoverage
