package org.uniquindio.edu.co.poo.bancouqjfx.viewController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.uniquindio.edu.co.poo.bancouqjfx.App;
import org.uniquindio.edu.co.poo.bancouqjfx.model.*;

public class TransaccionController {

    @FXML private TextField txtCuentaOrigen;
    @FXML private TextField txtCuentaDestino;
    @FXML private TextField txtMonto;
    @FXML private Button btnTransferir;
    @FXML private Button btnEliminar;
    @FXML private Label lblResultado;
    @FXML private ListView<String> listaHistorial;
    @FXML private ComboBox<String> cmbTipoCuentaOrigen;
    @FXML private ComboBox<String> cmbTipoCuentaDestino;
    @FXML private Label lblExtraInfo;
    @FXML private Button btnVolver;

    private Banco banco;
    private GestorTransacciones gestor = new GestorTransacciones();
    private App app;

    @FXML
    public void initialize() {
        banco = App.banco;

        // Configurar ComboBoxes con tipos de cuenta y converters
        var tiposCuenta = FXCollections.observableArrayList("ahorros", "corriente", "empresarial");
        cmbTipoCuentaOrigen.setItems(tiposCuenta);
        cmbTipoCuentaDestino.setItems(tiposCuenta);
        cmbTipoCuentaOrigen.setConverter(crearStringConverter());
        cmbTipoCuentaDestino.setConverter(crearStringConverter());

        cmbTipoCuentaOrigen.getSelectionModel().selectFirst();
        cmbTipoCuentaDestino.getSelectionModel().selectFirst();

        // Listener para actualizar campo extra al cambiar tipo o cuenta origen
        cmbTipoCuentaOrigen.setOnAction(e -> actualizarCampoExtra());

        txtCuentaOrigen.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) actualizarCampoExtra();
        });

        txtCuentaOrigen.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) actualizarCampoExtra();
        });
    }

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    public void realizarTransaccion() {
        String origen = txtCuentaOrigen.getText().trim();
        String destino = txtCuentaDestino.getText().trim();
        String tipoOrigen = cmbTipoCuentaOrigen.getValue();
        String tipoDestino = cmbTipoCuentaDestino.getValue();

        double monto = parseDoubleOrShowError(txtMonto.getText().trim(), "Monto inválido");
        if (monto < 0) return;

        // Validar dato extra si está visible
        double datoExtra = 0;
        if (lblExtraInfo.isVisible()) {
            String extra = lblExtraInfo.getText().trim();
            if (!extra.isEmpty()) {
                datoExtra = parseDoubleOrShowError(extra, "El valor adicional debe ser numérico");
                if (datoExtra < 0) return;
            }
        }

        CuentaBancaria cuentaOrigen = banco.buscarCuenta(origen);
        CuentaBancaria cuentaDestino = banco.buscarCuenta(destino);

        if (!validarCuenta(cuentaOrigen, tipoOrigen, "origen")) return;
        if (!validarCuenta(cuentaDestino, tipoDestino, "destino")) return;

        // Validar límite de transacciones para cuenta empresarial origen
        if (cuentaOrigen instanceof CuentaEmpresarial empresarial) {
            if (!empresarial.puedeHacerTransaccion()) {
                mostrarErrorYAgregarHistorial("Límite de transacciones alcanzado para cuenta empresarial " + origen);
                return;
            }
        }

        boolean exito = gestor.realizarTransaccion(cuentaOrigen, cuentaDestino, monto);

        if (exito) {
            if (cuentaOrigen instanceof CuentaEmpresarial empresarial) {
                empresarial.registrarTransaccion();
                int restantes = empresarial.getLimiteTransacciones() - empresarial.getTransaccionesRealizadas();
                String mensaje = "✅ Transacción exitosa. Te quedan " + restantes + " transacciones.";
                if (restantes <= 5) mensaje += " ⚠️ ¡Atención! Estás cerca del límite de transacciones.";
                lblResultado.setText(mensaje);
            } else {
                lblResultado.setText("✅ Transacción exitosa.");
            }
            ocultarCampoExtra();
        } else {
            lblResultado.setText("❌ Transacción fallida.");
        }

        actualizarHistorial();
    }

    @FXML
    public void eliminarTransaccion() {
        String origen = txtCuentaOrigen.getText().trim();
        String destino = txtCuentaDestino.getText().trim();

        boolean eliminado = gestor.getHistorialTransacciones().removeIf(t ->
                t.getCuentaOrigen().equals(origen) && t.getCuentaDestino().equals(destino)
        );

        lblResultado.setText(eliminado ? "✅ Transacción eliminada." : "❌ No se encontró la transacción.");
        actualizarHistorial();
    }

    @FXML
    public void actualizarTransaccion() {
        String origen = txtCuentaOrigen.getText().trim();
        String destino = txtCuentaDestino.getText().trim();
        double monto = parseDoubleOrShowError(txtMonto.getText().trim(), "Monto inválido");
        if (monto < 0) return;

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

    private void actualizarHistorial() {
        listaHistorial.getItems().clear();
        for (GestorTransacciones.Transaccion t : gestor.getHistorialTransacciones()) {
            String info = String.format("Cuenta Origen: %s (%s) → Cuenta Destino: %s (%s) | Monto: $%.2f",
                    t.getCuentaOrigen(), t.getTipoCuentaOrigen(),
                    t.getCuentaDestino(), t.getTipoCuentaDestino(),
                    t.getMonto());

            CuentaBancaria cuenta = banco.buscarCuenta(t.getCuentaOrigen());
            if (cuenta instanceof CuentaEmpresarial empresarial) {
                int restantes = empresarial.getLimiteTransacciones() - empresarial.getTransaccionesRealizadas();
                info += " | Transacciones restantes: " + restantes;
            }

            listaHistorial.getItems().add(info);
        }
    }

    private void actualizarCampoExtra() {
        String cuentaNum = txtCuentaOrigen.getText().trim();
        String tipo = cmbTipoCuentaOrigen.getValue();

        if (tipo == null || tipo.isEmpty()) {
            ocultarCampoExtra();
            return;
        }

        CuentaBancaria cuenta = banco.buscarCuenta(cuentaNum);

        if (cuenta == null) {
            lblExtraInfo.setText("❌ La cuenta no existe.");
            mostrarCampoExtra(false);
            return;
        }

        if (!normalizar(cuenta.getTipoCuenta()).equals(normalizar(tipo))) {
            lblExtraInfo.setText("❌ El tipo seleccionado no coincide con la cuenta.");
            mostrarCampoExtra(false);
            return;
        }

        switch (tipo.toLowerCase()) {
            case "ahorros" -> {
                if (cuenta instanceof CuentaAhorro ahorro) {
                    lblExtraInfo.setText("Tasa de interés: " + ahorro.getTasaInteres() + "%");
                    mostrarCampoExtra(false);
                } else {
                    lblExtraInfo.setText("⚠️ Esta no es una cuenta de ahorros.");
                    mostrarCampoExtra(false);
                }
            }
            case "corriente" -> {
                if (cuenta instanceof CuentaCorriente corriente) {
                    lblExtraInfo.setText("Sobregiro permitido: $" + corriente.getSobreGiroPermitido());
                    mostrarCampoExtra(false);
                } else {
                    lblExtraInfo.setText("⚠️ Esta no es una cuenta corriente.");
                    mostrarCampoExtra(false);
                }
            }
            case "empresarial" -> ocultarCampoExtra();
            default -> ocultarCampoExtra();
        }
    }

    private void ocultarCampoExtra() {
        lblExtraInfo.setVisible(false);
        lblExtraInfo.setVisible(false);
    }

    private void mostrarCampoExtra(boolean mostrarTxtExtra) {
        lblExtraInfo.setVisible(true);
        lblExtraInfo.setVisible(mostrarTxtExtra);
    }

    private boolean validarCuenta(CuentaBancaria cuenta, String tipo, String nombreCampo) {
        if (cuenta == null) {
            lblResultado.setText("❌ La cuenta de " + nombreCampo + " no existe.");
            return false;
        }
        if (tipo == null || !normalizar(cuenta.getTipoCuenta()).equals(normalizar(tipo))) {
            lblResultado.setText("❌ El tipo seleccionado para la cuenta de " + nombreCampo + " no coincide. Esta cuenta es de tipo "
                    + cuenta.getTipoCuenta() + ".");
            return false;
        }
        return true;
    }

    private double parseDoubleOrShowError(String valor, String mensajeError) {
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            lblResultado.setText("❌ " + mensajeError + ".");
            return -1;
        }
    }

    private void mostrarErrorYAgregarHistorial(String mensaje) {
        lblResultado.setText("❌ " + mensaje);
        listaHistorial.getItems().add("⚠️ " + mensaje);
    }

    private StringConverter<String> crearStringConverter() {
        return new StringConverter<>() {
            @Override
            public String toString(String tipo) {
                if (tipo == null) return "";
                return tipo.substring(0, 1).toUpperCase() + tipo.substring(1).toLowerCase();
            }

            @Override
            public String fromString(String string) {
                return string.toLowerCase();
            }
        };
    }

    private String normalizar(String tipo) {
        return tipo == null ? "" : tipo.trim().toLowerCase();
    }

    @FXML
    public void volver() {
        if (app == null) return;
        switch (App.usuarioActual) {
            case "cliente" -> app.openClienteMainView();
            case "administrador" -> app.openAdministradorMainView();
            case "cajero" -> app.openCajeroMainView();
            default -> app.openSeleccionUsuarioView();
        }
    }
}
