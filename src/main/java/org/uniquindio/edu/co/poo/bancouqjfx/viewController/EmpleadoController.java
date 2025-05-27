package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.uniquindio.edu.co.poo.bancouqjfx.App;
import org.uniquindio.edu.co.poo.bancouqjfx.model.Empleado;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField cedulaField;
    @FXML

    private ListView<Empleado> listaEmpleados;

    private List<Empleado> empleados = new ArrayList<>();

    private App app; // Instancia de App

    public void setApp(App app) {
        this.app = app;
    }
    @FXML
    private Button btnVolver;

    @FXML
    public void registrarEmpleado() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String cedula = cedulaField.getText();

        if (!nombre.isEmpty() && !apellido.isEmpty() && !cedula.isEmpty()) {
            Empleado empleado = new Empleado(nombre, apellido, cedula);
            empleados.add(empleado);
            actualizarLista();
            limpiarCampos();
        }
    }

    @FXML
    public void modificarEmpleado() {
        Empleado seleccionado = listaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(nombreField.getText());
            seleccionado.setApellido(apellidoField.getText());
            seleccionado.setCedula(cedulaField.getText());
            actualizarLista();
            limpiarCampos();
        }
    }

    @FXML
    public void eliminarEmpleado() {
        Empleado seleccionado = listaEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            empleados.remove(seleccionado);
            actualizarLista();
            limpiarCampos();
        }
    }

//    @FXML
//    public void volverAPrincipal() {
//        if (app != null) {
//            app.openViewPrincipal();
//        }
//    }

    private void actualizarLista() {
        listaEmpleados.getItems().setAll(empleados);
    }

    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        cedulaField.clear();
    }
    @FXML
    public void volver() {
        if (app != null) {
            switch (App.usuarioActual) {
                case "cliente":
                    app.openClienteMainView(); // 👈 Panel cliente
                    break;
                case "administrador":
                    app.openAdministradorMainView(); // 👈 Panel admin
                    break;
                case "cajero":
                    app.openCajeroMainView(); // 👈 Panel cajero
                    break;
                default:
                    app.openSeleccionUsuarioView(); // fallback si algo falla
                    break;
            }
        }
    }
}