package edu.ulatina.login;
//CAMBIOS

import edu.ulatina.controller.ControladorLogin;
import edu.ulatina.model.AccesorioTO;
import edu.ulatina.model.ProductoTO;
import edu.ulatina.servicio.ServicioProducto;
import edu.ulatina.servicio.ServicioAccesorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "crudViewADMIN")
@ViewScoped
        
public class CrudViewAdmin implements Serializable {

    private double precioReal;
    private double precioRecomendado;
    private String buscador;
    private ProductoTO productoSelected = new ProductoTO();
    private AccesorioTO accesorioSelected = new AccesorioTO();
    
    @ManagedProperty("#{controladorLogin}")
    private ControladorLogin controladorLogin;

    public String getBuscador() {
        return buscador;
    }

    public void setBuscador(String buscador) {
        this.buscador = buscador;
    }

    public ProductoTO getProductoSelected() {
        return productoSelected;
    }

    public void setProductoSelected(ProductoTO productoSelected) {
        this.productoSelected = productoSelected;
    }

    public AccesorioTO getAccesorioSelected() {
        return accesorioSelected;
    }

    public void setAccesorioSelected(AccesorioTO accesorioSelected) {
        this.accesorioSelected = accesorioSelected;
    }

    public double getPrecioReal() {
        return precioReal;
    }

    public void setPrecioReal(double precioReal) {
        this.precioReal = precioReal;
    }

    public double getPrecioRecomendado() {
        return precioRecomendado;
    }

    public void setPrecioRecomendado(double precioRecomendado) {
        this.precioRecomendado = precioRecomendado;
    }

    public CrudViewAdmin(ControladorLogin controladorLogin) {
        this.controladorLogin = new ControladorLogin();
    }

    public ControladorLogin getControladorLogin() {
        return controladorLogin;
    }

    public void setControladorLogin(ControladorLogin controladorLogin) {
        this.controladorLogin = controladorLogin;
    }

    public CrudViewAdmin() {
    }

    public void openNewAccesorio() {
        this.accesorioSelected = new AccesorioTO();
    }

    public void openNew() {
        this.productoSelected = new ProductoTO();
    }

    public void openNewBoth() {
        this.openNew();
        this.openNewAccesorio();
    }

    public void buscar() {
        ServicioProducto servicioProducto = new ServicioProducto();
        controladorLogin.setListaProductos(servicioProducto.buscarProducto(this.buscador));
    }

    public void actualizarListaAccesorios() {
        ServicioAccesorio servicioAccesorio = new ServicioAccesorio();
        this.productoSelected.setListaAccesorios(servicioAccesorio.filtrarAccesoriosXProducto(this.productoSelected));
    }

    public void saveProduct() {

        try {
            if (this.productoSelected.getFotoProducto() == null) {
                this.getProductoSelected().setFotoProducto("imagen-default.jpg");
            }
            ServicioProducto servicioProducto = new ServicioProducto();
            if (servicioProducto.validar(this.productoSelected)) {
                servicioProducto.actualizar(this.productoSelected);
            } else {
                this.productoSelected.setIdProducto(servicioProducto.ultimoIdProducto());
                servicioProducto.insertar(this.productoSelected);
            }
            if (this.productoSelected.getPrecioProducto() < calcularPrecioReal()) {
                this.productoSelected = new ProductoTO();
                controladorLogin.actualizarListaProducto();
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "CUIDADO", "El costo es mayor al precio al publico"));
            } else {
                this.productoSelected = new ProductoTO();
                controladorLogin.actualizarListaProducto();
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido agregado satisfactoriamente"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al ingresar", "La información no ha sido procesada correctamente."));
        }
    }

    public void actualizarEstadoProduct() {
        try {
            ServicioProducto servicioProducto = new ServicioProducto();
            servicioProducto.activarDesactivar(this.productoSelected);
            controladorLogin.actualizarListaProducto();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido eliminado satisfactoriamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar", "La información no ha sido procesada correctamente."));
        }
    }

    public void deleteAccesorio() {
        try {
            ServicioAccesorio servicioAccesorio = new ServicioAccesorio();
            servicioAccesorio.eliminar(this.accesorioSelected);
            this.calcularPrecioReal();
            this.calcularPrecio();
            this.actualizarListaAccesorios();
            this.openNewAccesorio();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "El registro ha sido eliminado satisfactoriamente"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar", "La información no ha sido procesada correctamente."));
        }
    }

    public void agregarAccesorio() {
        try {
            ServicioAccesorio servicioAccesorio = new ServicioAccesorio();
            if (servicioAccesorio.validar(this.accesorioSelected)) {
                servicioAccesorio.actualizar(this.accesorioSelected);
            } else {
                this.accesorioSelected.setIdAccesorio(servicioAccesorio.ultimoIdAccesorio());
                servicioAccesorio.insertar(this.accesorioSelected, this.productoSelected);
            }
            this.accesorioSelected = new AccesorioTO();
            this.actualizarListaAccesorios();
            this.calcularPrecioReal();
            this.calcularPrecio();
            this.openNewAccesorio();
            this.refrescarPrecioTotal();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación exitosa", "Se ha agregado el accesorio"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al agregar", "No se ha podido agregar."));
        }
    }
    
    public double retornarCostoTotal(int id, double precio) {
        ServicioAccesorio servicioAccesorio = new ServicioAccesorio();
        double costoTotal = 0;
        double utilidad = 0;
        List<AccesorioTO> listaRetornoAccesorioTO = new ArrayList<AccesorioTO>();
        ProductoTO produto = new ProductoTO();
        produto.setIdProducto(id);
        listaRetornoAccesorioTO = servicioAccesorio.filtrarAccesoriosXProducto(produto);
        for (int i = 0; i < listaRetornoAccesorioTO.size(); i++) {
            costoTotal += listaRetornoAccesorioTO.get(i).getPrecioUAccesorio() * listaRetornoAccesorioTO.get(i).getCantidadAccesorio();
        }
        utilidad = precio - costoTotal;
        return utilidad;
    }
    
    public double retornarUtilidadTotal() {
        double utilidadTotal = 0;
        List<ProductoTO> listaRetornoProductoTO = new ArrayList<ProductoTO>();
        listaRetornoProductoTO = controladorLogin.getListaProductos();
        for (int i = 0; i < listaRetornoProductoTO.size(); i++) {
            utilidadTotal += this.retornarCostoTotal(listaRetornoProductoTO.get(i).getIdProducto(), listaRetornoProductoTO.get(i).getPrecioProducto());
        }
        return utilidadTotal;
    }

    public double calcularPrecio() {
        double totalAccesorio = 0;
        double precioRecomendado = 0;
        try {
            AccesorioTO accesorioTO = new AccesorioTO();
            for (int i = 0; i < this.productoSelected.getListaAccesorios().size(); i++) {
                accesorioTO = this.productoSelected.getListaAccesorios().get(i);
                totalAccesorio += accesorioTO.getCantidadAccesorio() * accesorioTO.getPrecioUAccesorio();
            }
            precioRecomendado = totalAccesorio + ((this.productoSelected.getGananciaProducto() * totalAccesorio) / 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return precioRecomendado;
    }

    public void refrescarPrecioTotal() {
        this.productoSelected.setPrecioProducto(this.calcularPrecio());
    }

    public double calcularPrecioReal() {
        double totalAccesorio = 0;
        try {
            AccesorioTO accesorioTO = new AccesorioTO();
            for (int i = 0; i < this.productoSelected.getListaAccesorios().size(); i++) {
                accesorioTO = this.productoSelected.getListaAccesorios().get(i);
                totalAccesorio += accesorioTO.getCantidadAccesorio() * accesorioTO.getPrecioUAccesorio();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalAccesorio;
    }
    
    public boolean sinGanancia(double costoTotal, double precioPublico){
        boolean falta = false;
        if(costoTotal>precioPublico){
            falta = true;
        }
        return falta;
    }

    
}
