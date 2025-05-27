package org.uniquindio.edu.co.poo.bancouqjfx.model;

public class Cliente extends Persona {
    private String correo;
    private String contraseña;

    public Cliente(String nombre, String apellido, String cedula, String correo, String contraseña) {
        super(nombre, apellido, cedula);
        this.correo = correo;
        this.contraseña = contraseña;
    }

    // Constructor alternativo para vistas sin correo y contraseña
    public Cliente(String nombre, String apellido, String cedula) {
        super(nombre, apellido, cedula);
        this.correo = "";
        this.contraseña = "";
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
