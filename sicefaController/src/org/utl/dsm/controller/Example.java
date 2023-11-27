/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.dsm.controller;

import java.util.List;
import org.utl.dsm401.model.Empleado;

/**
 *
 * @author Pc
 */
public class Example {
    public static void main(String[] args) {
        EmpleadoController empleadoController = new EmpleadoController();
        List<Empleado> empleados = empleadoController.getAll();
        System.out.println(empleados.toString());
    }
}
