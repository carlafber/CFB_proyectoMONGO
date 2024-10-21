package com.example.carla_delafuentebernardino_crudcoches.CRUD;

import com.example.carla_delafuentebernardino_crudcoches.classes.Coche;
import com.example.carla_delafuentebernardino_crudcoches.util.Alerta;
import com.example.carla_delafuentebernardino_crudcoches.util.Conectar;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CocheCRUD {
    MongoClient con;
    MongoCollection<Document> collection = null;
    String json;
    Document doc;

    public void crearBD(){
        try {
            con = Conectar.conectar();

            MongoDatabase database= con.getDatabase("concesionario");

            //creando una coleccion
            database.createCollection("coches");

            //Inserto un documento en la coleccion coches
            collection = database.getCollection("coches");

            if (collection.countDocuments() == 0) {
                insertarCochesDePrueba();
            }

            //Alerta.mensajeInfo("ÉXITO", "Colección creada correctamente.");
        } catch (Exception exception) {
            Alerta.mensajeError(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }

    public boolean existeCoche(String matricula) {
        // Verifica si existe un coche con la matrícula proporcionada
        return collection.countDocuments(Filters.eq("matricula", matricula)) > 0;
    }

    public void insertarCochesDePrueba() {
        Coche coche1 = new Coche("6666HHH", "Renault", "Clio", "Deportivo");
        Coche coche2 = new Coche("5555BCD", "Ford", "SMax", "Familiar");

        insertarCoche(coche1);
        insertarCoche(coche2);
    }

    public boolean insertarCoche(Coche coche) {
        if (existeCoche(coche.getMatricula())) {
            Alerta.mensajeError("Ya existe un coche con la matrícula: " + coche.getMatricula());
            return false;
        } else {
            Gson gson = new Gson();
            json = gson.toJson(coche);
            doc = Document.parse(json);
            collection.insertOne(doc);

            // Verificar si el coche fue insertado correctamente
            if (existeCoche(coche.getMatricula())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public List<Coche> obtenerCoches(){
        List<Coche> coches = new ArrayList<>();
        Gson gson = new Gson();

        for (Document doc : collection.find()) {
            Coche coche = gson.fromJson(doc.toJson(), Coche.class);
            coches.add(coche);
        }

        return coches;
    }

    public void eliminarCoche(String matricula) {
        collection.deleteOne(new Document("matricula", matricula));
    }

    public void modificarCoche(Coche coche) {
        Document doc = new Document("marca", coche.getMarca())
                .append("modelo", coche.getModelo())
                .append("tipo", coche.getTipo());

        //Actualiza el documento en la colección utilizando la matrícula como filtro
        collection.updateOne(Filters.eq("matricula", coche.getMatricula()), new Document("$set", doc));
    }
}