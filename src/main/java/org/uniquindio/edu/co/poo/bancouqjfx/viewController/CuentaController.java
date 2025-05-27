package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.uniquindio.edu.co.poo.bancouqjfx.App;
import org.uniquindio.edu.co.poo.bancouqjfx.model.*;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CuentaController {

    @FXML private TextField txtNumeroCuenta;
    @FXML private TextField txtSaldo;
    @FXML private Label lblMensaje;
    @FXML private ListView<CuentaBancaria> listaCuentas;
    @FXML private ComboBox<String> tipoCuenta;
    @FXML private Label lblCampoExtra;
    @FXML private TextField txtCampoExtra;
    @FXML  private Button btnVolver;
    private Banco banco;
    private App app; // 👈 Atributo que necesitas

    // 👇 Este método es necesario para poder usar controller.setApp(this)
    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    public void initialize() {
        this.banco = App.banco;
        actualizarVistaCuentas();

        tipoCuenta.getItems().addAll("Ahorros", "Corriente", "Empresarial");

        tipoCuenta.setOnAction(event -> {
            String tipo = tipoCuenta.getValue();
            switch (tipo) {
                case "Corriente":
                    lblCampoExtra.setText("Sobregiro permitido: ");
                    lblCampoExtra.setVisible(true);
                    txtCampoExtra.setPromptText("Ej: 500000");
                    txtCampoExtra.setVisible(true);
                    break;
                case "Ahorros":
                    lblCampoExtra.setText("Tasa de interés: 2%");
                    lblCampoExtra.setVisible(true);
                    txtCampoExtra.setVisible(false); // no editable
                    break;
                case "Empresarial":
                    lblCampoExtra.setText("Límite de transacciones: 50");
                    lblCampoExtra.setVisible(true);
                    txtCampoExtra.setVisible(false);
                    break;
                default:
                    lblCampoExtra.setVisible(false);
                    txtCampoExtra.setVisible(false);
            }
        });
        tipoCuenta.getSelectionModel().selectFirst();
        tipoCuenta.getOnAction().handle(new javafx.event.ActionEvent());
    }

    @FXML
    public void agregarCuenta() {
        String numero = txtNumeroCuenta.getText().trim();
        String tipo = tipoCuenta.getValue();
        double saldo;

        if (tipo == null) {
            lblMensaje.setText("❌ Debes seleccionar un tipo de cuenta.");
            return;
        }

        try {
            saldo = Double.parseDouble(txtSaldo.getText().trim());
        } catch (NumberFormatException e) {
            lblMensaje.setText("❌ Saldo inválido.");
            return;
        }

        CuentaBancaria cuenta;

        switch (tipo.toLowerCase()) {
            case "ahorros":
                cuenta = new CuentaAhorro(numero, saldo, 2);
                break;
            case "corriente":
                double sobregiro;
                try{
                    sobregiro = Double.parseDouble(txtCampoExtra.getText().trim());
                }catch (NumberFormatException e){lblMensaje.setText("❌ Sobregiro inválido.");
                    return;
                }
                cuenta = new CuentaCorriente(numero, saldo, sobregiro);
                break;
            case "empresarial":
                cuenta = new CuentaEmpresarial(numero, saldo, 50);
                break;
            default:
                lblMensaje.setText("❌ Tipo de cuenta no válido.");
                return;
        }

        if (banco.agregarCuenta(cuenta)) {
            lblMensaje.setText("✅ Cuenta agregada.");
            limpiarCampos();
            actualizarVistaCuentas();
        } else {
            lblMensaje.setText("❌ La cuenta ya existe.");
        }
    }

    @FXML
    public void eliminarCuenta() {
        String numero = txtNumeroCuenta.getText().trim();

        if (banco.eliminarCuentas(numero)) {
            lblMensaje.setText("✅ Cuenta eliminada.");
            limpiarCampos();
            actualizarVistaCuentas();
        } else {
            lblMensaje.setText("❌ Cuenta no encontrada.");
        }
    }

    @FXML
    public void actualizarCuenta() {
        String numero = txtNumeroCuenta.getText().trim();
        String tipo = tipoCuenta.getValue();
        double saldo;

        if (tipo == null) {
            lblMensaje.setText("❌ Debes seleccionar un tipo de cuenta.");
            return;
        }

        try {
            saldo = Double.parseDouble(txtSaldo.getText().trim());
        } catch (NumberFormatException e) {
            lblMensaje.setText("❌ Saldo inválido.");
            return;
        }

        CuentaBancaria cuentaExistente = banco.buscarCuenta(numero);
        if (cuentaExistente == null) {
            lblMensaje.setText("❌ Cuenta no encontrada.");
            return;
        }
        cuentaExistente.setSaldo(saldo);


        switch (tipo.toLowerCase()) {
            case "corriente":
                if (cuentaExistente instanceof CuentaCorriente corriente) {
                    if (!txtCampoExtra.isVisible() || txtCampoExtra.getText().trim().isEmpty()) {
                        lblMensaje.setText("❌ Debes ingresar un sobregiro para actualizar.");
                        return;
                    }
                    try {
                        double sobregiro = Double.parseDouble(txtCampoExtra.getText().trim());
                        corriente.setSobreGiroPermitido(sobregiro);
                    } catch (NumberFormatException e) {
                        lblMensaje.setText("❌ Sobregiro inválido.");
                        return;
                    }
                }
                break;

            case "empresarial":
                    if (cuentaExistente instanceof CuentaEmpresarial empresarial) {
                        empresarial.setLimiteTransacciones(50);
                    }
                break;
            case "ahorros":
                if (cuentaExistente instanceof CuentaAhorro ahorro) {
                    ahorro.setTasaInteres(2); // Valor fijo por ahora
                }
                break;
        }

        lblMensaje.setText("✅ Cuenta actualizada.");
        limpiarCampos();
        actualizarVistaCuentas();
    }

    @FXML
    public void seleccionarCuenta(MouseEvent event) {
        CuentaBancaria cuenta = listaCuentas.getSelectionModel().getSelectedItem();
        if (cuenta != null) {
            txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
            txtSaldo.setText(String.valueOf(cuenta.getSaldo()));
            String tipoCapitalizado = cuenta.getTipoCuenta().substring(0,1).toUpperCase() + cuenta.getTipoCuenta().substring(1).toLowerCase();
            tipoCuenta.setValue(tipoCapitalizado);
            switch (cuenta.getTipoCuenta()) {
                case "Corriente":
                    lblCampoExtra.setText("Sobregiro permitido:");
                    if (cuenta instanceof CuentaCorriente corriente) {
                        txtCampoExtra.setText(String.valueOf(corriente.getSobreGiroPermitido()));
                    } else {
                        txtCampoExtra.setText(""); // Para prevenir errores si no es del tipo correcto
                    }
                    lblCampoExtra.setVisible(true);
                    txtCampoExtra.setVisible(true);
                    break;
                case "Empresarial":
                        lblCampoExtra.setText("Límite de transacciones: 50");
                        lblCampoExtra.setVisible(true);
                        txtCampoExtra.setVisible(false);
                    break;
                case "Ahorros":
                    lblCampoExtra.setText("Tasa de interés: 2%");
                    txtCampoExtra.clear();
                    lblCampoExtra.setVisible(true);
                    txtCampoExtra.setVisible(false);
                    txtCampoExtra.setText("");
                    break;
                default:
                    lblCampoExtra.setVisible(false);
                    txtCampoExtra.setVisible(false);
            }
            tipoCuenta.getOnAction().handle(new javafx.event.ActionEvent());
        }
    }

    private void actualizarVistaCuentas() {
        ObservableList<CuentaBancaria> cuentas = FXCollections.observableArrayList(banco.getListaCuentas());
        listaCuentas.setItems(cuentas);
        listaCuentas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(CuentaBancaria cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (empty || cuenta == null) {
                    setText(null);
                } else {
                    String info = cuenta.getNumeroCuenta() + " - " + cuenta.getTipoCuenta() + " - $" + cuenta.getSaldo();

                    if (cuenta instanceof CuentaEmpresarial empresarial) {
                        info += " | Usadas: " + empresarial.getTransaccionesRealizadas() +
                                " / " + empresarial.getLimiteTransacciones();
                    }

                    if (cuenta instanceof CuentaCorriente corriente) {
                        info += " | Sobregiro: $" + corriente.getSobreGiroPermitido();
                    }

                    if (cuenta instanceof CuentaAhorro ahorro) {
                        info += " | Interés: " + ahorro.getTasaInteres() + "%";
                    }

                    setText(info);
                }
            }
        });
    }

    private void limpiarCampos() {
        txtNumeroCuenta.clear();
        txtSaldo.clear();
        tipoCuenta.getSelectionModel().clearSelection();
        listaCuentas.getSelectionModel().clearSelection();
        txtCampoExtra.clear();
        lblCampoExtra.setVisible(false);
        txtCampoExtra.setVisible(false);
    }

    @FXML
    public void aplicarInteres() {
        CuentaBancaria cuenta = listaCuentas.getSelectionModel().getSelectedItem();

        if (cuenta instanceof CuentaAhorro ahorro) {
            ahorro.aplicarInteres();
            lblMensaje.setText("✅ Interés aplicado. Nuevo saldo: $" + ahorro.getSaldo());
            actualizarVistaCuentas();
        } else {
            lblMensaje.setText("❌ Selecciona una cuenta de ahorro para aplicar interés.");
        }
    }

//    @FXML
//    public void volverAPrincipal() {
//        if (app != null) {
//            app.openViewPrincipal();
//        }
//    }
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
