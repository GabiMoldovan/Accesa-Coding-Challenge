# Accesa-Coding-Challenge


# CONTEXT (PLEASE READ ALL)


Build the backend for a "Price Comparator - Market" -  a service that helps users compare prices of everyday grocery items 
across different supermarket chains (e.g., Lidl, Kaufland, Profi)



The system should allow users to track price changes, find the best deals, and manage their shopping lists effectively. 



# The problem that I solved:

I altered the problem a bit:
Let's imagine that we are building the backend for an online mall. There is a pool of items that can be found in one 
or multiple stores of that mall. 
The mall has multiple stores, and each store with its own pool of items. Each store has its own configuration for each item.
We have multiple users using our online mall application.
Each user has their own baskets, a basket for each store


Store items can have discounts applied to them.
Store item prices are updated when discounts are created.
If a discount expires, the price is set back to their original price.
This feature is functional as well

When an item is registered as a store item, we also register the first price history for that item
And any time the price of a store item is updated, we register the entry in the price history (updated by user or discount)


# So we have to solve the following problems:


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
(the items with the lowest prices after the discounts)
- SOLVED -
GET http://localhost:8080/discounts/all-items-discounts-with-max-discount-per-item




New Discounts: - List discounts that have been newly added (e.g., within the last 24 hours, 48 and 72)
The backend checks for the discounts that have became active within a specific period of time and returns them (startDate <= now <= startDate+numberOfHours)
- SOLVED -
GET http://localhost:8080/discounts/recent?hours={nr_of_hours}




Dynamic Price History Graphs: - Provide data points that would allow a frontend to calculate
				and display price trends over time for individual products.  
 			      - This data should be filterable by store, product category, or brand.
- TODO




Product Substitutes & Recommendations: - Highlight "value per unit" (e.g., price per kg, price per liter) to help identify the 
					 best buys, even if the pack size differs. 
- TODO




Custom Price Alert: - Allow users to set a target price for a product. The system should be able to identify when a 
		      product's price drops to or below that target. 
- TODO


# To run the project:


0. Make sure that you have Java installed correctly.
THE PROJECT RUNS ON JAVA 21


1. Clone it


2. Create the PostGreSQL database. Name it AccesaInternship


3. Open the project and add the environment variable which is the jdbc connection string.
Mine looks like this: DATASOURCE_URL=jdbc:postgresql://localhost:5432/AccesaInternship;DATASOURCE_USER=postgres;DATASOURCE_PASS=yourpasswordhere;


If you have the default pgAdmin settings, use the same username, but modify the DATASOURCE_PASS with your own password.
Also change the name of the database if you didn't name it AccesaInternship


5. If you want to view the data in the tables, add the database (the sidebar on the right, there is the database icon right above the maven one).
Click on New, Data Source, then select PostGreSQL. Put your user, password, and the name of the databse.
Or you can view it in pgAdmin


6. Use Postman to create a user.

POST http://localhost:8080/signup
Content-Type: application/json

{
  "firstName": "",
  "lastName": "",
  "email": "",
  "password": ""
}


7. If you want to automatically populate the database with the dummy data that I provided in the data for the data base folder, please check the databaseInitializer and go to InsertDummyDataInDB


In this file, you will find the documentation for implementing the project.
It has all the progress throughout each day.


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