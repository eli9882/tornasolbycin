package edu.ulatina.controller;

import edu.ulatina.Mail.ServicioEmailUtil;
import edu.ulatina.model.Carrito;
import edu.ulatina.model.CarritoDetalleTO;
import edu.ulatina.model.DeseoTO;
import edu.ulatina.model.UsuarioTO;
import edu.ulatina.model.OrdenTO;
import edu.ulatina.model.ProductoTO;
import edu.ulatina.servicio.ServicioCarrito;
import edu.ulatina.servicio.ServicioDeseo;
import edu.ulatina.servicio.ServicioUsuario;
import edu.ulatina.servicio.ServicioProducto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "controladorLogin")
@SessionScoped
public class ControladorLogin implements Serializable {

    private int estado = 2;
    private String correo;
    private String clave;
    private int idusuario;
    private String Direccion;
    private String claveNueva;
    private String claveConfirm;
    private boolean IncioSesion;
    private UsuarioTO usuarioTO = null;
    private CarritoDetalleTO cdto1 = null;
    private ProductoTO producto = null;
    
    private List<UsuarioTO> listaClientes = new ArrayList<UsuarioTO>();
    private List<ProductoTO> listaProductos = new ArrayList<ProductoTO>();
    private List<OrdenTO> listaOrdenes = new ArrayList<OrdenTO>();
    private List<Carrito> listaCarrito = new ArrayList<Carrito>();
    private CarritoDetalleTO cdto = new CarritoDetalleTO();

    private List<CarritoDetalleTO> listaCarritoDetalleO = new ArrayList<CarritoDetalleTO>();
    private List<CarritoDetalleTO> listaCarritoDetalle = new ArrayList<CarritoDetalleTO>();
    private List<CarritoDetalleTO> listaPasada = new ArrayList<CarritoDetalleTO>();
    private List<CarritoDetalleTO> listaOrden = new ArrayList<CarritoDetalleTO>();
    private List<CarritoDetalleTO> Suma = new ArrayList<CarritoDetalleTO>();
    
    private List<DeseoTO> SumaDeseo = new ArrayList<DeseoTO>();
    
    private List<UsuarioTO> listaContra = new ArrayList<UsuarioTO>();
    
    private List<DeseoTO> listaDeseo = new ArrayList<DeseoTO>();
    
    private String busqueda;
    private boolean tienePermisos;
    private boolean vengoFinalizar = false;
    private StringBuilder sb = new StringBuilder();
    
    @ManagedProperty("#{CrudView}")
    private CrudView crudview;
    
    
    
    public ControladorLogin() {
    }

    @PostConstruct
    public void inicializar() {
        this.actualizarListaProducto();
        this.setIncioSesion(false);
        this.setTienePermisos(false);
      
    }

    public void actualizarListaProducto() {
        this.listaProductos = new ServicioProducto().listaProducto(this.tienePermisos);
    }

    public void actualizarListaCarrito() {
        this.listaCarrito = new ServicioCarrito().listaCarrito();
    }

    public void actualizarListaClientes() {
        this.listaClientes = new ServicioUsuario().lista();
    }

    public void actualizarListaCarritoDetalle() { //Actualizar
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);
        this.listaCarritoDetalle = new ArrayList<CarritoDetalleTO>();
        this.listaCarritoDetalle = new ServicioCarrito().listaCarritoDetalle((int) sesion.getAttribute("idUsuario"));
    }
    
     public void actualizarListaCarrito2() { //Actualizar
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);
        this.listaCarrito = new ArrayList<Carrito>();
        this.listaCarrito = new ServicioCarrito().listaid((int) sesion.getAttribute("idUsuario"));
    }

    public void actualizarListaCarritoDetalleO() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);
        this.listaCarritoDetalleO = new ArrayList<CarritoDetalleTO>();
        if(this.tienePermisos == true){
            this.listaCarritoDetalleO = new ServicioCarrito().listaCarritoDetalleAll();
        }else{
            this.listaCarritoDetalleO = new ServicioCarrito().listaCarritoDetalleO((int) sesion.getAttribute("idUsuario"));
        }
    }

    public void actualizarListaCarritoDeseo() {//Actualizar
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);
        this.listaDeseo = new ArrayList<DeseoTO>();
        this.listaDeseo = new ServicioDeseo().listaDeseoTO((int) sesion.getAttribute("idUsuario"));
    }
    
    public void actualizarListaDeseo() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);
        this.listaDeseo = new ArrayList<DeseoTO>();
        this.listaDeseo = new ServicioDeseo().listaDeseoAct((int) sesion.getAttribute("idUsuario"));
    }
    
    public void actualizarListaCarritoDetalleSuma() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);
        this.Suma = new ServicioCarrito().Suma((int) sesion.getAttribute("idUsuario"));
    }
    
    public void actualizarListaCarritoDetalleSumaDeseo() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);
        this.SumaDeseo = new ServicioDeseo().SumaDeseo((int) sesion.getAttribute("idUsuario"));
    }
  
    public void listaProductosPasados() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesion = request.getSession(false);
        this.listaPasada = new ServicioCarrito().listaProductosPasados((int) sesion.getAttribute("idUsuario"));
    }

    public void ingresar() {
        ServicioUsuario servicioCliente = new ServicioUsuario();
        usuarioTO = new UsuarioTO(this.getCorreo(), this.getClave(), this.estado);
        usuarioTO = servicioCliente.validar(usuarioTO);
        if (usuarioTO != null) {
            
            int idUsuario = usuarioTO.getIdUsuario();
            String nombreUsuario = usuarioTO.getNombreCompleto();
            
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession sesion = request.getSession();
            
            sesion.setAttribute("idUsuario", idUsuario);
            sesion.setAttribute("nombreUsuario", nombreUsuario);
            
            this.verificarPermisos(usuarioTO);
            this.actualizarListaProducto();
            this.actualizarListaCarritoDetalle();
            this.actualizarListaCarritoDetalleSuma();
            this.actualizarListaCarritoDetalleO();
            this.actualizarListaCarritoDetalleSumaDeseo();
            this.actualizarListaCarritoDeseo();
            this.setIncioSesion(true);
            
            if (this.isVengoFinalizar() == true) {
                this.redireccionar("/faces/DetalleCompra.xhtml");
            } else {
                this.redireccionar("/faces/index.xhtml");
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "La clave o correo no son correctos o su cuenta no existe"));
        }
    }

    public void verificarPermisos(UsuarioTO clienteTO) {
        ServicioUsuario servicioCliente = new ServicioUsuario();
        this.setTienePermisos(servicioCliente.validarPermisos(clienteTO));
    }

    public void cambiarContra() {
        ServicioUsuario servicioCliente = new ServicioUsuario();
        if (this.claveNueva.equals(this.claveConfirm)) {
            servicioCliente.cambiarContra(this.usuarioTO, this.claveNueva);

            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacion Exitosa", "La contraseña ha sido actualizada"));
        } else {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "Las claves no coinciden"));
        }
    }

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.redireccionar("/faces/index.xhtml");

    }
    
    public boolean esAdminperoInicioSesion(){
        boolean retorno = true;
        
        if(this.IncioSesion == true && this.tienePermisos == true){
            retorno=false;
        }
        
        return retorno;
    }

     public void pruebaCorreo(Carrito carrito) {

        final String fromEmail = "tornasolbycinthya@gmail.com"; //requires valid gmail id
        final String password = "xbnfdjqvqwpvgezt"; // correct password for gmail id
        final String toEmail = "" + correo; // can be any email id 
        final String toEmail2 = "tornasolbycinthya@gmail.com"; //CORREO DE CINTIA AQUI!!!!!!!!!!

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        this.cargarDatosCorreo(carrito);
        ServicioEmailUtil.sendEmail(session, toEmail, "Confirmación de pedido!", sb.toString());
        ServicioEmailUtil.sendEmail(session, toEmail2 , "Copia del pedido de: "+this.usuarioTO.getNombreCompleto()+", "+"Correo: "+correo, sb.toString());
    }

    public void cargarDatosCorreo(Carrito pCarrito) {
       
        String cadenaItems = " ";
        actualizarListaCarritoDetalle();
//        for (CarritoDetalleTO carritoDetalleTO : this.getListaCarritoDetalle()) {
//            cadenaItems += "" + carritoDetalleTO.getProducto().getNombreProducto() + " \t  \t" + carritoDetalleTO.getProducto().getPrecioProducto() + " \t\t " + carritoDetalleTO.getProducto().getDescripcionProducto() + "\n";
//        }
        for (CarritoDetalleTO carritoDetalle : this.getListaCarritoDetalle()) {
            cadenaItems += "" + carritoDetalle.getProducto().getNombreProducto() + " \t  \t" + carritoDetalle.getProducto().getPrecioProducto() + " \t\t " + carritoDetalle.getProducto().getDescripcionProducto() + "\n";
        }

        int orden = pCarrito.getIdCarrito();

        //System.out.println( "PRUEBA "+this.crudView.getGranSubtotal());
        //this.crudView.getGranSubtotal();
        sb = new StringBuilder();
        sb.append("***************************************************************************************************");
        sb.append("\n");
        sb.append("TORNASOL BY CIN");
        sb.append("\n");
        sb.append("Correo: tornasolbycin@gmail.com");
        sb.append("\n");
        sb.append("Telefono: 8888-8888");
        sb.append("\n");
        sb.append("Orden: ").append(orden + "\tNombre Completo:  " +this.usuarioTO.getNombreCompleto());
        sb.append("\n");
        sb.append("Telefono: ").append(this.usuarioTO.getTelefonoUsuario() + "\tCorreo: " +this.usuarioTO.getCorreoUsuario());
        sb.append("\n");
        sb.append("Dirección: ").append(this.usuarioTO.getDireccionUsuario());
        sb.append("\n");
        sb.append("***************************************************************************************************");
        sb.append("\n");
        sb.append("Producto                     Precio           Descripción");
        sb.append("\n");
        sb.append("***************************************************************************************************");
        sb.append("\n");
        sb.append(cadenaItems);
        sb.append("\n");
        sb.append("***************************************************************************************************");
        sb.append("\n");
        sb.append("Total:").append(pCarrito.ObtenerTotal());
     
        
        System.out.println("CADENA ITEMS: "+cadenaItems);
    }
    
    public void rcontrasena() {  //RECUPERAR CONTRASENA NO TOCAR!!!
try{
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        usuarioTO = new UsuarioTO(this.getCorreo(), this.getClave());
        usuarioTO = servicioUsuario.validarRecuperarContrasena(usuarioTO);

        final String fromEmail = "tornasolbycinthya@gmail.com"; //requires valid gmail id
        final String password = "xbnfdjqvqwpvgezt"; // correct password for gmail id
        final String toEmail = correo; // can be any email id


        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        ServicioEmailUtil.sendEmail(session, toEmail , "Recuperar Contraseña!", "Su contraseña es: "+usuarioTO.getClaveUsuario());
          
        
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación Exitosa", "Por Favor Revisar su Correo"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al ingresar", "La información no ha sido procesada correctamente. "));

            e.printStackTrace();

        }
    }
    
     public void recContra() {   //TESTER DE RECUPERAR CONTRASENA
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        usuarioTO = new UsuarioTO(this.getCorreo(), this.getClave());
        usuarioTO = servicioUsuario.validarRecuperarContrasena(usuarioTO);
        
        if (usuarioTO != null) {
            System.out.println("Funka!");
            System.out.println("Correo: "+correo);
            System.out.println("Contra: "+usuarioTO.getClaveUsuario());
        }else{
            System.out.println("NO SIRVEEEEEEEEEEEEEEEE");
        }
    }

    public CarritoDetalleTO getCarritoDetalleTO() {
        return cdto1;
    }

    public void setCarritoDetalleTO(CarritoDetalleTO carritoDetalleTO) {
        this.cdto1 = carritoDetalleTO;
    }

    public void search() {
        this.listaProductos = new ServicioProducto().buscarProducto(this.busqueda);
    }
    
    public void ingresarOrdenesAdmin(){
        this.redireccionar("/faces/OrdenesAdmin.xhtml");
    }

    public void ingresarLogin() {
        this.actualizarListaProducto();
        this.redireccionar("/faces/Login.xhtml");
    }

    public void ingresarIndexCliente() {
        this.redireccionar("/faces/indexCliente.xhtml");
    }

    public void ingresarCarrito() {
        this.redireccionar("/faces/tabListaCarrito.xhtml");
    }

    public void ingresarHome() {
        this.redireccionar("/faces/index.xhtml");
    }

    public void ingresarAdminProducto() {
        this.redireccionar("/faces/testPage.xhtml");
    }
 public void ingresarterminos() {
        this.redireccionar("/faces/TerminosUso.xhtml");
    }
      public void ingresarprivacidad() {
        this.redireccionar("/faces/Politicasprivacidad.xhtml");
    }
    public void ingresarHomeCliente() {
        this.redireccionar("/faces/indexCliente.xhtml");
    }

    public CarritoDetalleTO getCdto() {
        return cdto;
    }

    public void setCdto(CarritoDetalleTO cdto) {
        this.cdto = cdto;
    }

    public void ingresarHomePagar() {
        FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Compra realizada", "Revise su correo electronico"));
        this.redireccionar("/faces/index.xhtml");
    }

    public void ingresarRegistro() {
        this.redireccionar("/faces/Registrarse.xhtml");
    }

    public void ingresarVerPerfil() {
        this.redireccionar("/faces/PerfilCliente.xhtml");
        this.actualizarListaCarritoDetalleO();
    }

    public void ingresarPerfil() {
        this.redireccionar("/faces/EditarInformacion.xhtml");
    }

    public void ingresarPagar() {
        this.redireccionar("/faces/Pagar.xhtml");
    }
    
    public void ingresarRecuperarContrasena() {
        this.redireccionar("/faces/RecuperarContrasena.xhtml");
    }
    
    public void redireccionar(String ruta) {

        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);

        } catch (Exception e) {

        }
    }

    public ControladorLogin(String correo, String clave) {
        this.correo = correo;
        this.clave = clave;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public UsuarioTO getUsuarioTO() {
        return usuarioTO;
    }

    public void setUsuarioTO(UsuarioTO usuarioTO) {
        this.usuarioTO = usuarioTO;
    }

    public List<CarritoDetalleTO> getListaPasada() {
        return listaPasada;
    }

    public void setListaPasada(List<CarritoDetalleTO> listaPasada) {
        this.listaPasada = listaPasada;
    }

    public List<DeseoTO> getSumaDeseo() {
        return SumaDeseo;
    }

    public void setSumaDeseo(List<DeseoTO> SumaDeseo) {
        this.SumaDeseo = SumaDeseo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public UsuarioTO getClienteTO() {
        return usuarioTO;
    }

    public void setClienteTO(UsuarioTO clienteTO) {
        this.usuarioTO = clienteTO;
    }

    public List<UsuarioTO> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<UsuarioTO> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ProductoTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoTO producto) {
        this.producto = producto;
    }

    public List<ProductoTO> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoTO> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public List<UsuarioTO> getListaContra() {
        return listaContra;
    }

    public void setListaContra(List<UsuarioTO> listaContra) {
        this.listaContra = listaContra;
    }
    
    public List<OrdenTO> getListaOrdenes() {
        return listaOrdenes;
    }

    public void setListaOrdenes(List<OrdenTO> listaOrdenes) {
        this.listaOrdenes = listaOrdenes;
    }

    public List<DeseoTO> getListaDeseo() {
        return listaDeseo;
    }

    public void setListaDeseo(List<DeseoTO> listaDeseo) {
        this.listaDeseo = listaDeseo;
    }

    public List<CarritoDetalleTO> getListaCarritoDetalleO() {
        return listaCarritoDetalleO;
    }

    public void setListaCarritoDetalleO(List<CarritoDetalleTO> listaCarritoDetalleO) {
        this.listaCarritoDetalleO = listaCarritoDetalleO;
    }

    public List<Carrito> getListaCarrito() {
        return listaCarrito;
    }

    public void setListaCarrito(List<Carrito> listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    public List<CarritoDetalleTO> getListaCarritoDetalle() {
        return listaCarritoDetalle;
    }

    public void setListaCarritoDetalle(List<CarritoDetalleTO> listaCarritoDetalle) {
        this.listaCarritoDetalle = listaCarritoDetalle;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public String getClaveConfirm() {
        return claveConfirm;
    }

    public void setClaveConfirm(String claveConfirm) {
        this.claveConfirm = claveConfirm;
    }

    public boolean isIncioSesion() {
        return IncioSesion;
    }

    public boolean isTienePermisos() {
        return tienePermisos;
    }

    public void setTienePermisos(boolean tienePermisos) {
        this.tienePermisos = tienePermisos;
    }

    public void setIncioSesion(boolean IncioSesion) {
        this.IncioSesion = IncioSesion;
    }

    public List<CarritoDetalleTO> getSuma() {
        return Suma;
    }

    public void setSuma(List<CarritoDetalleTO> Suma) {
        this.Suma = Suma;
    }

    public List<CarritoDetalleTO> getListaOrden() {
        return listaOrden;
    }

    public void setListaOrden(List<CarritoDetalleTO> listaOrden) {
        this.listaOrden = listaOrden;
    }

    public boolean isVengoFinalizar() {
        return vengoFinalizar;
    }

    public void setVengoFinalizar(boolean vengoFinalizar) {
        this.vengoFinalizar = vengoFinalizar;
    }

    public StringBuilder getSb() {
        return sb;
    }

    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }

    public CrudView getCrudview() {
        return crudview;
    }

    public void setCrudview(CrudView crudview) {
        this.crudview = crudview;
    }

}
