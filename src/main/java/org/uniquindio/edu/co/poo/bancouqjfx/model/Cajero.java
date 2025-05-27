package org.uniquindio.edu.co.poo.bancouqjfx.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Cajero extends Persona {

    private Collection<Cliente> clientes;

    public Cajero(String nombre, String apellido, String cedula, Collection<Cliente> clientes) {
        super(nombre, apellido, cedula);
        this.clientes = clientes != null ? clientes : new LinkedList<>();
    }

    public boolean agregarCliente(Cliente cliente) {
        if (!verificarCliente(cliente.getCedula())) {
            clientes.add(cliente);
            return true;
        }
        return false;
    }

    public boolean eliminarCliente(String cedula) {
        return clientes.removeIf(cliente -> cliente.getCedula().equals(cedula));
    }

    public boolean actualizarCliente(String cedula, Cliente actualizado) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                if (!cliente.getCedula().equals(actualizado.getCedula()) && verificarCliente(actualizado.getCedula())) {
                    return false; // Nueva cédula ya existe
                }
                cliente.setCedula(actualizado.getCedula());
                cliente.setNombre(actualizado.getNombre());
                cliente.setApellido(actualizado.getApellido());
                return true;
            }
        }
        return false;
    }

    public boolean verificarCliente(String cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }
}
