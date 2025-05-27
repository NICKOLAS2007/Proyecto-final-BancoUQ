package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.uniquindio.edu.co.poo.bancouqjfx.App;

public class RegistroAdministradorController {
    public App app;

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    private AdministradorMainController adminMainController = new AdministradorMainController();


    public void setAdministradorMainController(AdministradorMainController controller) {
        this.adminMainController = controller;
    }
    @FXML
    public void volver() {
        app.openSeleccionUsuarioView();
    }


    @FXML
    public void registrarAdmin() {
        String usuario = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return;
        }

        boolean existe = adminMainController.existeAdministrador(usuario);
        if (existe) {
            mostrarAlerta("Error", "El usuario ya existe.", Alert.AlertType.ERROR);
            return;
        }

        adminMainController.agregarAdministrador(usuario, contrasena);
        mostrarAlerta("Éxito", "Administrador registrado correctamente.", Alert.AlertType.INFORMATION);

        app.openSeleccionUsuarioView();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtCorreo.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}