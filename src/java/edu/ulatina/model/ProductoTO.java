
package edu.ulatina.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProductoTO implements Serializable {
    private int idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private String fotoProducto;
    private int gananciaProducto;
    private double precioProducto;
    private String estadoProducto;
    private int meGustaProducto;
    private DeseoTO deseo;
    private List<AccesorioTO> listaAccesorios = new ArrayList<AccesorioTO>();
    private List<DeseoTO> listaDeseoTO = new ArrayList<DeseoTO>();
    
    public ProductoTO() {
    }

    public ProductoTO(int idProducto, String nombreProducto, String descripcionProducto, String fotoProducto, int gananciaProducto, double precioProducto, String estadoProducto, int meGustaProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.fotoProducto = fotoProducto;
        this.gananciaProducto = gananciaProducto;
        this.precioProducto = precioProducto;
        this.estadoProducto = estadoProducto;
        this.meGustaProducto = meGustaProducto;
    }

    public ProductoTO(int idProducto, String nombreProducto, String descripcionProducto, int gananciaProducto, double precioProducto, String estadoProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.gananciaProducto = gananciaProducto;
        this.precioProducto = precioProducto;
        this.estadoProducto = estadoProducto;
    }

    public ProductoTO(int idProducto, String nombreProducto, String descripcionProducto, double precioProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
    }

    public ProductoTO(String nombreProducto, String descripcionProducto, double precioProducto) {
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
    }
    
     public ProductoTO(int idProducto, String nombreProducto, String descripcionProducto, String fotoProducto, int gananciaProducto, double precioProducto, String estadoProducto, int meGustaProducto, DeseoTO deseo) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.fotoProducto = fotoProducto;
        this.gananciaProducto = gananciaProducto;
        this.precioProducto = precioProducto;
        this.estadoProducto = estadoProducto;
        this.meGustaProducto = meGustaProducto;
        this.deseo = deseo;
    }

    public DeseoTO getDeseo() {
        return deseo;
    }

    public void setDeseo(DeseoTO deseo) {
        this.deseo = deseo;
    }

    public List<DeseoTO> getListaDeseoTO() {
        return listaDeseoTO;
    }

    public void setListaDeseoTO(List<DeseoTO> listaDeseoTO) {
        this.listaDeseoTO = listaDeseoTO;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(String fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    public int getMeGustaProducto() {
        return meGustaProducto;
    }

    public void setMeGustaProducto(int meGustaProducto) {
        this.meGustaProducto = meGustaProducto;
    }

    public int getGananciaProducto() {
        return gananciaProducto;
    }

    public void setGananciaProducto(int gananciaProducto) {
        this.gananciaProducto = gananciaProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(String estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public List<AccesorioTO> getListaAccesorios() {
        return listaAccesorios;
    }

    public void setListaAccesorios(List<AccesorioTO> listaAccesorios) {
        this.listaAccesorios = listaAccesorios;
    }
    
}