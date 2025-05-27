package org.uniquindio.edu.co.poo.bancouqjfx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GestorTransaccionesTest {

    private GestorTransacciones gestor;
    private CuentaBancaria cuentaOrigen;
    private CuentaBancaria cuentaDestino;

    @BeforeEach
    public void setUp() {
        gestor = new GestorTransacciones();
        cuentaOrigen = new CuentaAhorro("1111", 1000.0, 0.02);
        cuentaDestino = new CuentaAhorro("2222", 500.0, 0.02);
    }

    @Test
    public void testTransaccionExitosa() {
        boolean resultado = gestor.realizarTransaccion(cuentaOrigen, cuentaDestino, 300.0);
        assertTrue(resultado);
        assertEquals(700.0, cuentaOrigen.getSaldo(), 0.001);
        assertEquals(800.0, cuentaDestino.getSaldo(), 0.001);

        List<GestorTransacciones.Transaccion> historial = gestor.getHistorialTransacciones();
        assertEquals(1, historial.size());
        GestorTransacciones.Transaccion transaccion = historial.get(0);
        assertEquals("1111", transaccion.getCuentaOrigen());
        assertEquals("2222", transaccion.getCuentaDestino());
        assertEquals(300.0, transaccion.getMonto(), 0.001);
    }

    @Test
    public void testTransaccionMontoNegativo() {
        boolean resultado = gestor.realizarTransaccion(cuentaOrigen, cuentaDestino, -50.0);
        assertFalse(resultado);
        assertEquals(1000.0, cuentaOrigen.getSaldo(), 0.001);
        assertEquals(500.0, cuentaDestino.getSaldo(), 0.001);
        assertEquals(0, gestor.getHistorialTransacciones().size());
    }

    @Test
    public void testTransaccionMontoCero() {
        boolean resultado = gestor.realizarTransaccion(cuentaOrigen, cuentaDestino, 0.0);
        assertFalse(resultado);
        assertEquals(0, gestor.getHistorialTransacciones().size());
    }

    @Test
    public void testTransaccionSaldoInsuficiente() {
        boolean resultado = gestor.realizarTransaccion(cuentaOrigen, cuentaDestino, 2000.0);
        assertFalse(resultado);
        assertEquals(1000.0, cuentaOrigen.getSaldo(), 0.001);
        assertEquals(500.0, cuentaDestino.getSaldo(), 0.001);
        assertEquals(0, gestor.getHistorialTransacciones().size());
    }

    @Test
    public void testCuentaOrigenNull() {
        boolean resultado = gestor.realizarTransaccion(null, cuentaDestino, 100.0);
        assertFalse(resultado);
        assertEquals(0, gestor.getHistorialTransacciones().size());
    }


}