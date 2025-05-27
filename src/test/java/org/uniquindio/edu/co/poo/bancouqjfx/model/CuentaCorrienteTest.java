package org.uniquindio.edu.co.poo.bancouqjfx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaCorrienteTest {

    private CuentaCorriente cuentaCorriente;

    @BeforeEach
    public void setUp() {
        cuentaCorriente = new CuentaCorriente("3333", 1500.0, 300.0);
    }

    @Test
    public void testInicializacionCuentaCorriente() {
        assertEquals("3333", cuentaCorriente.getNumeroCuenta());
        assertEquals(1500.0, cuentaCorriente.getSaldo(), 0.001);
        assertEquals("corriente", cuentaCorriente.getTipoCuenta());
        assertEquals(300.0, cuentaCorriente.getSobreGiroPermitido(), 0.001);
    }

    @Test
    public void testSetSobreGiroPermitido() {
        cuentaCorriente.setSobreGiroPermitido(500.0);
        assertEquals(500.0, cuentaCorriente.getSobreGiroPermitido(), 0.001);
    }


}