
package edu.ulatina.model;

import java.io.Serializable;
import java.sql.Date;

public class OrdenTO implements Serializable {
    
    private int idorden;
    private int idusuario;
    private Date fecha;
    private String domicilio;

    public OrdenTO() {
        
    }

    public OrdenTO(int idorden, int idusuario, Date fecha, String domicilio) {
        this.idorden = idorden;
        this.idusuario = idusuario;
        this.fecha = fecha;
        this.domicilio = domicilio;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public int getIdorden() {
        return idorden;
    }

    public void setIdorden(int idorden) {
        this.idorden = idorden;
    }
    
    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

}
