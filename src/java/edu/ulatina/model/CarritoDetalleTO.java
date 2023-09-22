package edu.ulatina.model;

import java.io.Serializable;

public class CarritoDetalleTO implements Serializable {

    private int idCarrito;
    private int cantidad;
    private double subtotal;
    private ProductoTO producto;
    private DeseoTO deseo;
    private Carrito carrito;
    private UsuarioTO usuarioTO;

    public CarritoDetalleTO() {
    }

    public CarritoDetalleTO(int idCarrito, int cantidad) {
        this.idCarrito = idCarrito;
        this.cantidad = cantidad;
    }

    public CarritoDetalleTO(int cantidad, ProductoTO producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public CarritoDetalleTO(double subtotal) {
        this.subtotal = subtotal;
    }

    public CarritoDetalleTO(int cantidad, ProductoTO producto, Carrito carrito) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.carrito = carrito;
    }

    public CarritoDetalleTO(int idCarrito, int cantidad, double subtotal, ProductoTO producto, DeseoTO deseo, Carrito carrito) {
        this.idCarrito = idCarrito;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
        this.deseo = deseo;
        this.carrito = carrito;
    }

    public DeseoTO getDeseo() {
        return deseo;
    }

    public void setDeseo(DeseoTO deseo) {
        this.deseo = deseo;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public ProductoTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoTO producto) {
        this.producto = producto;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public UsuarioTO getUsuarioTO() {
        return usuarioTO;
    }

    public void setUsuarioTO(UsuarioTO usuarioTO) {
        this.usuarioTO = usuarioTO;
    }
    
    

}
