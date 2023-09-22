package edu.ulatina.servicio;

import edu.ulatina.model.UsuarioTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioUsuario extends Servicio {

    public void insertar(UsuarioTO usuarioTO) {

        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO usuario(nombre, apellido, correo, clave, telefono, permisos, estado, direccion) VALUES (?,?,?,?,?,?,?,?)";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setString(1, usuarioTO.getNombreUsuario());
            pstmt.setString(2, usuarioTO.getApellidoUsuario());
            pstmt.setString(3, usuarioTO.getCorreoUsuario());
            pstmt.setString(4, usuarioTO.getClaveUsuario());
            pstmt.setString(5, usuarioTO.getTelefonoUsuario());
            pstmt.setInt(6, 1);
            pstmt.setInt(7, 2);
            pstmt.setString(8, usuarioTO.getDireccionUsuario());

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

    //METODO ACTUALIZAR INFORMACION CLIENTE
    public void actualizar(UsuarioTO clienteTO) {
        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE usuario SET nombre=?, apellido=?, correo=?, clave=?, telefono=? WHERE idusuario=?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setString(1, clienteTO.getNombreUsuario());
            pstmt.setString(2, clienteTO.getApellidoUsuario());
            pstmt.setString(3, clienteTO.getCorreoUsuario());
            pstmt.setString(4, clienteTO.getClaveUsuario());
            pstmt.setString(5, clienteTO.getTelefonoUsuario());
            pstmt.setInt(6, clienteTO.getIdUsuario());
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

    public void cambiarContra(UsuarioTO clienteTO, String contraNueva) {

        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE usuario SET clave=? WHERE idusuario = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setString(1, contraNueva);
            pstmt.setInt(2, clienteTO.getIdUsuario());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public List<UsuarioTO> lista() {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<UsuarioTO> listaRetorno = new ArrayList<UsuarioTO>();
        try {
            String sql = "SELECT idusuario, nombre, apellido, correo, clave, telefono,direccion, estado FROM usuario ORDER BY nombre desc";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idusuario = rs.getInt("idusuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                int EstadoUsuario = rs.getInt("estado");
                UsuarioTO clienteTO = new UsuarioTO(idusuario, nombre, apellido, correo, clave, telefono, direccion, EstadoUsuario);
                listaRetorno.add(clienteTO);
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

    public UsuarioTO validar(UsuarioTO clienteTO) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UsuarioTO clienteTOretorno = null;
        try {
            String sql = "SELECT idusuario, nombre, apellido, correo, clave, telefono,direccion, estado FROM usuario WHERE correo = ? AND clave = ? AND estado = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setString(1, clienteTO.getCorreoUsuario());
            pstmt.setString(2, clienteTO.getClaveUsuario());
            pstmt.setInt(3, clienteTO.getEstadoUsuario());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idusuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                int EstadoUsuario = rs.getInt("estado");
                clienteTOretorno = new UsuarioTO(id, nombre, apellido, correo, clave, telefono, direccion, EstadoUsuario);
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
        return clienteTOretorno;
    }

    public void eliminar(UsuarioTO clienteTO) {

        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM proyecto2.usuario WHERE idusuario = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, clienteTO.getIdUsuario());
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

    public UsuarioTO validarRecuperarContrasena(UsuarioTO clienteTO) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UsuarioTO clienteTOretorno = null;
        try {
            String sql = "SELECT correo , clave FROM usuario WHERE correo = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setString(1, clienteTO.getCorreoUsuario());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String correo = rs.getString("correo");
                String clave = rs.getString("clave");
                clienteTOretorno = new UsuarioTO(correo, clave);

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
        return clienteTOretorno;
    }

    public boolean validarPermisos(UsuarioTO clienteTO) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int permisosRetono = 0;
        try {
            String sql = "SELECT permisos FROM proyecto2.usuario WHERE idusuario = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, clienteTO.getIdUsuario());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                permisosRetono = rs.getInt("permisos");
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
        if (permisosRetono != 2) {
            return false;
        } else {
            return true;
        }
    }

    public void eliminarC(UsuarioTO usuarioTO) {

        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE usuario SET estado=? WHERE idusuario = ?";
            pstmt = this.getConexion().prepareStatement(sql);
            pstmt.setInt(1, 1);
            pstmt.setInt(2, usuarioTO.getIdUsuario());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                    pstmt = null;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
