/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm401.model;

/**
 *
 * @author johan
 */
public class Cliente {
    private int idCliente;
    private String email;
    private String fechaRegistro;
    private int estatus;
    private Persona persona;

    public Cliente() {
    }

    public Cliente(String email, String fechaRegistro, int estatus, Persona persona) {
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.estatus = estatus;
        this.persona = persona;
    }

    public Cliente(int idCliente, String email, String fechaRegistro, int estatus, Persona persona) {
        this.idCliente = idCliente;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.estatus = estatus;
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setIdPersona(Persona persona) {
        this.persona = persona;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{");
        sb.append("idCliente=").append(idCliente);
        sb.append(", email=").append(email);
        sb.append(", fechaRegistro=").append(fechaRegistro);
        sb.append(", estatus=").append(estatus);
        sb.append(", Persona=").append(persona);
        sb.append('}');
        return sb.toString();
    }
    
  
}
