package org.uniquindio.edu.co.poo.bancouqjfx.model;

import javafx.scene.layout.AnchorPane;
import org.uniquindio.edu.co.poo.bancouqjfx.model.CuentaBancaria;

public class CuentaEmpresarial extends CuentaBancaria {
    private int limiteTransacciones;
    private int transaccionesRealizadas;

    public CuentaEmpresarial(String numeroCuenta, double saldo, int limiteTransacciones) {
        super(numeroCuenta,saldo,"empresarial");
        this.limiteTransacciones = limiteTransacciones;
        this.transaccionesRealizadas = 0;
    }

    public int getLimiteTransacciones() {
        return limiteTransacciones;
    }

    public int getTransaccionesRealizadas() {
        return transaccionesRealizadas;
    }

    public void setLimiteTransacciones(int limiteTransacciones) {
        this.limiteTransacciones = limiteTransacciones;
    }

    public void setTransaccionesRealizadas(int transaccionesRealizadas) {
        this.transaccionesRealizadas = transaccionesRealizadas;
    }

    public boolean puedeHacerTransaccion() {
        return transaccionesRealizadas < limiteTransacciones;
    }

    public void registrarTransaccion() {

        transaccionesRealizadas++;
    }

    @Override
    public String toString() {
        return getNumeroCuenta() + " (Saldo: " + getSaldo() + ", Tipo: " + getTipoCuenta()
                + ", Límite: " + limiteTransacciones + ", Usadas: " + transaccionesRealizadas + ")";
    }
}

