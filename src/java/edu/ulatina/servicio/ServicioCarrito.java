package edu.ulatina.servicio;

import edu.ulatina.model.Carrito;
import edu.ulatina.model.CarritoDetalleTO;
import edu.ulatina.model.DeseoTO;
import edu.ulatina.model.ProductoTO;
import edu.ulatina.model.UsuarioTO;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ServicioCarrito extends Servicio {

    public int AgregarCarrito(Carrito carrito) {

        PreparedStatement pstmt = null;
        int idCarrito = 0;

        try {

            //INICIA LA TRANSACCION
//            conexion.setAutoCommit(false);
            //INSERTA EL CARRITO Y OBTIENE EL IDCARRITO
            String sql = "INSERT INTO carrito (IdUsuario, IdEstado, FechaCreacion) VALUES (?,?,?)";
            pstmt = this.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, carrito.getIdUsuario());
            pstmt.setInt(2, carrito.getIdEstado());
            pstmt.setTimestamp(3, carrito.getFechaCreacion());
            pstmt.execute();

            //SE OBTIENE EL IDCARRITO EL CUAL SE OCUPA AL AGREGAR LOS DETALLES
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idCarrito = rs.getInt(1);
            }
            //SI TODO ESTA BIEN FINALIZA LA TRANSACCION
//            conexion.commit(); 

        } catch (SQLException e) {
//                if(conexion!=null){
//                   try {
//                       //SI DA ERROR HACE ROLLBACK Y NO GUARDA LOS DATOS
//                       conexion.rollback();
//                   }catch (SQLException ex) {
            System.out.println(e.toString());
//                   }
//                }
        }

        return idCarrito;

    }

    public int AgregarCarritoVisitante(Carrito carrito) {

        PreparedStatement pstmt = null;
        int idCarrito = 0;

        try {

            //INICIA LA TRANSACCION
//            conexion.setAutoCommit(false);
            //INSERTA EL CARRITO Y OBTIENE EL IDCARRITO
            String sql = "INSERT INTO carrito (IdUsuario, IdEstado, FechaCreacion) VALUES (?,?,?)";
            pstmt = this.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, carrito.getIdUsuario());
            pstmt.setInt(2, carrito.getIdEstado());
            pstmt.setTimestamp(3, carrito.getFechaCreacion());
            pstmt.execute();

            //SE OBTIENE EL IDCARRITO EL CUAL SE OCUPA AL AGREGAR LOS DETALLES
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idCarrito = rs.getInt(1);
                for (CarritoDetalleTO detalle : carrito.getListaDetalles()) {
                    detalle.setIdCarrito(idCarrito);
                    this.AgregarDetalle(detalle);
                }
            }

            //SI TODO ESTA BIEN FINALIZA LA TRANSACCION
//            conexion.commit(); 
        } catch (SQLException e) {
//                if(conexion!=null){
//                   try {
//                       //SI DA ERROR HACE ROLLBACK Y NO GUARDA LOS DATOS
//                       conexion.rollback();
//                   }catch (SQLException ex) {
            System.out.println(e.toString());
//                   }
//                }
        }
        return idCarrito;
    }

    public void AgregarDetalle(CarritoDetalleTO detalle) {
        try {
            PreparedStatement pstmt = null;
//            conexion.setAutoCommit(false);

            String sqlDetalle = "INSERT INTO carritoDetalle (IdCarrito, IdProducto, Cantidad) VALUES (?,?,?)";
            pstmt = this.getConexion().prepareStatement(sqlDetalle);
            pstmt.setInt(1, detalle.getIdCarrito());
            pstmt.setInt(2, detalle.getProducto().getIdProducto());
            pstmt.setInt(3, detalle.getCantidad());
            pstmt.execute();

//            conexion.commit(); 
        } catch (SQLException e) {
            if (conexion != null) {
//                   try {
//                       //SI DA ERROR HACE ROLLBACK Y NO GUARDA LOS DATOS
//////                       conexion.rollback();
//                   }catch (SQLException ex) {
                System.out.println(e.toString());
//                   }
            }
        }
    }

    public List<Carrito> listaCarrito() {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Carrito> listaRetorno = new ArrayList<Carrito>();
        try {
            String sql = "SELECT IdCarrito, IdUsuario, IdEstado, FechaCreacion FROM carrito";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int IdCarrito = rs.getInt("IdCarrito");
                int IdUsuario = rs.getInt("IdUsuario");
                int IdEstado = rs.getInt("IdEstado");
                Timestamp FechaCreacion = rs.getTimestamp("FechaCreacion");
                Carrito carrito = new Carrito(IdCarrito, IdUsuario, IdEstado, FechaCreacion);
                listaRetorno.add(carrito);
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

    public List<CarritoDetalleTO> listaCarritoDetalle(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CarritoDetalleTO> listaRetorno = new ArrayList<CarritoDetalleTO>();
        try {
            String sql = "SELECT producto.idproducto, producto.nombre,producto.descripcion,producto.precio,cd.cantidad,carrito.idcarrito "
                    + "FROM carritoDetalle cd INNER JOIN carrito ON carrito.IdCarrito = cd.IdCarrito INNER JOIN producto ON producto.idproducto = cd.IdProducto \n"
                    + "WHERE cd.idCarrito = (SELECT MAX(c.idCarrito) FROM carrito c\n"
                    + "WHERE c.idUsuario = ? ORDER BY c.idCarrito DESC)";

            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                int IdCarrito = rs.getInt("IdCarrito");

                ProductoTO producto = new ProductoTO(nombre, descripcion, precio);

                producto.setIdProducto(idProducto);

                CarritoDetalleTO carritoDetalleTO = new CarritoDetalleTO(cantidad, producto);

                carritoDetalleTO.setProducto(producto);

                carritoDetalleTO.setIdCarrito(IdCarrito);

                listaRetorno.add(carritoDetalleTO);
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

    public List<CarritoDetalleTO> Suma(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CarritoDetalleTO> listaRetorno = new ArrayList<CarritoDetalleTO>();
        try {
            String sql = "SELECT SUM(producto.precio) as Subtotal FROM carritoDetalle INNER JOIN carrito ON carrito.IdCarrito = carritoDetalle.IdCarrito INNER JOIN producto ON producto.idproducto = carritoDetalle.IdProducto  where carrito.IdUsuario = " + id;
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Double subtotal = rs.getDouble("Subtotal");

                CarritoDetalleTO carritoDetalleTO = new CarritoDetalleTO(subtotal);
                listaRetorno.add(carritoDetalleTO);
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

    public List<CarritoDetalleTO> listaProductosPasados(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CarritoDetalleTO> listaRetorno = new ArrayList<CarritoDetalleTO>();
        try {
            String sql = "SELECT producto.idproducto, producto.nombre,producto.descripcion,producto.precio,carritoDetalle.cantidad,carrito.FechaCreacion "
                    + "FROM carritoDetalle INNER JOIN carrito ON carrito.IdCarrito = carritoDetalle.IdCarrito "
                    + "INNER JOIN producto ON producto.idproducto = carritoDetalle.IdProducto  where carrito.IdUsuario =" + id;
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                Timestamp FechaCreacion = rs.getTimestamp("FechaCreacion");

                ProductoTO producto = new ProductoTO(idProducto, nombre, descripcion, precio);

                Carrito carrito = new Carrito(FechaCreacion);

                CarritoDetalleTO carritoDetalleTO = new CarritoDetalleTO(cantidad, producto, carrito);

                listaRetorno.add(carritoDetalleTO);
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

    public List<CarritoDetalleTO> listaOrden(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CarritoDetalleTO> listaRetorno = new ArrayList<CarritoDetalleTO>();
        try {
            String sql = "SELECT producto.idproducto, producto.nombre,producto.descripcion,producto.precio,carritoDetalle.Cantidad,carritoDetalle.IdCarrito "
                    + "FROM carritoDetalle INNER JOIN carrito ON carrito.IdCarrito = carritoDetalle.IdCarrito "
                    + "INNER JOIN producto ON producto.idproducto = carritoDetalle.IdProducto  where carrito.IdUsuario = " + id;
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                int IdCarrito = rs.getInt("IdCarrito");

                ProductoTO producto = new ProductoTO(nombre, descripcion, precio);
                producto.setIdProducto(idProducto);
                CarritoDetalleTO carritoDetalleTO = new CarritoDetalleTO(cantidad, producto);
                carritoDetalleTO.setProducto(producto);
                carritoDetalleTO.setIdCarrito(IdCarrito);

                listaRetorno.add(carritoDetalleTO);
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

    public void eliminar(CarritoDetalleTO carritoDetalleTO) {

        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM carritoDetalle WHERE IdProducto = ? and IdCarrito = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, carritoDetalleTO.getProducto().getIdProducto());
            pstmt.setInt(2, carritoDetalleTO.getIdCarrito());
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

    public List<CarritoDetalleTO> listaCarritoDetalleO(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CarritoDetalleTO> listaRetorno = new ArrayList<CarritoDetalleTO>();
        try {
            String sql = "SELECT producto.idproducto, producto.nombre,producto.descripcion,producto.precio,cd.cantidad,carrito.fechacreacion FROM carritoDetalle cd INNER JOIN carrito ON carrito.IdCarrito = cd.IdCarrito INNER JOIN producto ON producto.idproducto = cd.IdProducto WHERE  carrito.idUsuario = ? ";

            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                Timestamp fechacreacion = rs.getTimestamp("fechacreacion");

                ProductoTO producto = new ProductoTO(nombre, descripcion, precio);
                producto.setIdProducto(idProducto);
                Carrito carrito = new Carrito(fechacreacion);
                CarritoDetalleTO carritoDetalleTO = new CarritoDetalleTO(cantidad);
                carritoDetalleTO.setProducto(producto);
                carritoDetalleTO.setCarrito(carrito);

                listaRetorno.add(carritoDetalleTO);
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

    public List<CarritoDetalleTO> listaCarritoDetalleAll() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CarritoDetalleTO> listaRetorno = new ArrayList<CarritoDetalleTO>();
        try {
            String sql = "SELECT producto.idproducto, producto.nombre,producto.descripcion,producto.precio,cd.cantidad,carrito.fechacreacion, usuario.nombre AS nombreU,usuario.apellido,usuario.telefono FROM carritoDetalle cd INNER JOIN carrito ON carrito.IdCarrito = cd.IdCarrito INNER JOIN producto ON producto.idproducto = cd.IdProducto INNER JOIN usuario ON carrito.idUsuario = usuario.idusuario ";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("idproducto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                Timestamp fechacreacion = rs.getTimestamp("fechacreacion");
                String nombreU = rs.getString("nombreU");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                ProductoTO producto = new ProductoTO(nombre, descripcion, precio);
                producto.setIdProducto(idProducto);
                Carrito carrito = new Carrito(fechacreacion);
                CarritoDetalleTO carritoDetalleTO = new CarritoDetalleTO(cantidad);
                carritoDetalleTO.setProducto(producto);
                carritoDetalleTO.setCarrito(carrito);
                UsuarioTO usuario = new UsuarioTO();
                usuario.setNombreUsuario(nombreU);
                usuario.setApellidoUsuario(apellido);
                usuario.setTelefonoUsuario(telefono);
                carritoDetalleTO.setUsuarioTO(usuario);
                listaRetorno.add(carritoDetalleTO);
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
    
    public Carrito validarC(Carrito carrito) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Carrito carritoTOretorno = null;
        try {
            String sql = "SELECT IdCarrito From carrito WHERE IdCarrito = (SELECT MAX(c.idCarrito) FROM carrito c\n" +
"WHERE c.idUsuario = ? ORDER BY c.idCarrito DESC)";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, carrito.getIdUsuario());
           
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idusuario");
                
                carritoTOretorno = new Carrito(id);
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
        return carritoTOretorno;
    }
    
     
      public List<Carrito> listaid(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Carrito> listaRetorno = new ArrayList<Carrito>();
        try {
            String sql = "SELECT IdCarrito From carrito WHERE IdCarrito = (SELECT MAX(c.idCarrito) FROM carrito c\n" +
"WHERE c.idUsuario = ? ORDER BY c.idCarrito DESC)";

            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idCarrito = rs.getInt("IdCarrito");
             


                Carrito carrito = new Carrito(idCarrito);

               

                listaRetorno.add(carrito);
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
