
package edu.ulatina.model;

import java.io.Serializable;


public class OrdenDetalleTO implements Serializable{
    
    private int idorden;
    private int idproducto;
    private double preciototal;

    public OrdenDetalleTO() {
    }

    public OrdenDetalleTO(int idorden, int idproducto, double preciototal) {
        this.idorden = idorden;
        this.idproducto = idproducto;
        this.preciototal = preciototal;
    }
    
    public int getIdorden() {
        return idorden;
    }

    public void setIdorden(int idorden) {
        this.idorden = idorden;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public double getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(double preciototal) {
        this.preciototal = preciototal;
    }
}
