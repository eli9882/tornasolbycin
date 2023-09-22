
package edu.ulatina.model;

import java.io.Serializable;

public class AccesorioTO implements Serializable{
    private int idAccesorio;
    private ProductoTO productoTO;
    private String nombreAccesorio;
    private int cantidadAccesorio;
    private double precioUAccesorio;

    public AccesorioTO() {
    }

    public AccesorioTO(int idAccesorio, ProductoTO productoTO, String nombreAccesorio, int cantidadAccesorio, double precioUAccesorio) {
        this.idAccesorio = idAccesorio;
        this.productoTO = productoTO;
        this.nombreAccesorio = nombreAccesorio;
        this.cantidadAccesorio = cantidadAccesorio;
        this.precioUAccesorio = precioUAccesorio;
    }
    
    public AccesorioTO(String nombreAccesorio) {
        this.nombreAccesorio = nombreAccesorio;
    }

    public int getIdAccesorio() {
        return idAccesorio;
    }

    public void setIdAccesorio(int idAccesorio) {
        this.idAccesorio = idAccesorio;
    }

    public ProductoTO getProductoTO() {
        return productoTO;
    }

    public void setProductoTO(ProductoTO productoTO) {
        this.productoTO = productoTO;
    }
    
    public String getNombreAccesorio() {
        return nombreAccesorio;
    }

    public void setNombreAccesorio(String nombreAccesorio) {
        this.nombreAccesorio = nombreAccesorio;
    }

    public int getCantidadAccesorio() {
        return cantidadAccesorio;
    }

    public void setCantidadAccesorio(int cantidadAccesorio) {
        this.cantidadAccesorio = cantidadAccesorio;
    }

    public double getPrecioUAccesorio() {
        return precioUAccesorio;
    }

    public void setPrecioUAccesorio(double precioUAccesorio) {
        this.precioUAccesorio = precioUAccesorio;
    }
    
}
