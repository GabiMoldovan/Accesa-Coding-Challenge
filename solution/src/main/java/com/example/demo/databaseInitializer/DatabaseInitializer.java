package com.example.demo.databaseInitializer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private InsertDummyDataInDB insertDummyDataInDB;

    @PostConstruct
    public void init() {
        /* HOW TO INITIALIZE THE DATA BASE

        1. Make sure that your user is created. It must have their id = 1 because the data set is made for
        the user with id = 1
        You can create the user through Postman by using the signup endpoint

        2. Uncomment the "insertDummyDataInDB.populateDatabase();" line

        3. Run the application, let it finish the initialization, then close the application
        and comment back the line "insertDummyDataInDB.populateDatabase();"


        If you want to reinitialize the data base with the dummy data provided:
        drop all tables and then start from step 1

         */
        //insertDummyDataInDB.populateDatabase();
    }
}
