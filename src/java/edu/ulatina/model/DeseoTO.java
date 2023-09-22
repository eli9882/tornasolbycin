
package edu.ulatina.model;

import java.io.Serializable;

public class DeseoTO implements Serializable {
    private int idDeseo;
    private int idUsuario;
    private ProductoTO producto;
    private int Cantidad;
    private double Subtotal;
    private UsuarioTO usuarioTO;

    public DeseoTO() {
    }


    public DeseoTO(int Cantidad, ProductoTO producto) {
        this.producto = producto;
        this.Cantidad = Cantidad;
    }

    public DeseoTO(int idDeseo) {
        this.idDeseo = idDeseo;
    }

    public DeseoTO(double Subtotal) {
        this.Subtotal = Subtotal;
    }

    public int getIdDeseo() {
        return idDeseo;
    }

    public void setIdDeseo(int idDeseo) {
        this.idDeseo = idDeseo;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(double Subtotal) {
        this.Subtotal = Subtotal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ProductoTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoTO producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public UsuarioTO getUsuarioTO() {
        return usuarioTO;
    }

    public void setUsuarioTO(UsuarioTO usuarioTO) {
        this.usuarioTO = usuarioTO;
    }
}
    
    

