package org.uniquindio.edu.co.poo.bancouqjfx.model;
import java.util.Collection;
import java.util.LinkedList;

// Codificación realizada por: Nicolás Ramirez Celis, Samuel Cruz Pabón, Nicolás Monsalve Aguirre :)


public class Administrador extends Persona {
    private Collection<Cliente> clientes;
    private String correo;
    private String contraseña;

    public Administrador(String nombre, String apellido, String cedula, Collection<Cliente> clientes, String correo, String contraseña) {
        super(nombre, apellido, cedula);
        this.clientes = (clientes != null) ? clientes : new LinkedList<>();
        this.correo = correo;
        this.contraseña = contraseña;
    }


    public boolean agregarCliente(Cliente cliente) {
        boolean centinela = false;
        if (!verificarCliente(cliente.getCedula())) {
            clientes.add(cliente);
            centinela = true;
        }
        return centinela;
    }

    public boolean eliminarCliente(String cedula) {
        boolean centinela = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                clientes.remove(cliente);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean actualizarCliente(String cedula, Cliente actualizado) {
        boolean centinela = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                cliente.setCedula(actualizado.getCedula());
                cliente.setNombre(actualizado.getNombre());
                cliente.setApellido(actualizado.getApellido());
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean verificarCliente(String cedula) {
        boolean centinela = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                centinela = true;
            }
        }
        return centinela;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}