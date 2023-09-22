
package edu.ulatina.controller;

import edu.ulatina.controller.ControladorLogin;
import edu.ulatina.model.UsuarioTO;
import edu.ulatina.servicio.ServicioUsuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "controladorCliente")
@SessionScoped

public class ControladorCliente implements Serializable {

    private UsuarioTO usuarioTO = new UsuarioTO();
    @ManagedProperty("#{controladorLogin}")
    private ControladorLogin controladorLogin;
    

    public ControladorCliente() {
    }
    
    public void guardarCliente() {
        try {
            ServicioUsuario servicioUsuario = new ServicioUsuario();
            this.usuarioTO.setUsuario(controladorLogin.getUsuarioTO());
            servicioUsuario.insertar(this.usuarioTO);
            this.usuarioTO = new UsuarioTO();
            controladorLogin.ingresarLogin();

            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido agregado satisfactoriamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "La información no ha sido procesada correctamente. "));
        }
    }
    
    
    public void actualizarCliente(){
        try{
            this.setUsuarioTO(controladorLogin.getClienteTO());
            ServicioUsuario servicioCliente = new ServicioUsuario();
            servicioCliente.actualizar(this.usuarioTO);
            this.usuarioTO = new UsuarioTO();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido agregado satisfactoriamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "La información no ha sido procesada correctamente. "));
        }
    }
    
    public void eliminarCliente() {
        try {
           ServicioUsuario servicioUsuario = new ServicioUsuario();
            this.setUsuarioTO(controladorLogin.getClienteTO());
            servicioUsuario.eliminarC(this.controladorLogin.getClienteTO());
            controladorLogin.cerrarSesion();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "Su cuenta ha sido eliminada satisfactoriamente"));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "La información no ha sido procesada correctamente. "));
        }
    }

    public UsuarioTO getUsuarioTO() {
        return usuarioTO;
    }

    public void setUsuarioTO(UsuarioTO clienteTO) {
        this.usuarioTO = clienteTO;
    }

    public ControladorLogin getControladorLogin() {
        return controladorLogin;
    }

    public void setControladorLogin(ControladorLogin controladorLogin) {
        this.controladorLogin = controladorLogin;
    }
   
}
