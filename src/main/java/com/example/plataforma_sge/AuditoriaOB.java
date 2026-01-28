package com.example.plataforma_sge;

import com.mongodb.client.MongoCollection;
import javafx.beans.property.*;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class AuditoriaOB {

    private final String mongoid;
    private final String accion;
    private final Integer id_usuario;
    private final LocalDateTime fecha;

    public AuditoriaOB(String mongoid,String accion, Integer id_usuario,LocalDateTime fecha) {


        this.mongoid=mongoid;
        this.accion=accion;
        this.id_usuario=id_usuario;
        this.fecha=fecha;


    }

    public String getMongoid() {
        return mongoid;
    }

    public String getAccion() {
        return accion;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public static void pasarAuditoriaAMongo(String accion) {

        MongoCollection<Document> collection =
                MongoDBConnection.getDatabase().getCollection("auditoria");


        Document doc = new Document()
                .append("id_usuario", SQL.id_usuario)
                .append("Acción", accion)
                .append("Fecha de acción", Date.from(
                        LocalDateTime.now()
                                .atZone(ZoneId.systemDefault())
                                .toInstant()
                ));


        collection.insertOne(doc);
    }
}
