
package edu.ulatina.servicio;
import edu.ulatina.model.CarritoDetalleTO;
import edu.ulatina.model.DeseoDetalleTO;
import edu.ulatina.model.DeseoTO;
import edu.ulatina.model.ProductoTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServicioDeseo extends Servicio {

    public int AgregarDeseo(DeseoTO deseo) {

        PreparedStatement pstmt = null;
        int idCarrito = 0;

        try {
            String sql = "INSERT INTO deseo (idUsuario, idProducto, Cantidad) VALUES (?,?,?)";
            pstmt = this.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, deseo.getIdUsuario());
            pstmt.setInt(2, deseo.getProducto().getIdProducto());
            pstmt.setInt(3, deseo.getCantidad());
            pstmt.execute();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idCarrito = rs.getInt(1);
            }
        } catch (SQLException e) {

            System.out.println(e.toString());

        }

        return idCarrito;

    }

    public List<DeseoTO> listaDeseoTO(int idUsuario) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DeseoTO> listaRetorno = new ArrayList<DeseoTO>();
        try {
            String sql = "SELECT deseo.idDeseo, deseo.idUsuario, deseo.Cantidad, producto.idProducto, producto.nombre, producto.descripcion,producto.precio, producto.foto "
                    + "FROM deseo INNER JOIN producto on deseo.idProducto= producto.idproducto  "
                    + "WHERE idUsuario= ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1,idUsuario);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int IdDeseo = rs.getInt("idDeseo");
                int IdUsuario = rs.getInt("idUsuario");
                int IdProducto = rs.getInt("idProducto");
                int Cantidad = rs.getInt("Cantidad");
                String Nombre = rs.getString("nombre");
                String Descripcion = rs.getString("descripcion");
                int Precio = rs.getInt("precio");
                String Foto = rs.getString("foto");

                DeseoTO deseoTO = new DeseoTO();
                deseoTO.setIdDeseo(IdDeseo);
                deseoTO.setIdUsuario(IdUsuario);
                deseoTO.setCantidad(Cantidad);

                ProductoTO producto = new ProductoTO();
                producto.setIdProducto(IdProducto);
                producto.setNombreProducto(Nombre);
                producto.setDescripcionProducto(Descripcion);
                producto.setPrecioProducto(Precio);
                producto.setFotoProducto(Foto);

                deseoTO.setProducto(producto);

                listaRetorno.add(deseoTO);
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
    
    public void eliminar(DeseoTO deseoDetalleTO) {

        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM deseo WHERE IdProducto = ? and idDeseo = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1,deseoDetalleTO.getProducto().getIdProducto() );
            pstmt.setInt(2, deseoDetalleTO.getIdDeseo());
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
    
    public List<DeseoTO> SumaDeseo (int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DeseoTO> listaRetorno = new ArrayList<DeseoTO>();
        try {
            String sql = "SELECT SUM(producto.precio) as Subtotal FROM deseo INNER JOIN producto ON deseo.idProducto= producto.idproducto WHERE idUsuario ="+id;
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Double Subtotal = rs.getDouble("Subtotal");

                DeseoTO deseoTO = new DeseoTO(Subtotal);
                listaRetorno.add(deseoTO);
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
    
     
     public List<DeseoTO> listaDeseoAct (int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<DeseoTO> listaRetorno = new ArrayList<DeseoTO>();
        try {
            String sql = "SELECT producto.idproducto, producto.nombre,producto.descripcion,producto.precio, d.cantidad,d.idDeseo "
                    + "FROM deseo d INNER JOIN deseo ON d.idDeseo = d.idDeseo INNER JOIN producto ON producto.idproducto = d.idProducto \n"
                    + "WHERE d.idDeseo = (SELECT MAX(d.idDeseo) FROM deseo d\n"
                    + "WHERE d.idUsuario = ? ORDER BY d.idDeseo DESC)";

            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Double precio = rs.getDouble("precio");
                int Cantidad = rs.getInt("cantidad");
                int IdDeseo = rs.getInt("idDeseo");

                ProductoTO producto = new ProductoTO(nombre, descripcion, precio);
                producto.setIdProducto(idProducto);
                DeseoTO deseoTO = new DeseoTO(Cantidad, producto);
                deseoTO.setProducto(producto);
                deseoTO.setIdDeseo(IdDeseo);

                listaRetorno.add(deseoTO);
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
        System.out.println("tamano lista " + listaRetorno.size());
        return listaRetorno;

    }
    
  
}
