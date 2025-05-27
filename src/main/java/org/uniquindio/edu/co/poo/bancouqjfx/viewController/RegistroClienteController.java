package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.uniquindio.edu.co.poo.bancouqjfx.App;
import org.uniquindio.edu.co.poo.bancouqjfx.model.Cliente;

public class RegistroClienteController {

    private App app;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    public void registrarCliente() {
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Campos requeridos", "Por favor complete todos los campos.");
            return;
        }

        Cliente nuevo = new Cliente("Nuevo", "Usuario", "123" + Math.random(), correo, contrasena);
        boolean registrado = app.banco.agregarCliente(nuevo);

        if (registrado) {
            mostrarAlerta("Éxito", "Cliente registrado correctamente.");
            app.openSeleccionUsuarioView();  // volver al login
        } else {
            mostrarAlerta("Error", "Ya existe un cliente con esos datos.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}