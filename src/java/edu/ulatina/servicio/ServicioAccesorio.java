package edu.ulatina.servicio;

import edu.ulatina.model.ProductoTO;
import edu.ulatina.model.AccesorioTO;
import edu.ulatina.servicio.Servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioAccesorio extends Servicio {

    public List<AccesorioTO> listaAccesorioTO() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AccesorioTO> listaRetornoAccesorioTO = new ArrayList<AccesorioTO>();
        try {
            String sql = "SELECT a.idAccesorio, p.idproducto, p.nombre, p.descripcion, p.meGusta, p.foto, p.porcentajeGanancia, p.precio, p.estado, a.nombreAccesorio, a.cantidadAccesorio, a.precioUAccesorio "
                    + "FROM proyecto2.accesorio a INNER JOIN proyecto2.producto p ON a.idProducto = p.idproducto";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idAccesorio = rs.getInt("idAccesorio");
                int idproducto = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String foto = rs.getString("foto");
                int porcentajeGanancia = rs.getInt("porcentajeGanancia");
                double precio = rs.getDouble("precio");
                String estado = rs.getString("estado");
                String nombreAccesorio = rs.getString("nombreAccesorio");
                int cantidadAccesorio = rs.getInt("cantidadAccesorio");
                double precioUAccesorio = rs.getDouble("precioUAccesorio");
                int meGusta = rs.getInt("meGusta");
                ProductoTO productoTO = new ProductoTO(idproducto, nombre, descripcion, foto, porcentajeGanancia, precio, estado, meGusta);
                AccesorioTO accesorioTO = new AccesorioTO(idAccesorio, productoTO, nombreAccesorio, cantidadAccesorio, precioUAccesorio);
                listaRetornoAccesorioTO.add(accesorioTO);
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
        return listaRetornoAccesorioTO;
    }

    public List<AccesorioTO> filtrarAccesoriosXProducto(ProductoTO productoTO, Connection conexion) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AccesorioTO> listaRetornoAccesorioTO = new ArrayList<AccesorioTO>();
        try {
            String sql = "SELECT a.idAccesorio, p.idproducto, a.nombreAccesorio, a.cantidadAccesorio, a.precioUAccesorio "
                    + "FROM proyecto2.accesorio a, proyecto2.producto p WHERE p.idproducto = a.idProducto AND a.idProducto = ?";
            pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, productoTO.getIdProducto());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int idAccesorio = rs.getInt("idAccesorio");
                String nombreAccesorio = rs.getString("nombreAccesorio");
                int cantidadAccesorio = rs.getInt("cantidadAccesorio");
                double precioUAccesorio = rs.getDouble("precioUAccesorio");
                AccesorioTO accesorioTO = new AccesorioTO(idAccesorio, productoTO, nombreAccesorio, cantidadAccesorio, precioUAccesorio);
                listaRetornoAccesorioTO.add(accesorioTO);
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaRetornoAccesorioTO;
    }

    public List<AccesorioTO> filtrarAccesoriosXProducto(ProductoTO productoTO) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AccesorioTO> listaRetornoAccesorioTO = new ArrayList<AccesorioTO>();
        try {
            String sql = "SELECT a.idAccesorio, p.idproducto, a.nombreAccesorio, a.cantidadAccesorio, a.precioUAccesorio FROM proyecto2.accesorio a, proyecto2.producto p WHERE p.idproducto = a.idProducto AND a.idProducto = ?";
            Connection con = this.getConexion();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, productoTO.getIdProducto());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int idAccesorio = rs.getInt("idAccesorio");
                String nombreAccesorio = rs.getString("nombreAccesorio");
                int cantidadAccesorio = rs.getInt("cantidadAccesorio");
                double precioUAccesorio = rs.getDouble("precioUAccesorio");
                AccesorioTO accesorioTO = new AccesorioTO(idAccesorio, productoTO, nombreAccesorio, cantidadAccesorio, precioUAccesorio);
                listaRetornoAccesorioTO.add(accesorioTO);
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
        return listaRetornoAccesorioTO;
    }

    public void insertar(AccesorioTO accesorioTO, ProductoTO productoTO) {
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO accesorio (idAccesorio ,idProducto, nombreAccesorio, cantidadAccesorio, precioUAccesorio) VALUES (?,?,?,?,?)";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, this.ultimoIdAccesorio());
            pstmt.setInt(2, productoTO.getIdProducto());
            pstmt.setString(3, accesorioTO.getNombreAccesorio());
            pstmt.setInt(4, accesorioTO.getCantidadAccesorio());
            pstmt.setDouble(5, accesorioTO.getPrecioUAccesorio());
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

    public void actualizar(AccesorioTO accesorioTO) {
        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE proyecto2.accesorio SET nombreAccesorio=?, cantidadAccesorio=?, precioUAccesorio=? WHERE idAccesorio=?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setString(1, accesorioTO.getNombreAccesorio());
            pstmt.setInt(2, accesorioTO.getCantidadAccesorio());
            pstmt.setDouble(3, accesorioTO.getPrecioUAccesorio());
            pstmt.setInt(4, accesorioTO.getIdAccesorio());
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

    public void eliminar(AccesorioTO accesorioTO) {
        PreparedStatement pstmt = null;
        try {
            String sql = "DELETE FROM proyecto2.accesorio WHERE idAccesorio = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, accesorioTO.getIdAccesorio());
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

    public boolean validar(AccesorioTO accesorioTO) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean retorno = false;
        try {
            String sql = "SELECT * FROM proyecto2.accesorio WHERE idAccesorio = ? ";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, accesorioTO.getIdAccesorio());
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

    public int ultimoIdAccesorio() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int idAccesorioMAX = 0;
        try {
            String sql = "SELECT max(idAccesorio) AS ultimo FROM accesorio";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                idAccesorioMAX = rs.getInt("ultimo");
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
        return idAccesorioMAX + 1;
    }
}
