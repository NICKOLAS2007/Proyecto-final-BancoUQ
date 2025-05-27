package org.uniquindio.edu.co.poo.bancouqjfx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaAhorroTest {

    private CuentaAhorro cuentaAhorro;

    @BeforeEach
    public void setUp() {
        cuentaAhorro = new CuentaAhorro("4444", 1000.0, 5.0);
    }

    @Test
    public void testInicializacionCuentaAhorro() {
        assertEquals("4444", cuentaAhorro.getNumeroCuenta());
        assertEquals(1000.0, cuentaAhorro.getSaldo(), 0.001);
        assertEquals("ahorros", cuentaAhorro.getTipoCuenta());
        assertEquals(5.0, cuentaAhorro.getTasaInteres(), 0.001);
    }

    @Test
    public void testAplicarInteres() {
        cuentaAhorro.aplicarInteres(); // 5% de 1000 = 50 -> nuevo saldo: 1050
        assertEquals(1050.0, cuentaAhorro.getSaldo(), 0.001);
    }

    @Test
    public void testSetTasaInteres() {
        cuentaAhorro.setTasaInteres(7.5);
        assertEquals(7.5, cuentaAhorro.getTasaInteres(), 0.001);
    }
}