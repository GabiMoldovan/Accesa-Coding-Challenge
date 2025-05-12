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
        insertDummyDataInDB.populateDatabase();
    }
}
