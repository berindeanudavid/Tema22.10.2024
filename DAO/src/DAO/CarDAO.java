package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Helper.DBHelper;
import Pojo.Car;

public class CarDAO {
    
    public static List<Car> getAllCars() throws SQLException {
        List<Car> carList = new ArrayList<>(); 
        Connection conn = DBHelper.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from car");
        while(rs.next()) {
            int id = rs.getInt("id");
            String brand = rs.getString("brand");
            String model = rs.getString("model");
            int year = rs.getInt("year");
            Car car = new Car(id, brand, model, year);
            carList.add(car);
        }
        DBHelper.closeConnection();        
        return carList; 
    }
    
    public static int deleteOneCar(int id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        Statement stmt = conn.createStatement();
        int deletedRows = stmt.executeUpdate("delete from car where id = " + id);
        DBHelper.closeConnection();
        return deletedRows; 
    }
    
    public static int createOneNewCar(Car carToCreate) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "insert into car (brand, model, year) values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, carToCreate.getBrand()); 
        ps.setString(2, carToCreate.getModel());
        ps.setInt(3, carToCreate.getYear());
        int affectedRows = ps.executeUpdate();
        DBHelper.closeConnection();
        return affectedRows; 
    }
    
    public static int updateCar(Car car) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "update car set brand= ?, model= ?, year= ? where id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, car.getBrand());
        ps.setString(2, car.getModel());
        ps.setInt(3, car.getYear());
        ps.setInt(4, car.getId());
        int rowsUpdated = ps.executeUpdate();
        DBHelper.closeConnection();
        return rowsUpdated; 
    }
    
    public static Car getCarById(int id) throws SQLException {
        Connection conn = DBHelper.getConnection(); 
        List<Car> carList = getAllCars(); 
        for(Car car : carList) { 
            if(car.getId() == id) {  
                return car; 
            }
        }
        return null; 
    }
    
}

