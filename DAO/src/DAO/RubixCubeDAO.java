package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Helper.DBHelper;
import Pojo.RubixCube;

public class RubixCubeDAO {
    
    public static List<RubixCube> getAllRubixCubes() throws SQLException {
        List<RubixCube> rubixCubeList = new ArrayList<>(); 
        Connection conn = DBHelper.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from rubixcube");
        while(rs.next()) {
            int id = rs.getInt("id");
            String brand = rs.getString("brand");
            double weight = rs.getDouble("weight");
            int sides = rs.getInt("sides");
            RubixCube cube = new RubixCube(id, brand, weight, sides);
            rubixCubeList.add(cube);
        }
        DBHelper.closeConnection();        
        return rubixCubeList; 
    }
    
    public static int deleteOneRubixCube(int id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        Statement stmt = conn.createStatement();
        int deletedRows = stmt.executeUpdate("delete from rubixcube where id = " + id);
        DBHelper.closeConnection();
        return deletedRows; 
    }
    
    public static int createOneNewRubixCube(RubixCube cubeToCreate) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "insert into rubixcube (brand, weight, sides) values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, cubeToCreate.getBrand()); 
        ps.setDouble(2, cubeToCreate.getWeight());
        ps.setInt(3, cubeToCreate.getSides());
        int affectedRows = ps.executeUpdate();
        DBHelper.closeConnection();
        return affectedRows; 
    }
    
    public static int updateRubixCube(RubixCube cube) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "update rubixcube set brand= ?, weight= ?, sides= ? where id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, cube.getBrand());
        ps.setDouble(2, cube.getWeight());
        ps.setInt(3, cube.getSides());
        ps.setInt(4, cube.getId());
        int rowsUpdated = ps.executeUpdate();
        DBHelper.closeConnection();
        return rowsUpdated; 
    }
    
    public static RubixCube getRubixCubeById(int id) throws SQLException {
        Connection conn = DBHelper.getConnection(); 
        List<RubixCube> rubixCubeList = getAllRubixCubes(); 
        for(RubixCube cube : rubixCubeList) { 
            if(cube.getId() == id) {  
                return cube; 
            }
        }
        return null; 
    }
    
}
