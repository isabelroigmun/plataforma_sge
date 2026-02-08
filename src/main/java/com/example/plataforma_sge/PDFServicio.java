package com.example.plataforma_sge;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PDFServicio {
    MongoDatabase database = MongoDBConnection.getDatabase();
    MongoCollection<Document> docs= database.getCollection("fs.files");


    public List<String> doc_proyecto(int id_proyecto){

        GridFSBucket gridFSBucket = GridFSBuckets.create(database);

        List<String> pdfs= new ArrayList<>();

        GridFSFindIterable files = gridFSBucket.find(new Document("metadata.id_proyecto", id_proyecto));

        for (GridFSFile file : files) {
            pdfs.add(file.getFilename());
        }
        return pdfs;
    }

    public void addpdf(int id_proyecto, File pdfFile){

        try {
            GridFSBucket gridFSBucket = GridFSBuckets.create(database);

            try (FileInputStream fis = new FileInputStream(pdfFile)) {
                Document doc = new Document()
                        .append("id_proyecto", id_proyecto)
                        .append("Nombre", pdfFile.getName())
                        .append("Fecha de acción", Date.from(
                                LocalDateTime.now()
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()
                        ));

                GridFSUploadOptions options = new GridFSUploadOptions()
                        .chunkSizeBytes(1024 * 1024) // chunks de 1MB
                        .metadata(doc);

                gridFSBucket.uploadFromStream(pdfFile.getName(), fis, options);
                System.out.println("Archivo subido exitosamente a GridFS!");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void abrirVentanaDesdeMongo(String sel) {

        if (sel !=null){

            try {

                GridFSBucket gridFSBucket= GridFSBuckets.create(MongoDBConnection.getDatabase());

                File tf= File.createTempFile("temp_app",".pdf");
                tf.deleteOnExit();

                try (FileOutputStream fos= new FileOutputStream(tf)){
                    gridFSBucket.downloadToStream(sel,fos);
                }

                if (java.awt.Desktop.isDesktopSupported()){
                    java.awt.Desktop.getDesktop().open(tf);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }





    }

