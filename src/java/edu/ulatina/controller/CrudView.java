package edu.ulatina.controller;

import edu.ulatina.model.Carrito;
import edu.ulatina.model.CarritoDetalleTO;
import edu.ulatina.model.DeseoDetalleTO;
import edu.ulatina.model.DeseoTO;
import edu.ulatina.model.ProductoTO;
import edu.ulatina.model.UsuarioTO;
import edu.ulatina.servicio.ServicioCarrito;
import edu.ulatina.servicio.ServicioDeseo;
import edu.ulatina.servicio.ServicioProducto;
import edu.ulatina.servicio.ServicioUsuario;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "crudView")
@SessionScoped

public class CrudView implements Serializable {

    private ProductoTO selectedProducto = new ProductoTO();
    private CarritoDetalleTO selectedCarrito = new CarritoDetalleTO();
    private DeseoTO selectedDeseo = new DeseoTO();
    private UsuarioTO selectedClienteTO = new UsuarioTO();
    private Carrito carrito;
    private int ESTADO_CARRITO_PENDIENTE = 1;
    private List<CarritoDetalleTO> listaCarritoDetalleTO = new ArrayList<CarritoDetalleTO>();
    private List<ProductoTO> listapt = new ArrayList<ProductoTO>();
  
    private List<DeseoTO> listaDeseoTO = new ArrayList<DeseoTO>();
    
    private double granSubtotal;

    private double totalpagar = 0.0;
    private UsuarioTO usuarioTO = null;
    private DeseoTO deseo;
    
    @ManagedProperty("#{controladorLogin}")
    private ControladorLogin controladorLogin;

    public CrudView() {

    }

    @PostConstruct
    public void cargarListaDeseos() {
        ServicioDeseo servicioDeseo = new ServicioDeseo();
        // this.ListaDeseoTO = servicioDeseo.listaDeseo();  
        if (controladorLogin != null && controladorLogin.getUsuarioTO() != null) {
            this.listaDeseoTO = servicioDeseo.listaDeseoTO(controladorLogin.getUsuarioTO().getIdUsuario());
        }
    }

    public void ingresarListaDeseo() {
        this.cargarListaDeseos();
        controladorLogin.redireccionar("/faces/tabListaDeseo.xhtml");
    }

    public ControladorLogin getControladorLogin() {
        return controladorLogin;
    }

    public void setControladorLogin(ControladorLogin controladorLogin) {
        this.controladorLogin = controladorLogin;
    }

    public UsuarioTO getClienteTO() {
        return selectedClienteTO;
    }

    public UsuarioTO getSelectedClienteTO() {
        return selectedClienteTO;
    }

    public ProductoTO getSelectedProducto() {
        return selectedProducto;
    }

    public void setSelectedClienteTO(UsuarioTO selectedClienteTO) {
        this.selectedClienteTO = selectedClienteTO;
    }

    public void setSelectedProducto(ProductoTO selectedProducto) {
        this.selectedProducto = selectedProducto;
    }

    public List<DeseoTO> getListaDeseoTO() {
        return listaDeseoTO;
    }

    public void setListaDeseoTO(List<DeseoTO> listaDeseoTO) {
        this.listaDeseoTO = listaDeseoTO;
    }

    public DeseoTO getDeseo() {
        return deseo;
    }

    public void setDeseo(DeseoTO deseo) {
        this.deseo = deseo;
    }

    public CarritoDetalleTO getSelectedCarrito() {
        return selectedCarrito;
    }

    public void setSelectedCarrito(CarritoDetalleTO selectedCarrito) {
        this.selectedCarrito = selectedCarrito;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public int getESTADO_CARRITO_PENDIENTE() {
        return ESTADO_CARRITO_PENDIENTE;
    }

    public void setESTADO_CARRITO_PENDIENTE(int ESTADO_CARRITO_PENDIENTE) {
        this.ESTADO_CARRITO_PENDIENTE = ESTADO_CARRITO_PENDIENTE;
    }

    public List<CarritoDetalleTO> getListaCarritoDetalleTO() {
        return listaCarritoDetalleTO;
    }

    public void setListaCarritoDetalleTO(List<CarritoDetalleTO> listaCarritoDetalleTO) {
        this.listaCarritoDetalleTO = listaCarritoDetalleTO;
    }

    public List<ProductoTO> getListapt() {
        return listapt;
    }

    public void setListapt(List<ProductoTO> listapt) {
        this.listapt = listapt;
    }

    public double getGranSubtotal() {
        return granSubtotal;
    }

    public void setGranSubtotal(double granSubtotal) {
        this.granSubtotal = granSubtotal;
    }

    public double getTotalpagar() {
        return totalpagar;
    }

    public void setTotalpagar(double totalpagar) {
        this.totalpagar = totalpagar;
    }

    public DeseoTO getSelectedDeseo() {
        return selectedDeseo;
    }

    public void setSelectedDeseo(DeseoTO selectedDeseo) {
        this.selectedDeseo = selectedDeseo;
    }

    public UsuarioTO getUsuarioTO() {
        return usuarioTO;
    }

    public void setUsuarioTO(UsuarioTO usuarioTO) {
        this.usuarioTO = usuarioTO;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

//    public void AgregarNuevoCarrito() {
//
//        ServicioCarrito servicioCarrito = new ServicioCarrito();
//        carrito = new Carrito();
//        carrito.setIdEstado(ESTADO_CARRITO_PENDIENTE);
////        Date fechaHoy = new Date(System.currentTimeMillis());
////        carrito.setFechaCreacion(fechaHoy);
//        Timestamp fechaHoy = new Timestamp(calendar.getTimeInMillis());
//        carrito.setFechaCreacion(fechaHoy);
//
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        HttpSession sesion = request.getSession(false);
//
//        int idUsuario = (int) sesion.getAttribute("idUsuario");
//        carrito.setIdUsuario(idUsuario);
//
//        int idCarrito = servicioCarrito.AgregarCarrito(carrito);
//        carrito.setIdCarrito(idCarrito);
//
//    }

    public void AgregarNuevoDeseo(ProductoTO producto) {

        ServicioDeseo servicioDeseo = new ServicioDeseo();
        deseo = new DeseoTO();

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);

        int idUsuario = (int) sesion.getAttribute("idUsuario");
        deseo.setIdUsuario(idUsuario);
        deseo.setProducto(producto);
        deseo.setCantidad(1);
        int idDeseo = servicioDeseo.AgregarDeseo(deseo);
        deseo.setIdDeseo(idDeseo);
        controladorLogin.actualizarListaCarritoDeseo();
        
        this.cargarListaDeseos();
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se Agregó a la lista deseo exitosamente", ""));
    }

    public void agregarLike(ProductoTO producto) {
        ServicioProducto servicioproducto = new ServicioProducto();
        servicioproducto.addMeGusta(producto);
        controladorLogin.actualizarListaProducto();
    }

//    public void AgregarDetalle(ProductoTO producto) {
//
//        try {
//
//            if (carrito == null) {
//                AgregarNuevoCarrito();
//            }
//
//            CarritoDetalleTO detalle = ConvertirProductoToCarritoDetalle(producto);
//
//            ServicioCarrito servicioCarrito = new ServicioCarrito();
//            servicioCarrito.AgregarDetalle(detalle);
//
//            controladorLogin.actualizarListaProducto();
//            controladorLogin.actualizarListaCarritoDetalle();
//            controladorLogin.actualizarListaCarritoDetalleSuma();
//            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacion exitosa", "Se agregado satisfactoriamente al carrito"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al Agregar al carrito", "La información no ha sido procesada correctamente. "));
//
//        }
//    }

    private CarritoDetalleTO ConvertirProductoToCarritoDetalle(ProductoTO producto) {
        CarritoDetalleTO detalle = new CarritoDetalleTO();

        detalle.setIdCarrito(carrito.getIdCarrito());
        detalle.setCantidad(1);
        detalle.setProducto(producto);
        

        return detalle;
    }
    //Visitante
    
    public ProductoTO ConvertirDeseoToProducto(DeseoTO deseoTo){
        
       ProductoTO producto = new ProductoTO();
       
       producto.setIdProducto(deseoTo.getProducto().getIdProducto());
       producto.setFotoProducto(deseoTo.getProducto().getFotoProducto());
       producto.setNombreProducto(deseoTo.getProducto().getNombreProducto());
       producto.setDescripcionProducto(deseoTo.getProducto().getDescripcionProducto());
       producto.setPrecioProducto(deseoTo.getProducto().getPrecioProducto());

        return producto;
    }

    public void AgregarDetalleNuevo(ProductoTO producto) {
        try {
            CarritoDetalleTO detalle = new CarritoDetalleTO();

            detalle.setCantidad(1);
            detalle.setProducto(producto);
          
            detalle.setSubtotal(detalle.getCantidad() * detalle.getProducto().getPrecioProducto());
            System.out.println(detalle.getSubtotal());
            this.listaCarritoDetalleTO.add(detalle);
            granSubtotal += detalle.getSubtotal();

            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Se Agrego al Carrito exitosamente", ""));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al ingresar", "La información no ha sido procesada correctamente. "));

        }
    }
    
    public void AgregarDetalleNuevoDeseo(ProductoTO producto) {
        try {
            CarritoDetalleTO detalle = new CarritoDetalleTO();
            
            detalle.setCantidad(1);
            detalle.setProducto(producto);
            this.listaCarritoDetalleTO.add(detalle);
            granSubtotal += detalle.getSubtotal();
            controladorLogin.actualizarListaCarritoDetalleSumaDeseo();
            
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacion exitosa", "El registro ha sido agregado satisfactoriamente"));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al ingresar", "La información no ha sido procesada correctamente. "));

        }
    }

//Visitante
    public void finalizarCompra() {
        try {

            if (controladorLogin.getClienteTO() != null) {
                controladorLogin.redireccionar("/faces/DetalleCompra.xhtml");
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacion exitosa", "El registro ha sido agregado satisfactoriamente"));

            } else {
                controladorLogin.setVengoFinalizar(true);
                controladorLogin.redireccionar("/faces/Login.xhtml");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al ingresar", "La información no ha sido procesada correctamente. "));

            e.printStackTrace();
        }
    }
    
//Visitante
    Calendar calendar = Calendar.getInstance();

    public void pagar() {
        try {
            ServicioCarrito servicioCarrito = new ServicioCarrito();
            Carrito carrito = new Carrito();
            
            carrito.setIdEstado(ESTADO_CARRITO_PENDIENTE);
            Timestamp fechaHoy = new Timestamp(calendar.getTimeInMillis());
            carrito.setFechaCreacion(fechaHoy);

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession sesion = request.getSession(false);
            int idUsuario = (int) sesion.getAttribute("idUsuario");

            carrito.setIdUsuario(idUsuario);
            carrito.setListaDetalles(this.listaCarritoDetalleTO);
            int idCarritoAgregado = servicioCarrito.AgregarCarritoVisitante(carrito);
            carrito.setIdCarrito(idCarritoAgregado);

            this.granSubtotal = 0.0;
            controladorLogin.pruebaCorreo(carrito);
            listaCarritoDetalleTO.removeAll(this.listaCarritoDetalleTO);
            controladorLogin.redireccionar("/faces/Pago.xhtml");
            controladorLogin.actualizarListaCarritoDetalleO();
            
            

            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacion exitosa", "El registro ha sido agregado satisfactoriamente"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al ingresar", "La información no ha sido procesada correctamente. "));

            e.printStackTrace();

        }
    }
    
    public void confirmarContra(){
        
        controladorLogin.rcontrasena();
        controladorLogin.redireccionar("/faces/Login.xhtml");
    }

    public void f() {
        try {

            if (controladorLogin.getClienteTO() != null) {
                controladorLogin.redireccionar("/faces/PerfilCliente.xhtml");
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacion exitosa", "El registro ha sido agregado satisfactoriamente"));

            } else {
                //controladorLogin.setVengoFinalizar(true);
                controladorLogin.redireccionar("/faces/Login.xhtml");
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al ingresar", "La información no ha sido procesada correctamente. "));

            e.printStackTrace();
        }
    }
    
  public void seleccionarDetalleDeseo(DeseoTO detalle) {
        selectedDeseo = detalle;
    }

  public void eliminarD() {
        try {
            ServicioDeseo servicioDeseo = new ServicioDeseo();
            servicioDeseo.eliminar(this.selectedDeseo);
            controladorLogin.actualizarListaCarritoDeseo();
            controladorLogin.actualizarListaCarritoDetalleSumaDeseo();
            //System.out.println(selectedDeseo.getIdDeseo());
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido eliminado satisfactoriamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "La información no ha sido procesada correctamente. "));
        }

    }
  
    public void seleccionarDetalleCarrito(CarritoDetalleTO detalle) {
        selectedCarrito = detalle;
    }

    public void eliminarC() {

        try {
            ServicioCarrito servicioCarrito = new ServicioCarrito();
            servicioCarrito.eliminar(this.selectedCarrito);
            controladorLogin.actualizarListaCarritoDetalle();
            controladorLogin.actualizarListaCarritoDetalleSuma();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido eliminado satisfactoriamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "La información no ha sido procesada correctamente. "));
        }

    }

    public void eliminarDes() {

        try {
            ServicioDeseo servicioDeseo = new ServicioDeseo();
            servicioDeseo.eliminar(this.selectedDeseo);
            controladorLogin.actualizarListaDeseo();
            System.out.println("ELIMINARDES FUNCIONANDO");

            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido eliminado satisfactoriamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "La información no ha sido procesada correctamente. "));
        }

    }

    public void eliminarProducto() {
        this.granSubtotal = this.granSubtotal - this.selectedCarrito.getSubtotal();//ACTUALIZA EL SUBTOTAL NO BORRAR
        listaCarritoDetalleTO.remove(this.selectedCarrito);
    }

    public void eliminarCarrito() {
        listaCarritoDetalleTO.removeAll(this.listaCarritoDetalleTO);
        controladorLogin.redireccionar("/faces/index.xhtml");

    }

    public void update() {
        try {
            ServicioUsuario servicioCliente = new ServicioUsuario();
            this.setSelectedClienteTO(controladorLogin.getClienteTO());
            servicioCliente.actualizar(this.selectedClienteTO);
            this.selectedClienteTO = new UsuarioTO();
            controladorLogin.actualizarListaClientes();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "La actualización ha sido efectuada satisfactoriamente"));
        } catch (Exception e) {

        }

    }

    public void borrarCliente() {
        try {
            ServicioUsuario servicioCliente = new ServicioUsuario();
            servicioCliente.eliminar(this.selectedClienteTO);
            controladorLogin.actualizarListaClientes();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido eliminado satisfactoriamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "La información no ha sido procesada correctamente. "));
        }

    }
}
