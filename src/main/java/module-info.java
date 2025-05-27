module org.uniquindio.edu.co.poo.bancouqjfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.uniquindio.edu.co.poo.bancouqjfx to javafx.fxml;
    exports org.uniquindio.edu.co.poo.bancouqjfx.model;
    exports org.uniquindio.edu.co.poo.bancouqjfx.viewController;
    opens org.uniquindio.edu.co.poo.bancouqjfx.viewController to javafx.fxml;
    exports org.uniquindio.edu.co.poo.bancouqjfx;

}