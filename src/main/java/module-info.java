module com.example.plataforma_sge {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.plataforma_sge to javafx.fxml;
    exports com.example.plataforma_sge;
}