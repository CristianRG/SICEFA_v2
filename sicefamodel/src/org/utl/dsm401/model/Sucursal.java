/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utl.dsm401.model;

/**
 *
 * @author Usuario
 */
public class Sucursal {
    /*
     idSucursal    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nombre        VARCHAR(49) NOT NULL,
  titular       VARCHAR(49) NOT NULL DEFAULT '',
  rfc           VARCHAR(15) NOT NULL  DEFAULT '', -- RFC del titular
  domicilio     VARCHAR(129) NOT NULL DEFAULT '',
  colonia       VARCHAR(65) NOT NULL DEFAULT '',
  codigoPostal  VARCHAR(11) NOT NULL DEFAULT '',
  ciudad        VARCHAR(65) NOT NULL DEFAULT '',
  estado        VARCHAR(49) NOT NULL DEFAULT '',
  telefono      VARCHAR(20) NOT NULL DEFAULT '',
  latitud       VARCHAR(65) NOT NULL DEFAULT '0.0',
  longitud      VARCHAR(65) NOT NULL DEFAULT '0.0',
  estatus       INT NOT NULL DEFAULT 1 -- 0: Inactivo; 1: Activo
    */
    private int idSucursal;
    private String nombre;
    private String titular;
    private String rfc;
    private String domicilio;
    private String codigoPostal;
    private String ciudad;
    private String estado;
    private String telefono;
    private String latitud;
    private String longitud;
    private int estatus;

    public Sucursal() {
    }

    public Sucursal(String nombre, String titular, String rfc, String domicilio, String codigoPostal, String ciudad, String estado, String telefono, String latitud, String longitud, int estatus) {
        this.nombre = nombre;
        this.titular = titular;
        this.rfc = rfc;
        this.domicilio = domicilio;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estatus = estatus;
    }

    public Sucursal(int idSucursal, String nombre, String titular, String rfc, String domicilio, String codigoPostal, String ciudad, String estado, String telefono, String latitud, String longitud, int estatus) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.titular = titular;
        this.rfc = rfc;
        this.domicilio = domicilio;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estatus = estatus;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
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
        sb.append("Sucursal{idSucursal:").append(idSucursal);
        sb.append(", nombre:").append(nombre);
        sb.append(", titular:").append(titular);
        sb.append(", rfc:").append(rfc);
        sb.append(", domicilio:").append(domicilio);
        sb.append(", codigoPostal:").append(codigoPostal);
        sb.append(", ciudad:").append(ciudad);
        sb.append(", estado:").append(estado);
        sb.append(", telefono:").append(telefono);
        sb.append(", latitud:").append(latitud);
        sb.append(", longitud:").append(longitud);
        sb.append(", estatus:").append(estatus);
        sb.append('}');
        return sb.toString();
    }
    
    
}
