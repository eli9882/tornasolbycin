
package edu.ulatina.servicio;

import edu.ulatina.model.OrdenDetalleTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioOrdenDetalle extends Servicio {
    
    
    public List<OrdenDetalleTO> listaOrdenDetalle(){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrdenDetalleTO>listaRetorno = new ArrayList<OrdenDetalleTO>();
        try{
            String sql = "SELECT idordendetalle, idproducto, idorden, total FROM ordendetalle";
            pstmt = this.getConexion().prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                int idordendetalle = rs.getInt("idordendetalle");
                int idproducto = rs.getInt("idproducto");
                int idorden = rs.getInt("idorden");
                double total = rs.getDouble("total");
                //OrdenDetalleTO ordenDetalleTO = new OrdenDetalleTO(idordendetalle,idproducto,idorden,total);
                //listaRetorno.add(ordenDetalleTO);
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
