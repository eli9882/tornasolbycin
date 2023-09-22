package edu.ulatina.model;

import java.io.Serializable;

public class DeseoDetalleTO implements Serializable {

    private int idDeseo;
    private int idUsuario;
    private ProductoTO producto;

    public DeseoDetalleTO() {
    }

    public DeseoDetalleTO(int idDeseo, int idUsuario, ProductoTO producto) {
        this.idDeseo = idDeseo;
        this.idUsuario = idUsuario;
        this.producto = producto;
    }

    public int getIdDeseo() {
        return idDeseo;
    }

    public void setIdDeseo(int idDeseo) {
        this.idDeseo = idDeseo;
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

    

    

}
