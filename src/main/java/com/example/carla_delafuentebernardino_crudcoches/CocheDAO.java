package com.example.carla_delafuentebernardino_crudcoches;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CocheDAO {
    MongoClient con;
    MongoCollection<Document> collection = null;
    String json;
    Document doc;

    public void conectar(){
        try {
            con = Conectar.conectar();

            MongoDatabase database= con.getDatabase("concesionario");

            //creando una coleccion
            database.createCollection("coches");

            //Inserto un documento en la coleccion coches
            collection = database.getCollection("coches");

            //Alerta.mensajeInfo("ÉXITO", "Colección creada correctamente.");
        } catch (Exception exception) {
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }
}
