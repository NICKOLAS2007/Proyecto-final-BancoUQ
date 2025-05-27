package org.uniquindio.edu.co.poo.bancouqjfx.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CajeroTest {

    private Cajero cajero;
    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    void setUp() {
        // Se crea el Cajero sin clientes inicialmente
        cajero = new Cajero("Carlos", "Gomez", "9876", new LinkedList<>());

        // Constructor Cliente(nombre, apellido, cedula)
        cliente1 = new Cliente("Laura", "Lopez", "1111");
        cliente2 = new Cliente("Andres", "Perez", "2222");
    }

    @Test
    void testAgregarClienteNuevo() {
        boolean resultado = cajero.agregarCliente(cliente1);
        assertTrue(resultado, "Debe agregar un cliente nuevo");
        assertTrue(cajero.verificarCliente("1111"), "Debe encontrar al cliente agregado");
    }

    @Test
    void testAgregarClienteExistente() {
        cajero.agregarCliente(cliente1);
        boolean resultado = cajero.agregarCliente(cliente1);
        assertFalse(resultado, "No debe agregar un cliente ya existente");
    }

    @Test
    void testEliminarClienteExistente() {
        cajero.agregarCliente(cliente1);
        boolean resultado = cajero.eliminarCliente("1111");
        assertTrue(resultado, "Debe eliminar el cliente existente");
        assertFalse(cajero.verificarCliente("1111"), "Ya no debe encontrar al cliente eliminado");
    }

    @Test
    void testEliminarClienteInexistente() {
        boolean resultado = cajero.eliminarCliente("9999");
        assertFalse(resultado, "No debe eliminar un cliente inexistente");
    }

    @Test
    void testActualizarClienteExistente() {
        cajero.agregarCliente(cliente1);
        Cliente actualizado = new Cliente("Laura", "Ramirez", "3333");
        boolean resultado = cajero.actualizarCliente("1111", actualizado);
        assertTrue(resultado, "Debe actualizar el cliente existente");
        assertTrue(cajero.verificarCliente("3333"), "Debe encontrar el cliente con nueva cédula");
        assertFalse(cajero.verificarCliente("1111"), "Ya no debe encontrar al cliente con la cédula anterior");
    }

    @Test
    void testActualizarClienteInexistente() {
        Cliente actualizado = new Cliente("Pedro", "Martinez", "4444");
        boolean resultado = cajero.actualizarCliente("0000", actualizado);
        assertFalse(resultado, "No debe actualizar un cliente inexistente");
    }

    @Test
    void testVerificarClienteExistente() {
        cajero.agregarCliente(cliente2);
        assertTrue(cajero.verificarCliente("2222"), "Debe verificar correctamente un cliente existente");
    }

    @Test
    void testVerificarClienteInexistente() {
        assertFalse(cajero.verificarCliente("0000"), "No debe encontrar un cliente inexistente");
    }
}
