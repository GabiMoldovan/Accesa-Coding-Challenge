# Accesa-Coding-Challenge


# CONTEXT (PLEASE READ THE ENTIRE FILE)


Build the backend for a "Price Comparator - Market" -  a service that helps users compare prices of everyday grocery items 
across different supermarket chains (e.g., Lidl, Kaufland, Profi)



The system should allow users to track price changes, find the best deals, and manage their shopping lists effectively. 



# The main problem:

I modified the initial problem a bit, to make it more general:


Let's imagine that we are building the backend for an online mall.
The mall has multiple stores, and each store with its own pool of items. 
Each store has its own configuration for each item.
We have multiple users using our online mall application.
Each user has their own baskets, a basket for each store


Store items can have discounts applied to them.
Store item prices must be updated when discounts are created.
If a discount expires, the price of that store item must be set back to the original price.
(This feature is functional as well)

When an item is registered as a store item, we also make an entry for the price history for that item
And any time the price of a store item is updated, we register the entry in the price history (updated by user or discount)


# The requirements:


Daily Shopping Basket Monitoring: - Help users split their basket into shopping lists that optimise for cost savings

I solved it by letting users pick what items they want, and added a "optimization" endpoint that works like this:
Let's say that the user has 3 baskets, each basket for one store, with their specific items in it.
By accessing the endpoint, the backend gets the pool of items that's in all the baskets, and for each item, we search
the stores to find the cheapest possible instance of that item. We do that for all the items
So in the end, the user will have their baskets shuffled, but the baskets will contain the cheapest items in the mall.
- SOLVED -
POST http://localhost:8080/baskets/users/{{userId}}/optimize

The endpoint will return the optimized baskets


Best Discounts: - List products with the highest current percentage discounts across all tracked stores.

The backend gets all the store items that have a discount, and sort them descending, and return the list of discounts with unique items
(if multiple items have a discount, we only get the item that has the biggest discount)
- SOLVED -
GET http://localhost:8080/discounts/all-items-discounts-with-max-discount-per-item




New Discounts: - List discounts that have been newly added (e.g., within the last 24 hours, 48 and 72)

The backend checks for the discounts that have became active within a specific period of time and returns them (startDate <= now <= startDate+numberOfHours)
- SOLVED -
GET http://localhost:8080/discounts/recent?hours={nr_of_hours}




Dynamic Price History Graphs: - Provide data points that would allow a frontend to calculate
				and display price trends over time for individual products.  
 			      - This data should be filterable by store, product category, or brand.

The backend has a custom filter function in the PriceHistoryRepo, and applies the filters that the endpoint contains
- SOLVED -
GET http://localhost:8080/price-history/dynamic-price-history?

    storeId=store_id&

    category=category_name&

    brand=brand_name




Product Substitutes & Recommendations: - Highlight "value per unit" (e.g., price per kg, price per liter) to help identify the 
					 best buys, even if the pack size differs. 
- SOLVED -
GET http://localhost:8080/store-item/{{storeItemId}}/best-value




Custom Price Alert: - Allow users to set a target price for a product. The system should be able to identify when a 
		      product's price drops to or below that target. 
- SOLVED -
GET http://localhost:8080/price-alerts/user/{{userId}}/get-triggered-alerts


# To run the project:


0. Make sure that you have Java installed correctly.
THE PROJECT RUNS ON JAVA 21.


1. Clone it.


2. Create the PostGreSQL database. Name it AccesaInternship.


3. Open the project and add the environment variable which is the jdbc connection string.
Mine looks like this: DATASOURCE_URL=jdbc:postgresql://localhost:5432/AccesaInternship;DATASOURCE_USER=postgres;DATASOURCE_PASS=yourpasswordhere;


If you have the default pgAdmin settings, use the same username, but modify the DATASOURCE_PASS with your own password.
Also change the name of the database if you didn't name it AccesaInternship.


5. If you want to view the data in the tables, add the database (the sidebar on the right, there is the database icon right above the maven one).
Click on New, Data Source, then select PostGreSQL. Put your user, password, and the name of the databse.
Or you can view it in pgAdmin.


6. Use Postman to create a user.

POST http://localhost:8080/signup
Content-Type: application/json

{

  "firstName": "",

  "lastName": "",

  "email": "",

  "password": ""

}

After creating the user, add the credentials to the Authorisation section in Postman so you are able to test the endpoints

<img width="688" alt="Screenshot_1" src="https://github.com/user-attachments/assets/af4fe774-cb86-441c-87c6-108014be80e5" />


# SWAGGER

If you want to acces the Swagger API Documentation:

1. Start the application

2. Go to http://localhost:8080/swagger-ui/index.html


You will get the documentation for each endpoint for every controller that exist in the app


<img width="720" alt="Screenshot_1" src="https://github.com/user-attachments/assets/291cbcb0-b787-48be-a8eb-a3a3d2a3dc85" />

<img width="722" alt="Screenshot_3" src="https://github.com/user-attachments/assets/f1722f61-7881-456b-ba62-7e6933766755" />

<img width="734" alt="Screenshot_4" src="https://github.com/user-attachments/assets/1898c64a-1111-44f5-b35e-cae66a360c1b" />

<img width="725" alt="Screenshot_5" src="https://github.com/user-attachments/assets/0531422b-6f39-482f-91c2-029ab557c605" />


# IMPORTANT

7. If you want to automatically populate the database with the dummy data that I provided in the data for the "data for the data base" folder, please check the databaseInitializer and go to DatabaseInitializer


In this file, you will find the documentation for implementing the project.
It has all the progress throughout each day.


# Implementation explanation (based on the evaluation criteria)


- Functionality

Correct and robust implementation of the specified business requirements (core features, analytical aspects, notifications) - All core features have been implemented with careful attention to detail, including data accuracy, processing, and logic validation. The backend exposes all required endpoints to support frontend functionality, such as:

Authentication Controller: for user registration and login

User Controller: create, delete, and retrieve users by ID

Item Controller: create, retrieve (by ID), update, delete items, and list all items

Store Controller: create, retrieve (by ID), update, and delete stores

All controllers are structured to reflect the full set of application features. You can refer to the individual controller classes for a complete list of exposed endpoints.


Accuracy of data processing, calculations, and comparisons - Discounts are applied automatically to store items when they become active, ensuring up-to-date pricing.

Every price change — whether due to a manual update or an active/inactive discount — is accurately logged in the price_history table.

User passwords are securely stored using encryption to ensure data protection and privacy.


Effective handling of sample data and edge cases presented in your data - I created my own dataset based on the sample data.
Please check the dummy_data.txt in the data for the data base folder.


- Solution Design

Soundness of your overall backend design - The models serve as the blueprint for the entities in the application, mapping each entity to a corresponding table in the database using JPA annotations. The JPA repositories provide an abstraction layer for data access, enabling CRUD operations without the need for boilerplate SQL code.

To ensure clean separation of concerns, we implement internal business logic to convert data from entity format to DTO (Data Transfer Object) format using mappers, which are useful for structuring API responses.

The service layer handles the core business logic, orchestrating operations such as validation, transformations, and interactions with multiple repositories or external services when needed. This keeps the logic centralized and reusable.

The controllers act as the entry point to the application, handling HTTP requests and responses. They delegate processing to the service layer, ensuring that the application remains modular, maintainable, and easy to test. This layered architecture promotes a clear separation of responsibilities and enhances scalability and readability across the backend codebase.


Appropriate choices for structuring your application (e.g., services, controllers, data access objects) - My implementation makes use of the MVC (Model-View-Controller) design pattern, ensuring a clear separation of concerns and a maintainable codebase.

In my application, I structured the backend using several distinct layers:

Controllers handle HTTP requests and responses, acting as the entry point of the application.

DTOs (Data Transfer Objects) are used to define the shape of the data exchanged between the client and the server, helping to decouple the internal data structure from the API.

Models represent the core entities of the application and are mapped to the database using JPA annotations.

Repositories (JPA Repositories) provide the data access layer, abstracting database operations and offering built-in CRUD functionality.

Services contain the business logic of the application, processing data and coordinating between the repository and other components.

Mappers convert between entities and DTOs, ensuring clean data transformation and separation between persistence and API layers.

Exception handling is centralized to provide meaningful error messages and consistent responses, improving robustness and user experience.

This layered architecture enhances modularity, testability, and scalability, making the application easier to extend and maintain over time.


How data is managed, processed, and accessed - Data is persistently stored in the database and accessed via JPA repositories. It is processed in the service layer, where the core business logic is applied. The controllers expose API endpoints that allow clients to interact with the data, delegating processing to the services and returning appropriate responses.

Thoughtfulness regarding potential scalability or extensibility (even if not fully implemented) - I want to point out that we can add ROLES for users. So we'll have: USER, STORE_OWNER, ADMIN. By implementing this feature, we can limit the access of some features so they can be used only by the users with a specific role. For example, we only want users to be able to make purchases, delete items from their baskets, etc. We only want store owners to manage the store(s) that they are assigned to. So they'll be able to add store items, modify them, etc. And we want the admins to have full access to every feature of the app. This would be a nice feature.



- Code Quality & Testing

Clarity, readability, and organization of your Java code - The code is organized in a clean and modular structure, following best practices for separation of concerns. Each layer of the application — controllers, services, repositories, models, DTOs, and mappers — is clearly defined and placed in its own dedicated package.

Methods and classes are named descriptively to reflect their purpose, and responsibilities are properly divided, avoiding duplication and tightly coupled logic. The use of annotations, dependency injection, and well-structured layers improves the overall readability and makes the application easy to navigate, maintain, and extend.

Adherence to Java best practices and coding conventions - The code adheres to standard Java best practices and coding conventions. Class and method names follow the camelCase and PascalCase naming conventions appropriately, and access modifiers are used consistently to ensure encapsulation. Code is structured with clear indentation and spacing, enhancing readability.

Annotations such as @Service, @RestController, and @Repository are used correctly to leverage Spring’s dependency injection and component scanning features. Constants, when needed, are declared as static final, and exception handling is implemented thoughtfully to provide meaningful error feedback.

Additionally, responsibilities are well-separated across layers, avoiding code duplication and promoting reusability. The use of DTOs, mappers, and a layered architecture reflects a strong understanding of clean code principles and maintainable design.

Modularity, maintainability, and good separation of concerns (e.g., distinct layers for data access, business logic) - The application is built with a well-defined layered architecture that promotes modularity and maintainability. Each layer has a clear responsibility: the controller layer handles HTTP requests and delegates tasks to the service layer, which contains the core business logic. Data persistence is handled separately by the repository layer, ensuring a clean abstraction over database operations.

Additionally, DTOs and mappers are used to decouple internal data models from the API, further enforcing separation of concerns. This structure allows each component to evolve independently, simplifies testing, and makes the codebase easier to understand and extend. The overall design supports scalability and aligns with standard enterprise application patterns.

Efficient use of data structures and algorithms where applicable - The application makes efficient use of Java's built-in data structures to manage and manipulate data effectively. Stream operations are used where appropriate to improve readability and performance when processing collections, particularly in mapping entities to DTOs.

Algorithms and logic are kept simple and efficient, avoiding unnecessary complexity. Wherever possible, operations are optimized for performance and clarity, ensuring that the application remains responsive and scalable even as data volume increases. The use of efficient patterns like caching or validation at the service level (if implemented) further enhances performance and reliability.


- Documentation & Presentation


Clarity and completeness of the README.md file - I think that this file has everything to explain my thinking process


Effectiveness of the video demonstration in showcasing the project's capabilities and your understanding. 


Well-commented code where necessary to explain complex logic.


# Progress Tracking:


# 09.05.2025:
1. Created the Spring Boot project using Spring Initializr.
I added the following dependencies: DevTools, Spring Web, Spring Data JPA, Spring Security, PostGreSQL Driver.

2. I created a class diagram for the entities in the project

DISCLAIMER: I know that the photo it not clear. If you want to see the diagram for yourself, please take a look inside the diagrams folder. I provided instructions so that you can load it up yourself, it is very easy.

<img width="700" alt="Screenshot_3" src="https://github.com/user-attachments/assets/19fffa12-98a7-40fd-b686-724553c4ef77" />


3. I created the database in pgAdmin. I named it: AccesaInternship

4. I added the database

5. Managed to make all the configurations necessary for implementing the project (also added swagger for api documentation and lombok to avoid the boilerplate code)


# 10.05.2025:

1. Added all the model entities

2. Finished the model entities and the class diagram

3. I created the JpaRepositoryes for the entities

4. I created the exceptions

5. I created the DTOs

6. I created the mappers

7. I created the UserService and AuthService

# 11.05.2025:

1. Added the login/register logic with crypted passwords

2. Created the files for the rest of the Services

3. Finished the services with the CRUD operations

4. I added the user and auth controllers

5. I created a dummy data txt file that I will later use to insert it into the app

6. I used Postman to create the testing user

# 12.05.2025

1. I created the controllers for CRUD operations on all entities and I also tested them using Postman

2. I created a data base initializer

3. I added the solution for the first requirement

# 13.02.2025

1. I added the solution to the second requirement

2. I added the solution to the third requirement

3. I added the solution for the fourth requirement

4. I added the solution for the fifth requirement

5. I added the solution for the sixth requirement

6. Optimized the data base initializer

# 14.02.2025

1. I created an API documentation where you can search how to call almost every endpoint that the application exposes

2. I created the presentation video

3. I added docstrings in the services

# 21.05.2025

1. I added the SWAGGER section here in the README file