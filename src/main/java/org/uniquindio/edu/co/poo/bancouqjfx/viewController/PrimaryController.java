package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.fxml.FXML;
import org.uniquindio.edu.co.poo.bancouqjfx.App;

public class PrimaryController {

    public App app;

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    public void onOpenCrudClientes() {
        app.openCrudCliente();
    }

    @FXML
    public void onOpenTransacciones() {
        app.openTransaccionView();
    }

    @FXML
    public void onOpenCuenta() {
        app.openCuentaView();
    }

    @FXML
    public void onOpenEmpleadoView() {
        app.openEmpleadoView();
    }


    @FXML
    public void abrirConsultaSaldo() {
        app.openConsultaSaldoView();
    }
}