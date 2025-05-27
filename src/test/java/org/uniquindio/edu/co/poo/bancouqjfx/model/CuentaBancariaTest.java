package org.uniquindio.edu.co.poo.bancouqjfx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CuentaBancariaTest {

    private CuentaBancaria cuenta;

    @BeforeEach
    public void setUp() {
        cuenta = new CuentaBancaria("1111", 1000.0, "Ahorros");
    }

    @Test
    public void testDepositar() {
        cuenta.depositar(200);
        assertEquals(1200.0, cuenta.getSaldo(), 0.001, "El saldo debería aumentar tras el depósito");
    }

    @Test
    public void testRetiroExitoso() {
        boolean resultado = cuenta.retirar(300);
        assertTrue(resultado, "El retiro debería ser exitoso");
        assertEquals(700.0, cuenta.getSaldo(), 0.001, "El saldo debería reducirse tras el retiro");
    }

    @Test
    public void testRetiroFallidoPorSaldoInsuficiente() {
        boolean resultado = cuenta.retirar(1500);
        assertFalse(resultado, "El retiro no debería ser posible con saldo insuficiente");
        assertEquals(1000.0, cuenta.getSaldo(), 0.001, "El saldo no debería cambiar si el retiro falla");
    }
}