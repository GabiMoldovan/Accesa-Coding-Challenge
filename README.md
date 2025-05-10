# Accesa-Coding-Challenge

To run the project:

0. Make sure that you have Java installed correctly.
THE PROJECT RUNS ON JAVA 21

1. Clone it

2. Create the PostGreSQL database. Name it AccesaInternship

3. Open the project and add the environment variable which is the jdbc connection string.
Mine looks like this: DATASOURCE_URL=jdbc:postgresql://localhost:5432/AccesaInternship;DATASOURCE_USER=postgres;DATASOURCE_PASS=youpasswordhere;

If you have the default pgAdmin settings, use the same username, but modify the DATASOURCE_PASS with your own password.
Also change the name of the database if you didn't name it AccesaInternship

5. If you want to view the data in the tables, add the database (the sidebar on the right, there is the database icon right above the maven one).
Click on New, Data Source, then select PostGreSQL. Put your user, password, and the name of the databse.
Or you can view it in pgAdmin





In this file, you will find the documentation for implementing the project.
It has all the progress throughout each day.


Track-Progress:


09.05.2025:
1. Created the Spring Boot project using Spring Initializr.
I added the following dependencies: DevTools, Spring Web, Spring Data JPA, PostGreSQL Driver.

2. I created a class diagram for the entities in the project

DISCLAIMER: I know that the photo it not clear. If you want to see the diagram for yourself, please take a look inside the diagrams folder. I provided instructions so that you can load it up yourself, it is very easy.

<img width="700" alt="Screenshot_3" src="https://github.com/user-attachments/assets/19fffa12-98a7-40fd-b686-724553c4ef77" />


3. I created the database in pgAdmin. I named it: AccesaInternship

4. I added the database

5. Managed to make all the configurations necessary for implementing the project


10.05.2025:

1. Added all the model entities

2. Finished the model entities and the class diagram

3. I created the JpaRepositoryes for the entities

4. I created the exceptions

5. I created the DTOs