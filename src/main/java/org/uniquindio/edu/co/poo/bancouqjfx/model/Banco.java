
package org.uniquindio.edu.co.poo.bancouqjfx.model;

import java.util.*;
import org.uniquindio.edu.co.poo.bancouqjfx.model.GestorTransacciones;
import org.uniquindio.edu.co.poo.bancouqjfx.model.GestorTransacciones.Transaccion;

public class Banco {
    public String nombre;
    public Collection<Cliente> clientes;
    public Collection<Administrador> administradores;
    public List<Transaccion> historialTransacciones;
    public Collection<CuentaBancaria> listaCuentas;

    public Banco(String nombre) {
        this.nombre = nombre;
        clientes = new LinkedList<>();
        administradores = new LinkedList<>();
        historialTransacciones = new ArrayList<>();
        listaCuentas = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Collection<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean agregarCliente(Cliente cliente) {
        if (!verificarCliente(cliente.getCedula())) {
            clientes.add(cliente);
            return true;
        }
        return false;
    }

    public boolean eliminarCliente(String cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                clientes.remove(cliente);
                return true;
            }
        }
        return false;
    }

    public boolean actualizarCliente(String cedula, Cliente actualizado) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                cliente.setCedula(actualizado.getCedula());
                cliente.setNombre(actualizado.getNombre());
                cliente.setApellido(actualizado.getApellido());
                // Si quieres actualizar correo y contraseña:
                cliente.setCorreo(actualizado.getCorreo());
                cliente.setContraseña(actualizado.getContraseña());
                return true;
            }
        }
        return false;
    }
    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        for (CuentaBancaria cuenta : listaCuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null;
    }

    public boolean verificarCliente(String cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    // Nuevo método para buscar cliente por correo
    public Cliente buscarClientePorCorreo(String correo) {
        if (correo == null) return null;
        for (Cliente cliente : clientes) {
            if (correo.equalsIgnoreCase(cliente.getCorreo())) {
                return cliente;
            }
        }
        return null;
    }

    public boolean agregarAdmin(Administrador administrador) {
        if (!verificarAdmin(administrador.getCorreo())) {
            administradores.add(administrador);
            return true;
        }
        return false;
    }


    public boolean verificarAdmin(String correo) {
        for (Administrador administrador : administradores) {
            if (administrador.getCorreo().equals(correo)) {
                return true;
            }
        }
        return false;
    }

    // Nuevo método para buscar admin por correo
    public Administrador buscarAdminPorCorreo(String correo) {
        if (correo == null) return null;
        for (Administrador administrador : administradores) {
            if (correo.equalsIgnoreCase(administrador.getCorreo())) {
                return administrador;
            }
        }
        return null;
    }
    // Métodos para cuentas bancarias
    public Collection<CuentaBancaria> getListaCuentas() {
        return listaCuentas;
    }

    public boolean agregarCuenta(CuentaBancaria cuenta) {
        if (!verificarcuenta(cuenta.getNumeroCuenta())) {
            listaCuentas.add(cuenta);
            return true;
        }
        return false;
    }

    public boolean eliminarCuentas(String numeroCuenta) {
        for (CuentaBancaria cuentaBancaria : listaCuentas) {
            if (cuentaBancaria.getNumeroCuenta().equals(numeroCuenta)) {
                listaCuentas.remove(cuentaBancaria);
                return true;
            }
        }
        return false;
    }

    public boolean actualizarCuentas(String numeroCuenta, CuentaBancaria actualizada) {
        for (CuentaBancaria cuentaBancaria : listaCuentas) {
            if (cuentaBancaria.getNumeroCuenta().equals(numeroCuenta)) {
                cuentaBancaria.setNumeroCuenta(actualizada.getNumeroCuenta());
                cuentaBancaria.setSaldo(actualizada.getSaldo());
                return true;
            }
        }
        return false;
    }

    public boolean verificarcuenta(String numeroCuenta) {
        for (CuentaBancaria cuentaBancaria : listaCuentas) {
            if (cuentaBancaria.getNumeroCuenta().equals(numeroCuenta)) {
                return true;
            }
        }
        return false;
    }

    // Métodos para transacciones
    public boolean agregarTransaccion(Transaccion nuevaTransaccion) {
        if (!verificarTransaccion(nuevaTransaccion.getCuentaOrigen(), nuevaTransaccion.getCuentaDestino())) {
            historialTransacciones.add(nuevaTransaccion);
            return true;
        }
        return false;
    }

    public boolean eliminarTransaccion(String cuentaOrigen, String cuentaDestino) {
        for (Transaccion transaccion : historialTransacciones) {
            if (transaccion.getCuentaOrigen().equals(cuentaOrigen) &&
                    transaccion.getCuentaDestino().equals(cuentaDestino)) {
                historialTransacciones.remove(transaccion);
                return true;
            }
        }
        return false;
    }

    public boolean actualizarTransaccion(String cuentaOrigen, String cuentaDestino, Transaccion actualizada) {
        for (Transaccion transaccion : historialTransacciones) {
            if (transaccion.getCuentaOrigen().equals(cuentaOrigen) &&
                    transaccion.getCuentaDestino().equals(cuentaDestino)) {
                transaccion.setCuentaOrigen(actualizada.getCuentaOrigen());
                transaccion.setCuentaDestino(actualizada.getCuentaDestino());
                transaccion.setMonto(actualizada.getMonto());
                transaccion.setFechaHora(actualizada.getFechaHora());
                return true;
            }
        }
        return false;
    }

    public boolean verificarTransaccion(String cuentaOrigen, String cuentaDestino) {
        for (Transaccion transaccion : historialTransacciones) {
            if (transaccion.getCuentaOrigen().equals(cuentaOrigen) &&
                    transaccion.getCuentaDestino().equals(cuentaDestino)) {
                return true;
            }
        }
        return false;
    }
}
