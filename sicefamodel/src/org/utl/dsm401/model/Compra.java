package org.utl.dsm401.model;


/**
 *
 * @author Pc
 */
public class Compra {
    private int idCompra;
    private String fechaPedido;
    private int idEmpleado;
    private int idSucursal;
    private String sucursal;
    private int estatus;
    private int idProducto;
    private String producto;
    private int cantidad;
    private double precioCompra;
    private double total;

    public Compra(int idCompra, String fechaPedido, int idEmpleado, int idSucursal, String sucursal, int estatus, int idProducto, String producto, int cantidad, double precioCompra, double total) {
        this.idCompra = idCompra;
        this.fechaPedido = fechaPedido;
        this.idEmpleado = idEmpleado;
        this.idSucursal = idSucursal;
        this.sucursal = sucursal;
        this.estatus = estatus;
        this.idProducto = idProducto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.total = total;
    }

    public Compra(String fechaPedido, int idEmpleado, int idSucursal, String sucursal, int estatus, int idProducto, String producto, int cantidad, double precioCompra, double total) {
        this.fechaPedido = fechaPedido;
        this.idEmpleado = idEmpleado;
        this.idSucursal = idSucursal;
        this.sucursal = sucursal;
        this.estatus = estatus;
        this.idProducto = idProducto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.total = total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }
    
    
}
