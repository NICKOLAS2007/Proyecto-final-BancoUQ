package org.uniquindio.edu.co.poo.bancouqjfx.model;

import javafx.scene.layout.AnchorPane;
import org.uniquindio.edu.co.poo.bancouqjfx.model.CuentaBancaria;

public class CuentaCorriente extends CuentaBancaria {

    private double sobreGiroPermitido;

    public CuentaCorriente(String numeroCuenta, double saldo, double sobreGiroPermitido) {
        super(numeroCuenta, saldo, "corriente");

        this.sobreGiroPermitido = sobreGiroPermitido;
    }

    public void setSobreGiroPermitido(double sobreGiroPermitido) {
        this.sobreGiroPermitido = sobreGiroPermitido;
    }

    public double getSobreGiroPermitido() {
        return sobreGiroPermitido;
    }

    @Override
    public String toString() {
        return getNumeroCuenta() + " (Saldo: " + getSaldo() + ", Tipo: " + getTipoCuenta() +
                ", Sobregiro: $" + sobreGiroPermitido + ")";
    }
}

