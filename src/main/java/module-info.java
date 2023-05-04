module inventory {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens inventory.model to javafx.base;
    exports inventory.model;
    opens inventory to javafx.fxml,
            org.junit.jupiter.engine,
            org.junit.platform.engine;
    exports inventory;
    opens inventory.controller to javafx.fxml;
    exports inventory.controller;
    exports inventory.model.exception;
    opens inventory.model.exception to javafx.base;

}