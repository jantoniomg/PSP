module org.example.cronometro {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.cronometro to javafx.fxml;
    exports org.example.cronometro;
}