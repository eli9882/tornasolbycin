
package edu.ulatina.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Carrito implements Serializable {
    private int idCarrito;
    private int idUsuario;
    private int IdEstado;
    private Timestamp FechaCreacion;
    private UsuarioTO usuarioTO;
    private List<CarritoDetalleTO> ListaDetalles = new ArrayList<CarritoDetalleTO>();

    
    public Carrito() {
    }

    public Carrito(int idCarrito, int idUsuario, int IdEstado, Timestamp FechaCreacion) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.IdEstado = IdEstado;
        this.FechaCreacion = FechaCreacion;
    }

    public Carrito(int idCarrito, int idUsuario, int IdEstado, Timestamp FechaCreacion, UsuarioTO clienteTO) {
        this.idCarrito = idCarrito;
        this.idUsuario = idUsuario;
        this.IdEstado = IdEstado;
        this.FechaCreacion = FechaCreacion;
        this.usuarioTO = clienteTO;
    }
    
    public Carrito(int idCarrito) {
        this.idCarrito = idCarrito;
     
    }

    public Carrito(Timestamp FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public Timestamp getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Timestamp FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    public UsuarioTO getUsuarioTO() {
        return usuarioTO;
    }

    public void setUsuarioTO(UsuarioTO clienteTO) {
        this.usuarioTO = clienteTO;
    }

    public List<CarritoDetalleTO> getListaDetalles() {
        return ListaDetalles;
    }

    public void setListaDetalles(List<CarritoDetalleTO> ListaDetalles) {
        this.ListaDetalles = ListaDetalles;
    }
    
    public float ObtenerTotal() {
        
        float total = 0;
        
         for (CarritoDetalleTO carritoDetalle : getListaDetalles()) {
           total += carritoDetalle.getSubtotal();
        }
         
         return total;
    }

}
