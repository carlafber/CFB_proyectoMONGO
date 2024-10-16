package com.example.carla_delafuentebernardino_crudcoches.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Conectar {
    public static MongoClient conectar(){
        try{
            final MongoClient conexion = new MongoClient(new MongoClientURI("mongodb://root:1234@localhost:27017/?authSource=admin"));
            System.out.println("Conexión a la base de datos realizada correctamente.");
            return conexion;
        } catch (Exception e) {
            Alerta.mensajeError("Conexión Fallida\n" + e.getMessage());
            return null;
        }
    }

    public static void desconectar(MongoClient con) {
        con.close();
    }
}
