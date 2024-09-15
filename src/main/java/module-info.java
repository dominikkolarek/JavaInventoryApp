module com.example.inventoryapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires org.json;

    opens com.example.inventoryapp to javafx.fxml;
    exports com.example.inventoryapp;
}