
package edu.ulatina.controller;

import edu.ulatina.model.Carrito;
import edu.ulatina.model.CarritoDetalleTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "controladorCarrito")
@SessionScoped

public class ControladorCarrito implements Serializable {
    
    private Carrito carrito = new Carrito();
    @ManagedProperty("#{controladorLogin}")
    private ControladorLogin controladorLogin;
    private List<Carrito> listaCarrito = new ArrayList<Carrito>();
    private List<CarritoDetalleTO> listaCarritoDetalle = new ArrayList<CarritoDetalleTO>();

    public ControladorCarrito() {
    }
 public String sesion(){
      HttpServletRequest request;
           
      String nombreUsuario = "nombreUsuario";
      
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession sesion =request.getSession(false);
          
            //String idUsuario = (String)sesion.getAttribute("IdUsuario");
            nombreUsuario  = (String)sesion.getAttribute("nombreUsuario") + " - " +(int)sesion.getAttribute("idUsuario");
            
            int miIdUsuario = (int)sesion.getAttribute("idUsuario");
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            System.out.println(idUsuario);

             } catch (Exception e) {

        }
        
        return nombreUsuario;
 }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public ControladorLogin getControladorLogin() {
        return controladorLogin;
    }

    public void setControladorLogin(ControladorLogin controladorLogin) {
        this.controladorLogin = controladorLogin;
    }

    public List<Carrito> getListaCarrito() {
        return listaCarrito;
    }

    public void setListaCarrito(List<Carrito> listaCarrito) {
        this.listaCarrito = listaCarrito;
    }  
}
