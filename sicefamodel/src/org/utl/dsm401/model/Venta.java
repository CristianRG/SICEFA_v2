/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm401.model;

/**
 *
 * @author Pc
 */
public class Venta {
    private int idVenta;
    private String nombre;
    private int cantidad;
    private double precio;
    private String empleado;
    private String cliente;
    private String fecha;
    private int status;
    private double total;

    public Venta(int idVenta, String nombre, int cantidad, double precio, String empleado, String cliente, String fecha, int status, double total) {
        this.idVenta = idVenta;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.empleado = empleado;
        this.cliente = cliente;
        this.fecha = fecha;
        this.status = status;
        this.total = total;
    }

    public Venta(String nombre, int cantidad, double precio, String empleado, String cliente, String fecha, int status, double total) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.empleado = empleado;
        this.cliente = cliente;
        this.fecha = fecha;
        this.status = status;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    
}
