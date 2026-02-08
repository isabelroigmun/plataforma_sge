package com.example.plataforma_sge;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

//Clase que simplemente obtiene la base de datos de Mongo deseada,
// para poder usarla en otras clases

public class MongoDBConnection {

    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
        }
        return mongoClient.getDatabase("sge");
    }

}
