module com.example.geometrywarsfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.geometrywarsfx to javafx.fxml;
    exports com.example.geometrywarsfx;
}