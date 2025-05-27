package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.uniquindio.edu.co.poo.bancouqjfx.App;
import org.uniquindio.edu.co.poo.bancouqjfx.model.Administrador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorMainController {

    private App app;

    @FXML
    private AnchorPane panelContenido;

    private List<AdminSimple> administradores = new ArrayList<>();

    private static class AdminSimple {
        String usuario, contrasena;
        AdminSimple(String u, String c) { usuario = u; contrasena = c; }
    }

    public void setApp(App app) {
        this.app = app;
//        mostrarLoginAdmin();
    }

//    private void mostrarLoginAdmin() {
//        panelContenido.getChildren().clear();
//
//        VBox loginBox = new VBox(10);
//        loginBox.setPrefWidth(400);
//        loginBox.setPrefHeight(300);
//
//        Label lblTitulo = new Label("Login Administrador");
//        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
//
//        TextField txtUsuario = new TextField();
//        txtUsuario.setPromptText("Usuario");
//
//        PasswordField txtContrasena = new PasswordField();
//        txtContrasena.setPromptText("Contraseña");
//
//        Button btnIngresar = new Button("Ingresar");
//        Button btnRegistrar = new Button("Registrar nuevo admin");
//
//        btnIngresar.setOnAction(e -> {
//            String usuario = txtUsuario.getText().trim();
//            String contrasena = txtContrasena.getText();
//
//            if (usuario.isEmpty() || contrasena.isEmpty()) {
//                mostrarError("Complete todos los campos.");
//                return;
//            }
//
//            boolean existe = administradores.stream()
//                    .anyMatch(admin -> admin.usuario.equals(usuario) && admin.contrasena.equals(contrasena));
//
//            if (existe) {
//                mostrarInfo("Login exitoso.");
//                // Aquí iría tu lógica para cargar panel de administración
//            } else {
//                mostrarError("Usuario o contraseña incorrectos.");
//            }
//        });
//
//        btnRegistrar.setOnAction(e -> mostrarRegistroAdmin());
//
//        loginBox.getChildren().addAll(lblTitulo, txtUsuario, txtContrasena, btnIngresar, btnRegistrar);
//        panelContenido.getChildren().add(loginBox);
//    }

    private void mostrarRegistroAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uniquindio/edu/co/poo/bancouqjfx/registrarAdmin.fxml"));
            Parent root = loader.load();

            RegistroAdministradorController controller = loader.getController();
            controller.setAdministradorMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Registrar Administrador");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("No se pudo cargar el registro de administrador.");
        }
    }

    public boolean existeAdministrador(String usuario) {
        return app.banco.verificarAdmin(usuario);
    }

    public void agregarAdministrador(String usuario, String contrasena) {
        app.banco.agregarAdmin(new Administrador("", "", "", null, usuario, contrasena ));
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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