package org.uniquindio.edu.co.poo.bancouqjfx.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorTransacciones {

    // Lista para guardar el historial de transacciones
    private List<Transaccion> historialTransacciones;

    public GestorTransacciones() {
        historialTransacciones = new ArrayList<>();
    }

    // Método para realizar la transacción (mover dinero entre cuentas)
    public boolean realizarTransaccion(CuentaBancaria origen, CuentaBancaria destino, double monto) {
        if (origen == null || destino == null) {
            System.out.println("Error: Cuenta origen o destino no válida.");
            return false;
        }

        if (monto <= 0) {
            System.out.println("Error: El monto debe ser mayor a cero.");
            return false;
        }

        if (origen.getSaldo() >= monto) {
            origen.retirar(monto);
            destino.depositar(monto);

            // Crear y guardar la transacción
            Transaccion t = new Transaccion(
                    origen.getNumeroCuenta(),
                    origen.getTipoCuenta(),
                    destino.getNumeroCuenta(),
                    destino.getTipoCuenta(),
                    monto,
                    LocalDateTime.now()
            );
            historialTransacciones.add(t);

            System.out.println("Transacción realizada con éxito.");
            return true;
        } else {
            System.out.println("Error: Saldo insuficiente en la cuenta de origen.");
            return false;
        }
    }

    // Método para acceder al historial
    public List<Transaccion> getHistorialTransacciones() {
        return historialTransacciones;
    }

    // Clase interna Transaccion
    public static class Transaccion {
        private String cuentaOrigen;
        private String tipoCuentaOrigen;
        private String cuentaDestino;
        private String tipoCuentaDestino;
        private double monto;
        private LocalDateTime fechaHora;

        public Transaccion(String cuentaOrigen, String tipoCuentaOrigen, String cuentaDestino, String tipoCuentaDestino, double monto, LocalDateTime fechaHora) {
            this.cuentaOrigen = cuentaOrigen;
            this.tipoCuentaOrigen = tipoCuentaOrigen;
            this.cuentaDestino = cuentaDestino;
            this.tipoCuentaDestino = tipoCuentaDestino;
            this.monto = monto;
            this.fechaHora = fechaHora;
        }

        public String getCuentaOrigen() {
            return cuentaOrigen;
        }

        public String getTipoCuentaOrigen() {
            return tipoCuentaOrigen;
        }

        public String getCuentaDestino() {
            return cuentaDestino;
        }

        public String getTipoCuentaDestino() {
            return tipoCuentaDestino;
        }

        public double getMonto() {
            return monto;
        }

        public LocalDateTime getFechaHora() {
            return fechaHora;
        }

        public void setCuentaOrigen(String cuentaOrigen) {
            this.cuentaOrigen = cuentaOrigen;
        }

        public void setTipoCuentaOrigen(String tipoCuentaOrigen) {
            this.tipoCuentaOrigen = tipoCuentaOrigen;
        }

        public void setCuentaDestino(String cuentaDestino) {
            this.cuentaDestino = cuentaDestino;
        }

        public void setTipoCuentaDestino(String tipoCuentaDestino) {
            this.tipoCuentaDestino = tipoCuentaDestino;
        }

        public void setMonto(double monto) {
            this.monto = monto;
        }

        public void setFechaHora(LocalDateTime fechaHora) {
            this.fechaHora = fechaHora;
        }

        @Override
        public String toString() {
            return "Transacción de " + monto + " desde " + cuentaOrigen + " (" + tipoCuentaOrigen + ") a " + cuentaDestino + " (" + tipoCuentaDestino + ") el " + fechaHora;
        }
    }
}
