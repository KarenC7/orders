***Pre-Requirements***

Java Version: 17
Important: Ensure to setup properly the Java version in your environment or IDE.


**Lombok Plugin:**

1. Ensure the Lombok plugin is installed and after that:
- IntelliJ IDEA: Go to File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors and check "Enable annotation processing".
- Eclipse: Ensure the annotation processing is enabled in the project settings.


**MySQL create database from command line:**
1. mysql -u root -p
2. enter password. (Ex.: root)
3. CREATE DATABASE orders;
4. SHOW DATABASES;
5. EXIT;

**Postman:**

Import in postman this collection:

[src\main\resources\Complete API Collection (Authentication, Products, Orders).postman_collection.json](https://github.com/KarenC7/orders/blob/master/src/main/resources/Complete%20API%20Collection%20(Authentication%2C%20Products%2C%20Orders).postman_collection.json)



**Run Application:**

mvn spring-boot:run


**Swagger documentation for APIs:**

http://localhost:8080/swagger-ui/index.html
