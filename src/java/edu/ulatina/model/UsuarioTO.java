
package edu.ulatina.model;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioTO implements Serializable {

    private int idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correoUsuario;
    private String claveUsuario;
    private String telefonoUsuario;
    private int permisosUsuario;
    private int estadoUsuario;
    private String direccionUsuario;
    private UsuarioTO usuario; 
  

    public UsuarioTO() {
    }
    
    public UsuarioTO(String nombreUsuario, String apellidoUsuario, String telefonoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.telefonoUsuario = telefonoUsuario;
    }

    public UsuarioTO(int idUsuario, String nombreUsuario, String apellidoUsuario, String correoUsuario, String claveUsuario, String telefonoUsuario, int permisosUsuario, int estadoUsuario, String direccionUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.correoUsuario = correoUsuario;
        this.claveUsuario = claveUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.permisosUsuario = permisosUsuario;
        this.estadoUsuario = estadoUsuario;
        this.direccionUsuario = direccionUsuario;
    }

    public UsuarioTO(int idUsuario, String nombreUsuario, String apellidoUsuario, String correoUsuario, String claveUsuario, String telefonoUsuario, String direccionUsuario, int estadoUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.correoUsuario = correoUsuario;
        this.claveUsuario = claveUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.direccionUsuario = direccionUsuario;
        this.estadoUsuario = estadoUsuario;
    } 

    public UsuarioTO(String correoUsuario, String claveUsuario) {
        this.correoUsuario = correoUsuario;
        this.claveUsuario = claveUsuario;
    }

    public UsuarioTO(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public UsuarioTO(String correo, String clave, int estado) {
        this.correoUsuario = correo;
        this.claveUsuario = clave;
        this.estadoUsuario = estado;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public UsuarioTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioTO usuario) {
        this.usuario = usuario;
    }
    
    

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public int getPermisosUsuario() {
        return permisosUsuario;
    }

    public void setPermisosUsuario(int permisosUsuario) {
        this.permisosUsuario = permisosUsuario;
    }

    public int getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(int estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public String getNombreCompleto() {
        return this.getNombreUsuario() + " " + this.getApellidoUsuario();
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioTO other = (UsuarioTO) obj;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (!Objects.equals(this.nombreUsuario, other.nombreUsuario)) {
            return false;
        }
        if (!Objects.equals(this.apellidoUsuario, other.apellidoUsuario)) {
            return false;
        }
        return Objects.equals(this.idUsuario, other.idUsuario);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.idUsuario;
        hash = 29 * hash + Objects.hashCode(this.nombreUsuario);
        hash = 29 * hash + Objects.hashCode(this.apellidoUsuario);
        hash = 29 * hash + Objects.hashCode(this.idUsuario);
        return hash;
    }
}
