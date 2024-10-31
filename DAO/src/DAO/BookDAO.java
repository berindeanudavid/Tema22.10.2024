package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Helper.DBHelper;
import Pojo.Book;

public class BookDAO {
	
    public static List<Book> getAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>(); 
        Connection conn = DBHelper.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from book");
        while(rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int pages = rs.getInt("pages");
            Book buk = new Book(id, title, author, pages);
            bookList.add(buk);
        }
        DBHelper.closeConnection();        
        return bookList; 
    }
    
    public static int deleteOneBook(int id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        Statement stmt = conn.createStatement();
        int deletedRows = stmt.executeUpdate("delete from book where id = " + id);
        DBHelper.closeConnection();
        return deletedRows; 
    }
    
    public static int createOneNewBook(Book bookToCreate) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "insert into book (title, author, pages) values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, bookToCreate.getTitle()); 
        ps.setString(2, bookToCreate.getAuthor());
        ps.setInt(3, bookToCreate.getPages());
        int affectedRows = ps.executeUpdate();
        DBHelper.closeConnection();
        return affectedRows; 
    }
    
    public static int updateBook(Book book) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String query = "update book set title= ?, author= ?, pages= ? where id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());
        ps.setInt(3, book.getPages());
        ps.setInt(4, book.getId());
        int rowsUpdated = ps.executeUpdate();
        DBHelper.closeConnection();
        return rowsUpdated; 
    }
    
    public static Book getBookById(int id) throws SQLException {
        Connection conn = DBHelper.getConnection(); 
        List<Book> book = getAllBooks(); 
        for(Book buk : book) { 
            if(buk.getId() == id) {  
                return buk; 
            }
        }
        return null; 
    }
    
}
