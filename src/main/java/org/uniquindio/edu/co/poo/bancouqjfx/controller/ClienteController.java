package org.uniquindio.edu.co.poo.bancouqjfx.controller;

import  org.uniquindio.edu.co.poo.bancouqjfx.model.Cliente;
import  org.uniquindio.edu.co.poo.bancouqjfx.model.Banco;
import java.util.Collection;

public class ClienteController {

    Banco banco;

    public ClienteController(Banco banco) {
        this.banco = banco;
    }

    public boolean crearCliente(Cliente cliente) {
        return banco.agregarCliente(cliente);
    }

    public Collection<Cliente> obtenerListaClientes() {
        return banco.getClientes();
    }

    public boolean eliminarCliente(String cedula) {
        return banco.eliminarCliente(cedula);
    }

    public boolean actualizarCliente(String cedula, Cliente cliente) {
        return banco.actualizarCliente(cedula, cliente);
    }
}