package org.uniquindio.edu.co.poo.bancouqjfx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaEmpresarialTest {

    private CuentaEmpresarial cuentaEmpresarial;

    @BeforeEach
    public void setUp() {
        cuentaEmpresarial = new CuentaEmpresarial("9999", 5000.0, 3);
    }

    @Test
    public void testInicializacionCuentaEmpresarial() {
        assertEquals("9999", cuentaEmpresarial.getNumeroCuenta());
        assertEquals(5000.0, cuentaEmpresarial.getSaldo(), 0.001);
        assertEquals("empresarial", cuentaEmpresarial.getTipoCuenta());
        assertEquals(3, cuentaEmpresarial.getLimiteTransacciones());
        assertEquals(0, cuentaEmpresarial.getTransaccionesRealizadas());
    }

    @Test
    public void testPuedeHacerTransaccion() {
        assertTrue(cuentaEmpresarial.puedeHacerTransaccion());

        cuentaEmpresarial.registrarTransaccion();
        cuentaEmpresarial.registrarTransaccion();
        cuentaEmpresarial.registrarTransaccion();

        assertFalse(cuentaEmpresarial.puedeHacerTransaccion());
    }

    @Test
    public void testRegistrarTransaccion() {
        cuentaEmpresarial.registrarTransaccion();
        assertEquals(1, cuentaEmpresarial.getTransaccionesRealizadas());

        cuentaEmpresarial.registrarTransaccion();
        assertEquals(2, cuentaEmpresarial.getTransaccionesRealizadas());
    }
}