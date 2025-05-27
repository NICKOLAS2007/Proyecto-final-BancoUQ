package org.uniquindio.edu.co.poo.bancouqjfx.model;

import static org.junit.jupiter.api.Assertions.*;





import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

    class AdministradorTest {

        private Administrador admin;
        private Cliente cliente1;
        private Cliente cliente2;

        @BeforeEach
        void setUp() {
            admin = new Administrador("Ana", "Gonzalez", "9999", new LinkedList<>(), "ana@admin.com", "admin123");
            cliente1 = new Cliente("Carlos", "Ramirez", "1234");
            cliente2 = new Cliente("Maria", "Lopez", "5678");
        }

        @Test
        void testAgregarClienteNuevo() {
            boolean resultado = admin.agregarCliente(cliente1);
            assertTrue(resultado, "Debe agregar un cliente nuevo");
            assertTrue(admin.verificarCliente("1234"), "Debe verificar que el cliente fue agregado");
        }

        @Test
        void testAgregarClienteExistente() {
            admin.agregarCliente(cliente1);
            boolean resultado = admin.agregarCliente(cliente1);
            assertFalse(resultado, "No debe agregar un cliente ya existente");
        }

        @Test
        void testEliminarClienteExistente() {
            admin.agregarCliente(cliente1);
            boolean resultado = admin.eliminarCliente("1234");
            assertTrue(resultado, "Debe eliminar el cliente existente");
            assertFalse(admin.verificarCliente("1234"), "No debe encontrar al cliente eliminado");
        }

        @Test
        void testEliminarClienteInexistente() {
            boolean resultado = admin.eliminarCliente("0000");
            assertFalse(resultado, "No debe eliminar un cliente inexistente");
        }

        @Test
        void testActualizarClienteExistente() {
            admin.agregarCliente(cliente1);
            Cliente actualizado = new Cliente("Carlos", "Morales", "9999");
            boolean resultado = admin.actualizarCliente("1234", actualizado);
            assertTrue(resultado, "Debe actualizar el cliente existente");
            assertTrue(admin.verificarCliente("9999"), "Debe encontrar al cliente con la nueva cédula");
            assertFalse(admin.verificarCliente("1234"), "No debe encontrar la cédula antigua");
        }

        @Test
        void testActualizarClienteInexistente() {
            Cliente actualizado = new Cliente("Pepe", "Martinez", "7777");
            boolean resultado = admin.actualizarCliente("0000", actualizado);
            assertFalse(resultado, "No debe actualizar un cliente inexistente");
        }

        @Test
        void testVerificarClienteExistente() {
            admin.agregarCliente(cliente2);
            assertTrue(admin.verificarCliente("5678"), "Debe verificar correctamente un cliente existente");
        }

        @Test
        void testVerificarClienteInexistente() {
            assertFalse(admin.verificarCliente("0000"), "No debe encontrar un cliente inexistente");
        }

        @Test
        void testGetCorreoYContraseña() {
            assertEquals("ana@admin.com", admin.getCorreo(), "El correo debe ser el mismo que se configuró");
            assertEquals("admin123", admin.getContraseña(), "La contraseña debe ser la misma que se configuró");
        }

        @Test
        void testSetCorreoYContraseña() {
            admin.setCorreo("nuevo@admin.com");
            admin.setContraseña("nueva123");
            assertEquals("nuevo@admin.com", admin.getCorreo(), "El correo debe haber sido actualizado");
            assertEquals("nueva123", admin.getContraseña(), "La contraseña debe haber sido actualizada");
        }
    }


