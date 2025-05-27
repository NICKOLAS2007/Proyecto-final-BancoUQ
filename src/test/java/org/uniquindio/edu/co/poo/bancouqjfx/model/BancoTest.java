package org.uniquindio.edu.co.poo.bancouqjfx.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BancoTest {

    private Cliente cliente1;
    private Cliente cliente2;
    private Administrador admin;
    private CuentaBancaria cuenta1;
    private CuentaBancaria cuenta2;
    private GestorTransacciones gestor;

    @BeforeEach
    public void setUp() {
        cliente1 = new Cliente("Juan", "Perez", "123456789", "juan@example.com", "pass123");
        cliente2 = new Cliente("Ana", "Gomez", "987654321", "ana@example.com", "pass456");

        admin = new Administrador("Carlos", "Lopez", "555555555", new LinkedList<>(), "admin@banco.com", "adminpass");

        cuenta1 = new CuentaBancaria("1111", 1000.0, "ahorros");
        cuenta2 = new CuentaBancaria("2222", 500.0, "corriente");

        gestor = new GestorTransacciones();
    }

    @Test
    public void testAgregarCliente() {
        boolean agregado = admin.agregarCliente(cliente1);
        assertTrue(agregado, "El cliente debería agregarse correctamente");

        // Intentar agregar mismo cliente (por cédula)
        boolean agregado2 = admin.agregarCliente(cliente1);
        assertFalse(agregado2, "No se debería permitir agregar cliente repetido");
    }

    @Test
    public void testEliminarCliente() {
        admin.agregarCliente(cliente1);
        boolean eliminado = admin.eliminarCliente(cliente1.getCedula());
        assertTrue(eliminado, "El cliente debería eliminarse correctamente");

        // Eliminar cliente inexistente
        boolean eliminado2 = admin.eliminarCliente("000000000");
        assertFalse(eliminado2, "No debería eliminar cliente inexistente");
    }

    @Test
    public void testActualizarCliente() {
        admin.agregarCliente(cliente1);

        Cliente actualizado = new Cliente("Juan Carlos", "Perez", "123456789", "nuevoemail@example.com", "nuevopass");
        boolean actualizadoOk = admin.actualizarCliente(cliente1.getCedula(), actualizado);

        assertTrue(actualizadoOk, "Debería actualizar cliente");
        assertEquals("Juan Carlos", cliente1.getNombre());
        assertEquals("Perez", cliente1.getApellido());
        // Nota: no actualizamos correo y contraseña en actualizarCliente, podrías agregar si quieres
    }

    @Test
    public void testDepositarYRetirar() {
        cuenta1.depositar(200);
        assertEquals(1200, cuenta1.getSaldo(), 0.001);

        boolean retiroOk = cuenta1.retirar(300);
        assertTrue(retiroOk);
        assertEquals(900, cuenta1.getSaldo(), 0.001);

        // Intentar retirar más que saldo
        boolean retiroFail = cuenta1.retirar(1000);
        assertFalse(retiroFail);
        assertEquals(900, cuenta1.getSaldo(), 0.001);
    }

    @Test
    public void testRealizarTransaccion() {
        boolean exito = gestor.realizarTransaccion(cuenta1, cuenta2, 400);
        assertTrue(exito, "Transacción debería ser exitosa");
        assertEquals(600, cuenta1.getSaldo(), 0.001);
        assertEquals(900, cuenta2.getSaldo(), 0.001);
        assertEquals(1, gestor.getHistorialTransacciones().size());

        // Transacción con monto mayor que saldo
        boolean fallo = gestor.realizarTransaccion(cuenta1, cuenta2, 1000);
        assertFalse(fallo, "Transacción debería fallar por saldo insuficiente");
        assertEquals(600, cuenta1.getSaldo(), 0.001);
        assertEquals(900, cuenta2.getSaldo(), 0.001);
        assertEquals(1, gestor.getHistorialTransacciones().size());
    }

}
