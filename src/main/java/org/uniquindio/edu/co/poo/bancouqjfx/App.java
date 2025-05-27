package org.uniquindio.edu.co.poo.bancouqjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.uniquindio.edu.co.poo.bancouqjfx.model.Cliente;
import org.uniquindio.edu.co.poo.bancouqjfx.model.Banco;
import org.uniquindio.edu.co.poo.bancouqjfx.viewController.*;

public class App extends Application {

    private Stage primaryStage;
    public static Banco banco = new Banco("UQ");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestión de Clientes y Transacciones");

        // Mostrar la vista de selección de usuario al iniciar la app
        openSeleccionUsuarioView();
    }

    public void openSeleccionUsuarioView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/SeleccionUsuario.fxml"));
            Parent rootLayout = loader.load();

            SeleccionUsuarioController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Seleccione Tipo de Usuario");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que recibe el tipo de usuario y lo redirige al panel correspondiente
     */
    public void loguearUsuario(String tipoUsuario) {
        usuarioActual = tipoUsuario.toLowerCase(); // 👈
        inicializarData();

        switch (usuarioActual) {
            case "cliente":
                openClienteMainView();
                break;
            case "administrador":
                openAdministradorMainView();
                break;
            case "cajero":
                openCajeroMainView();
                break;
        }
    }

//
//    public void openViewPrincipal() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/primary.fxml"));
//            Parent rootLayout = loader.load();
//
//            PrimaryController primaryController = loader.getController();
//            primaryController.setApp(this);
//
//            Scene scene = new Scene(rootLayout);
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("Vista Principal");
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void openCrudCliente() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/crudCliente.fxml"));
            Parent rootLayout = loader.load();

            ClienteViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión Clientes");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openTransaccionView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/transaccionview.fxml"));
            Parent rootLayout = loader.load();

            TransaccionController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Transacciones");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCuentaView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/agregarCuenta.fxml"));
            Parent rootLayout = loader.load();

            CuentaController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Agregar Cuenta");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEmpleadoView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/empleado.fxml"));
            Parent rootLayout = loader.load();

            EmpleadoController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Empleados");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openConsultaSaldoView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/consultaSaldo.fxml"));
            Parent rootLayout = loader.load();

            ConsultaSaldoController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Consulta de Saldo");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openClienteMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/cliente.fxml"));
            Parent rootLayout = loader.load();

            ClienteMainController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Panel Cliente");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAdministradorMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/administrador.fxml"));
            Parent rootLayout = loader.load();

            AdministradorMainController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Panel Administrador");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCajeroMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/cajero.fxml"));
            Parent rootLayout = loader.load();

            CajeroMainController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Panel Cajero");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openRegistroClienteView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/registrarCliente.fxml"));
            Parent rootLayout = loader.load();

            RegistroClienteController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Registro de Cliente");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //    public void volverAlInicioSegunTipoUsuario(String tipoUsuario) {
//        switch (tipoUsuario.toLowerCase()) {
//            case "cliente":
//                openClienteMainView();
//                break;
//            case "administrador":
//                openAdministradorMainView();
//                break;
//            case "cajero":
//                openCajeroMainView();
//                break;
//            default:
//                openViewPrincipal();
//                break;
//        }
//    }
    public void openRegistroAdministradorView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/org/uniquindio/edu/co/poo/bancouqjfx/registrarAdmin.fxml"));
            Parent rootLayout = loader.load();

            RegistroAdministradorController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Registro de Administrador");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void inicializarData() {
        Cliente cliente = new Cliente("Juan", "Pérez", "1223", "primero@gmail.com", "123");
        banco.agregarCliente(cliente);
    }

    public static void main(String[] args) {
        launch();
    }

    public static String usuarioActual = "";
}