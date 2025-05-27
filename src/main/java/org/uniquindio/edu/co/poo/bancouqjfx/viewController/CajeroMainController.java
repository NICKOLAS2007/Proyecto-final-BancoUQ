package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.uniquindio.edu.co.poo.bancouqjfx.App;

import java.io.IOException;

public class CajeroMainController {

    private App app;

    @FXML
    private AnchorPane panelContenido;

    public void setApp(App app) {
        this.app = app;
//        cargarRegistrarCliente();
    }

    private void cargarRegistrarCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uniquindio/edu/co/poo/bancouqjfx/crudCliente.fxml"));
            AnchorPane clientePane = loader.load();

            ClienteViewController controller = loader.getController();
            controller.setApp(app);

            panelContenido.getChildren().setAll(clientePane);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar registro de clientes.");
        }
    }

    @FXML
    public void abrirCrudCliente() {cargarRegistrarCliente();}


    @FXML
    public void abrirTransacciones() {
        app.openTransaccionView();
    }

    @FXML
    public void abrirConsultaSaldo() {
        app.openConsultaSaldoView();
    }

    @FXML
    public void abrirRetirosDepositos() {
        // Si tienes una vista específica para retiros y depósitos, ábrela aquí
        app.openTransaccionView(); // Por ahora usa la vista de transacciones
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}