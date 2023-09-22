package edu.ulatina.servicio;

import edu.ulatina.model.Carrito;
import edu.ulatina.model.CarritoDetalleTO;
import edu.ulatina.model.ProductoTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServicioProducto extends Servicio {
    
    public ServicioProducto() {
    }
    public List<ProductoTO> listaProducto(boolean tienePermisos) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        List<ProductoTO> listaRetornoProductoTO = new ArrayList<ProductoTO>();
        try {
            if(tienePermisos == false){
                sql = "SELECT idproducto, nombre, descripcion, foto, porcentajeGanancia, precio, meGusta, estado FROM producto WHERE estado = 'Activado'";
            }else if(tienePermisos == true){
                sql = "SELECT idproducto, nombre, descripcion, foto, porcentajeGanancia, precio, meGusta, estado FROM producto";
            }
            Connection con = this.getConexion();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int idproducto = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String foto = rs.getString("foto");
                int porcentajeGanancia = rs.getInt("porcentajeGanancia");
                double precio = rs.getDouble("precio");
                String estado = rs.getString("estado");
                int meGusta = rs.getInt("meGusta");
                ProductoTO productoTO = new ProductoTO(idproducto, nombre, descripcion, foto, porcentajeGanancia, precio, estado, meGusta);
                listaRetornoProductoTO.add(productoTO);
                ServicioAccesorio servicioAccesorio = new ServicioAccesorio();
                productoTO.setListaAccesorios(servicioAccesorio.filtrarAccesoriosXProducto(productoTO, con));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                    rs = null;
                }
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }     
        return listaRetornoProductoTO;
    }

    public void insert(Carrito carrito) {
        PreparedStatement pstmt = null;
        try {
            //INICIA LA TRANSACCION
            conexion.setAutoCommit(false);
            //INSERTA EL CARRITO Y OBTIENE EL IDCARRITO
            String sql = "INSERT INTO carrito (IdUsuario, IdEstado, FechaCreacion) VALUES (?,?,?)";
            pstmt = this.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, carrito.getIdUsuario());
            pstmt.setInt(2, carrito.getIdEstado());
            pstmt.setTimestamp(3, carrito.getFechaCreacion());
            pstmt.execute();
            //SE OBTIENE EL IDCARRITO EL CUAL SE OCUPA AL AGREGAR LOS DETALLES
            int idCarrito = 0;
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idCarrito = rs.getInt(1);
            }
            //AGREGA LOS DETALLES DEL CARRITO
            List<CarritoDetalleTO> carritoDetalles = carrito.getListaDetalles();
            for (int i = 0; i < carritoDetalles.size(); i++) {
                CarritoDetalleTO detalle = carritoDetalles.get(i);

                String sqlDetalle = "INSERT INTO carrito (IdCarrito, IdProducto, Cantidad) VALUES (?,?,?)";
                pstmt = this.getConexion().prepareStatement(sqlDetalle);
                pstmt.setInt(1, idCarrito);
                pstmt.setInt(2, detalle.getProducto().getIdProducto());
                pstmt.setInt(3, detalle.getCantidad());
                pstmt.execute();
            }
            //SI TODO ESTA BIEN FINALIZA LA TRANSACCION
            conexion.commit();
        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    //SI DA ERROR HACE ROLLBACK Y NO GUARDA LOS DATOS
                    conexion.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }
        }
    }

    public void insertar(ProductoTO productoTO) {
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO proyecto2.producto (idproducto, nombre, descripcion, foto, porcentajeGanancia, precio, estado) VALUES (?,?,?,?,?,?,?)";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, productoTO.getIdProducto());
            pstmt.setString(2, productoTO.getNombreProducto());
            pstmt.setString(3, productoTO.getDescripcionProducto());
            pstmt.setString(4, productoTO.getFotoProducto());
            pstmt.setInt(5, productoTO.getGananciaProducto());
            pstmt.setDouble(6, productoTO.getPrecioProducto());
            pstmt.setString(7, "Desactivado");
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void actualizar(ProductoTO producto) {

        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE proyecto2.producto SET nombre=?, descripcion=?, foto=?, porcentajeGanancia=?, precio=? WHERE idproducto=?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setString(1, producto.getNombreProducto());
            pstmt.setString(2, producto.getDescripcionProducto());
            pstmt.setString(3, producto.getFotoProducto());
            pstmt.setInt(4, producto.getGananciaProducto());
            pstmt.setDouble(5, producto.getPrecioProducto());
            pstmt.setInt(6, producto.getIdProducto());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void addMeGusta(ProductoTO producto) {

        PreparedStatement pstmt = null;
        try {
            int nuevo = producto.getMeGustaProducto()+1;
            String sql = "UPDATE proyecto2.producto SET meGusta=? WHERE idproducto=?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, nuevo);
            pstmt.setInt(2, producto.getIdProducto());
            
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void activarDesactivar(ProductoTO producto) {

        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE proyecto2.producto SET estado=? WHERE idproducto=?";
            pstmt = this.getConexion().prepareStatement(sql);
            if (producto.getEstadoProducto().equals("Activado")) {
                pstmt.setString(1, "Desactivado");
            } else {
                pstmt.setString(1, "Activado");
            }
            pstmt.setInt(2, producto.getIdProducto());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validar(ProductoTO producto) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean retorno = false;
        try {
            String sql = "SELECT * FROM proyecto2.producto WHERE idproducto = ? ";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, producto.getIdProducto());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                retorno = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                    rs = null;
                }
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }

    public int ultimoIdProducto() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int idProductoMAX = 0;
        try {
            String sql = "SELECT max(idproducto) AS ultimo FROM proyecto2.producto";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                idProductoMAX = rs.getInt("ultimo");
                idProductoMAX = idProductoMAX + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                    rs = null;
                }
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idProductoMAX;
    }

    public List<ProductoTO> buscarProducto(String prod) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ProductoTO> listaRetorno = new ArrayList<ProductoTO>();
        try {
            String sql = "SELECT * FROM proyecto2.producto where nombre like '%" + prod + "%' OR descripcion like '%" + prod + "%'";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductoTO p = new ProductoTO();
                p.setIdProducto(rs.getInt("idproducto"));
                p.setNombreProducto(rs.getString("nombre"));
                p.setDescripcionProducto(rs.getString("descripcion"));
                p.setFotoProducto(rs.getString("foto"));
                p.setGananciaProducto(rs.getInt("porcentajeGanancia"));
                p.setPrecioProducto(rs.getDouble("precio"));
                p.setEstadoProducto(rs.getString("estado"));
                listaRetorno.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                    rs = null;
                }
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaRetorno;
    }
}
