package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.uniquindio.edu.co.poo.bancouqjfx.App;

import java.io.IOException;

public class ClienteMainController {

    private App app;

    @FXML
    private AnchorPane panelCrud;

    public void setApp(App app) {
        this.app = app;
        cargarCrudCliente();
    }
    //    @FXML
//    public void volverAPrincipal() {
//        if (app != null) {
//            app.openViewPrincipal();
//        }
// }
    @FXML
    private void volverAlMenuCliente() {
        // Aquí llamamos a la vista principal del cliente
        app.openClienteMainView();
    }




    // Carga el CRUD cliente dentro del panel
    private void cargarCrudCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uniquindio/edu/co/poo/bancouqjfx/crudCliente.fxml"));
            AnchorPane crudPane = loader.load();

            ClienteViewController controller = loader.getController();
            controller.setApp(app);

            panelCrud.getChildren().setAll(crudPane);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar gestión de clientes.");
        }
    }

    @FXML
    public void abrirCrudCliente() {
        cargarCrudCliente();
    }

    @FXML
    public void abrirTransacciones() {
        app.openTransaccionView();
    }

    @FXML
    public void abrirConsultaSaldo() {
        app.openConsultaSaldoView();
    }

    @FXML
    public void abrirCrearCuenta() {
        app.openCuentaView();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}