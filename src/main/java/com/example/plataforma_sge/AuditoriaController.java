package com.example.plataforma_sge;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

//Clase controladora de la interfaz gráfica de Auditoria

public class AuditoriaController implements Initializable {

    // Se inicializan todos los elementos FXML de la interfaz
     @FXML
     private TableView<AuditoriaOB> tabla_auditoria;
     @FXML
     private TableColumn<AuditoriaOB,String> id_mongo;
     @FXML
     private TableColumn<AuditoriaOB,String> accion;
     @FXML
     private TableColumn<AuditoriaOB,Integer> id_usuario;
     @FXML
     private TableColumn<AuditoriaOB, LocalDateTime> fecha;


     //Configuración de las columnas de la tabla, llamando a la carga de los datos.

     public void tabla(){

         id_mongo.setCellValueFactory(new PropertyValueFactory<AuditoriaOB, String>("mongoid"));
         accion.setCellValueFactory(new PropertyValueFactory<AuditoriaOB, String>("accion"));
         id_usuario.setCellValueFactory(new PropertyValueFactory<AuditoriaOB, Integer>("id_usuario"));
         fecha.setCellValueFactory(new PropertyValueFactory<AuditoriaOB, LocalDateTime>("fecha"));

         cargarDatos();
     }

     //Método que obtiene la colección creada en mongo para poder usarla en java,
    //que recorre toda la colección 'auditoria' y la añade a la lista, para finalmente,
    //configurar la tabla
    public void cargarDatos(){
        MongoCollection<Document> collection=
                MongoDBConnection.getDatabase().getCollection("auditoria");

        ObservableList<AuditoriaOB> lista= FXCollections.observableArrayList();

        try(MongoCursor<Document> cursor = collection.find().iterator()){

            while (cursor.hasNext()){

                Document d= cursor.next();

                String id= d.getObjectId("_id").toHexString();
                String accion= d.getString("Acción");
                Integer usuario_id= d.getInteger("id_usuario");
                Instant instant = d.getDate("Fecha de acción").toInstant();
                LocalDateTime fechaa = instant
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();


                lista.add(new AuditoriaOB(id,accion,usuario_id,fechaa));

            }

        }

        tabla_auditoria.setItems(lista);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         //nada más se abra la interfaz gráfica, la tabla ocupará el espacio completo y se
        //ejecutará el método tabla.
        tabla_auditoria.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla();
    }
}
