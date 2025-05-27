package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.uniquindio.edu.co.poo.bancouqjfx.App;
import org.uniquindio.edu.co.poo.bancouqjfx.model.Administrador;
import org.uniquindio.edu.co.poo.bancouqjfx.model.Cliente;

public class SeleccionUsuarioController {

    // Constantes para los tipos de usuario
    private static final String CLIENTE = "Cliente";
    private static final String ADMINISTRADOR = "Administrador";
    private static final String CAJERO = "Cajero";

    private App app;

    @FXML
    private ComboBox<String> comboTipoUsuario;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRegistrar;

    /**
     * Inicializa el ComboBox con los tipos de usuario
     */
    @FXML
    public void initialize() {
        comboTipoUsuario.getItems().addAll(CLIENTE, ADMINISTRADOR, CAJERO);
        comboTipoUsuario.getSelectionModel().selectFirst();
    }

    public void setApp(App app) {
        this.app = app;
    }

    /**
     * Lógica del botón Ingresar
     */
    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private Label lblMensaje;
    @FXML
    public void ingresar() {
        String tipoUsuario = comboTipoUsuario.getValue();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            mostrarAlerta("Error", "Seleccione un tipo de usuario.");
            return;
        }

        if (CAJERO.equals(tipoUsuario)) {
            System.out.println("➡ Entrando como cajero...");
            mostrarInfo("Acceso como Cajero", "No necesitas login. Ingresando al panel...");
            app.openCajeroMainView();
            return;
        }

        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Correo y contraseña son obligatorios.");
            return;
        }

        switch (tipoUsuario) {
            case CLIENTE:
                manejarLoginCliente(correo, contrasena);
                break;
            case ADMINISTRADOR:
                manejarLoginAdministrador(correo, contrasena);
                break;
            default:
                mostrarAlerta("Error", "Seleccione un tipo de usuario válido.");
        }
    }

    /**
     * Lógica del botón Registrarse
     */
    @FXML
    public void registrar() {
        String tipoUsuario = comboTipoUsuario.getValue();

        if (CLIENTE.equals(tipoUsuario)) {
            app.openRegistroClienteView();
        } else if (ADMINISTRADOR.equals(tipoUsuario)) {
            app.openRegistroAdministradorView(); // Abre vista de registro de empleado (que puede ser admin)
        } else {
            mostrarAlerta("Información", "Registro no disponible para " + tipoUsuario);
        }
    }

    // Métodos para manejar el ingreso por tipo

    private void manejarLoginCliente(String correo, String contrasena) {
        Cliente cliente = app.banco.buscarClientePorCorreo(correo);
        if (cliente != null && cliente.getContraseña().equals(contrasena)) {
            app.openClienteMainView();
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas para cliente.");
        }
    }

    private void manejarLoginAdministrador(String correo, String contrasena) {
        if (validarAdmin(correo, contrasena)) {
            app.openAdministradorMainView();
        } else {
            mostrarAlerta("Error", "Credenciales incorrectas para administrador.");
        }
    }

//    private void manejarLoginCajero(String correo, String contrasena) {
//        if (validarCajero(correo, contrasena)) {
//            app.openCajeroMainView();
//        } else {
//            mostrarAlerta("Error", "Credenciales incorrectas para cajero.");
//        }
//    }

    // Validaciones simuladas (puedes reemplazarlas con búsqueda real en Banco)

    private boolean validarAdmin(String correo, String contrasena) {

        Administrador administrador = app.banco.buscarAdminPorCorreo(correo);
        if (administrador != null && administrador.getContraseña().equals(contrasena)) {
            return true;
        }
        return false;
    }

    private boolean validarCajero(String correo, String contrasena) {
        return correo.equals("cajero@gmail.com") && contrasena.equals("123");
    }

    // Mostrar alertas genéricas

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}