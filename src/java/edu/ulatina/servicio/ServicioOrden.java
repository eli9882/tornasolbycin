
package edu.ulatina.servicio;

import edu.ulatina.model.OrdenTO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioOrden extends Servicio {

        public List<OrdenTO> lista(){//ORDEN
            
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrdenTO>listaRetorno = new ArrayList<OrdenTO>();
        try{
            String sql = "SELECT idorden, idusuario, fecha, domicilio FROM orden";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                int idorden = rs.getInt("idorden");
                int idusuario = rs.getInt("idusuario");
                Date fecha = rs.getDate("fecha");
                String domicilio = rs.getString("domicilio");
                OrdenTO ordenTO = new OrdenTO(idorden,idusuario,fecha,domicilio);
                listaRetorno.add(ordenTO);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (rs != null && !rs.isClosed()){
                    rs.close();
                    rs = null;
                }
                if(pstmt != null && !pstmt.isClosed()){
                    pstmt.close();
                    pstmt = null;
                }
                this.desconectar();
                
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return listaRetorno;
        
    }
    
}
