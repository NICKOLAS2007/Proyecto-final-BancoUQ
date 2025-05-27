package org.uniquindio.edu.co.poo.bancouqjfx.model;

import javafx.scene.layout.AnchorPane;
import org.uniquindio.edu.co.poo.bancouqjfx.model.CuentaBancaria;

public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(String numeroCuenta, double saldo, double tasaInteres) {
        super(numeroCuenta, saldo, "ahorros");
        this.tasaInteres = tasaInteres;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public void aplicarInteres() {
        double interes = getSaldo() * (tasaInteres / 100);
        setSaldo(getSaldo() + interes);
    }

    @Override
    public String toString() {
        return getNumeroCuenta() + " (Saldo: " + getSaldo() + ", Tipo: Ahorros, Tasa: " + tasaInteres + "%)";
    }
}

