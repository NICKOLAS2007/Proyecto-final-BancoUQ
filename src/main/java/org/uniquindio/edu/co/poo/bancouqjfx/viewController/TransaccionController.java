package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.uniquindio.edu.co.poo.bancouqjfx.App;
import org.uniquindio.edu.co.poo.bancouqjfx.model.*;

import java.util.HashMap;
import java.util.Map;

public class TransaccionController {

    @FXML private TextField txtCuentaOrigen;
    @FXML private TextField txtCuentaDestino;
    @FXML private TextField txtMonto;
    @FXML private Button btnTransferir;
    @FXML private Button btnEliminar;
    @FXML private Label lblResultado;
    @FXML private TextArea areaHistorial;
    @FXML private ComboBox<String> cmbTipoCuentaOrigen;
    @FXML private ComboBox<String> cmbTipoCuentaDestino;
    @FXML private Label lblExtraInfo;
    @FXML private TextField txtExtraInfo;
    @FXML
    private Button btnVolver;

    private Banco banco;


    private GestorTransacciones gestor = new GestorTransacciones();

    @FXML
    public void initialize() {
        this.banco = App.banco;
        cmbTipoCuentaOrigen.setItems(FXCollections.observableArrayList("ahorros", "corriente", "empresarial"));
        cmbTipoCuentaDestino.setItems(FXCollections.observableArrayList("ahorros", "corriente", "empresarial"));

        cmbTipoCuentaOrigen.setConverter(crearStringConverter());
        cmbTipoCuentaDestino.setConverter(crearStringConverter());

        cmbTipoCuentaOrigen.getSelectionModel().selectFirst();
        cmbTipoCuentaDestino.getSelectionModel().selectFirst();

        cmbTipoCuentaOrigen.setOnAction(event -> actualizarCampoExtra());

        txtCuentaOrigen.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // perdió el foco
                actualizarCampoExtra();
            }
        });

        txtCuentaOrigen.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                actualizarCampoExtra();
            }
        });
    }

    private App app;

    public void setApp(App app) {
        this.app = app;
    }



    @FXML
    public void realizarTransaccion() {
        String origen = txtCuentaOrigen.getText().trim();
        String destino = txtCuentaDestino.getText().trim();
        String tipoOrigen = cmbTipoCuentaOrigen.getValue();
        String tipoDestino = cmbTipoCuentaDestino.getValue();
        double monto;

        try {
            monto = Double.parseDouble(txtMonto.getText().trim());
        } catch (NumberFormatException e) {
            lblResultado.setText("❌ Monto inválido.");
            return;
        }

        double dato = 0;
        String valorExtra = txtExtraInfo.getText().trim();
        if (!valorExtra.isEmpty()) {
            try {
                 dato = Double.parseDouble(valorExtra);
                System.out.println("⚙️ Dato adicional recibido: " + dato);
                // Aquí podrías usarlo para setear atributos específicos si defines subclases como CuentaAhorro, etc.
            } catch (NumberFormatException e) {
                lblResultado.setText("❌ El valor adicional debe ser numérico.");
                return;
            }
        }

        CuentaBancaria cuentaOrigen = banco.buscarCuenta(origen);
        CuentaBancaria cuentaDestino = banco.buscarCuenta(destino);


        if (cuentaOrigen == null) {
            lblResultado.setText("❌ La cuenta de origen no existe.");
            return;
        } else if (tipoOrigen == null || !normalizar(cuentaOrigen.getTipoCuenta()).equals(normalizar(tipoOrigen))) {
            lblResultado.setText("❌ El tipo seleccionado para la cuenta de origen no coincide. Esta cuenta es de tipo " +
                    cuentaOrigen.getTipoCuenta() + ".");
            return;
        }

        if (cuentaDestino == null) {
            lblResultado.setText("❌ La cuenta de destino no existe.");
            return;
        } else if (tipoDestino == null || !normalizar(cuentaDestino.getTipoCuenta()).equals(normalizar(tipoDestino))) {
            lblResultado.setText("❌ El tipo seleccionado para la cuenta de destino no coincide. Esta cuenta es de tipo " +
                    cuentaDestino.getTipoCuenta() + ".");
            return;
        }

        if (cuentaOrigen instanceof CuentaEmpresarial empresarial) {
            if (!empresarial.puedeHacerTransaccion()) {
                lblResultado.setText("❌ Límite de transacciones alcanzado.");
                areaHistorial.appendText("⚠️ La cuenta empresarial " + origen +
                        " ha alcanzado su límite de transacciones (50).\n");
                return;
            }
        }
        if (cuentaDestino != null) {

        }
        boolean exito = gestor.realizarTransaccion(cuentaOrigen, cuentaDestino, monto);

        if (exito) {
            if (cuentaOrigen instanceof CuentaEmpresarial empresarial) {
                empresarial.registrarTransaccion();
                int restantes = empresarial.getLimiteTransacciones() - empresarial.getTransaccionesRealizadas();
                String mensaje = "✅ Transacción exitosa. Te quedan " + restantes + " transacciones.";

                if (restantes <= 5) {
                    mensaje += " ⚠️ ¡Atención! Estás cerca del límite de transacciones.";
                }

                lblResultado.setText(mensaje);

            } else {
                lblResultado.setText("✅ Transacción exitosa.");
            }

            lblExtraInfo.setVisible(false);
            txtExtraInfo.setVisible(false);

        } else {
            lblResultado.setText("❌ Transacción fallida.");
        }

        actualizarHistorial();
    }


    @FXML
    public void eliminarTransaccion() {
        String origen = txtCuentaOrigen.getText().trim();
        String destino = txtCuentaDestino.getText().trim();

        // Se elimina desde el gestor
        boolean eliminado = gestor.getHistorialTransacciones().removeIf(t ->
                t.getCuentaOrigen().equals(origen) && t.getCuentaDestino().equals(destino)
        );

        if (eliminado) {
            lblResultado.setText("✅ Transacción eliminada.");
        } else {
            lblResultado.setText("❌ No se encontró la transacción.");
        }

        actualizarHistorial();
    }

    private void actualizarHistorial() {
        areaHistorial.clear();
        for (GestorTransacciones.Transaccion t : gestor.getHistorialTransacciones()) {
            String info = "Cuenta Origen: " + t.getCuentaOrigen() + " (" + t.getTipoCuentaOrigen() + ") → " +
                    "Cuenta Destino: " + t.getCuentaDestino() + " (" + t.getTipoCuentaDestino() + ") | " +
                    "Monto: $" + String.format("%.2f", t.getMonto());

            CuentaBancaria cuenta = banco.buscarCuenta(t.getCuentaOrigen());

            if (cuenta instanceof CuentaEmpresarial empresarial) {
                int restantes = empresarial.getLimiteTransacciones() - empresarial.getTransaccionesRealizadas();
                info += " | Transacciones restantes: " + restantes;
            }

            areaHistorial.appendText(info + "\n");
        }
    }
    @FXML
    public void actualizarTransaccion() {
        String origen = txtCuentaOrigen.getText().trim();
        String destino = txtCuentaDestino.getText().trim();
        double monto;

        try {
            monto = Double.parseDouble(txtMonto.getText().trim());
        } catch (NumberFormatException e) {
            lblResultado.setText("❌ Monto inválido.");
            return;
        }

        for (GestorTransacciones.Transaccion t : gestor.getHistorialTransacciones()) {
            if (t.getCuentaOrigen().equals(origen) && t.getCuentaDestino().equals(destino)) {
                t.setMonto(monto);
                t.setFechaHora(java.time.LocalDateTime.now());
                lblResultado.setText("✅ Transacción actualizada.");
                actualizarHistorial();
                return;
            }
        }

        lblResultado.setText("❌ Transacción no encontrada para actualizar.");

    }

//    @FXML
//    public void volverAPrincipal() {
//        if (app != null) {
//            app.openViewPrincipal();
//        }
//    }

    private void actualizarCampoExtra()  {
        String cuentaNum = txtCuentaOrigen.getText().trim();
        String tipo = cmbTipoCuentaOrigen.getValue();

        if (tipo == null) {
            lblExtraInfo.setVisible(false);
            txtExtraInfo.setVisible(false);
            return;
        }
        CuentaBancaria cuenta = banco.buscarCuenta(cuentaNum);

        if (cuenta == null) {
            lblExtraInfo.setText("❌ La cuenta no existe.");
            lblExtraInfo.setVisible(true);
            txtExtraInfo.setVisible(false);
            return;
        }

        switch (tipo.toLowerCase()) {
            case "ahorros":
                if (cuenta instanceof CuentaAhorro ahorro) {
                    lblExtraInfo.setText("Tasa de interés: " + ahorro.getTasaInteres() + "%");
                } else {
                    lblExtraInfo.setText("⚠️ Esta no es una cuenta de ahorros.");
                }
                lblExtraInfo.setVisible(true);
                txtExtraInfo.setVisible(false);
                break;

            case "corriente":
                if (cuenta instanceof CuentaCorriente corriente) {
                    lblExtraInfo.setText("Sobregiro permitido: $" + corriente.getSobreGiroPermitido());
                } else {
                    lblExtraInfo.setText("⚠️ Esta no es una cuenta corriente.");
                }
                lblExtraInfo.setVisible(true);
                txtExtraInfo.setVisible(false);
                break;

            case "empresarial":
                lblExtraInfo.setVisible(false);
                txtExtraInfo.setVisible(false);
                break;
            default:
                lblExtraInfo.setVisible(false);
                txtExtraInfo.setVisible(false);
        }

    }

    private StringConverter<String> crearStringConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(String tipo) {
                // Convierte para mostrar al usuario (primera letra en mayúscula)
                if (tipo == null) return "";
                return tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase();
            }

            @Override
            public String fromString(String string) {
                // Convierte al valor interno (todo en minúscula)
                return string.toLowerCase();
            }
        };
    }
    private String normalizar(String tipo) {
        return tipo == null ? "" : tipo.trim().toLowerCase();
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