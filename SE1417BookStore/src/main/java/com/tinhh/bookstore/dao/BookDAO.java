/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinhh.bookstore.dao;

import com.tinhh.bookstore.dto.Book;
import com.tinhh.bookstore.util.DBUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author No
 */
public class BookDAO implements Serializable {

    //singleton pattern
    private static BookDAO dao;

    // Khai bao ket noi CSDL HERE
    private Connection conn = DBUtil.makeConnection();

    //tao se tra ve cho ai do ben ngoai mot BookDAO object
    // de roi cham cac ham getABook() updateBook()... nhu bth
    // van de la khong cho new truc tiep DAO
    // xai DAO o ngoai claa nay , vi du nhu ben controller bang cach
    //Book dao =  BookDAO.getBookDAO()
    public static BookDAO getBookDAO() {
        if (dao == null) {
            dao = new BookDAO();
        }
        return dao;
    }

    //constructor default de private, de cam khong cho ai new class nay
    private BookDAO() {
    }

    // viet ham  lay data tu CSDL len
    // giong 100% mon java web
    public Book getABook(String isbn) {
        PreparedStatement preStm; // chuan bi cau SQL dua xuong csdl
        ResultSet rs; // lay tu csdl thi tra ve 1 tap data co hang co cot
        // minh duyet qua de lay tung cot sau do minh new book de return
        Book book;
        try {
            String sql = "Select * from Book where isbn = ?";
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, isbn);
            rs = preStm.executeQuery();
            if (rs.next()) {
                book = new Book(rs.getString("Isbn"), rs.getString("Title"), rs.getString("Author"), rs.getInt("Edition"), rs.getInt("PublishedYear"));
                return book;
            }
        } catch (Exception e) {
            System.out.println("Search Book By isbn, ERROR HERE");
            e.printStackTrace();
        }
        return null; // viet cau lenh Select Where lsbn = ma sach nhu bth
    }

    public ArrayList<Book> getAllBook() {
        PreparedStatement preStm;
        ResultSet rs;
        ArrayList<Book> list = new ArrayList<>();
        try {
            String sql = "select * from Book";
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getString("Isbn"), rs.getString("Title"), rs.getString("Author"), rs.getInt("Edition"), rs.getInt("PublishedYear"));
                list.add(book);
            }
            return list;
        } catch (Exception e) {
            System.out.println("Get all book . Error here");
            e.printStackTrace();
        }
        return null;
    }

    public String insertBook(Book book) {
        PreparedStatement preStm;
        ResultSet rs;
        try {
            preStm = conn.prepareStatement("Insert Into Book(Isbn,Title,Author,Edition,PublishedYear) VALUES(?,?,?,?,?)");
            preStm.setString(1, book.getIsbn());
            preStm.setString(2, book.getTitle());
            preStm.setString(3, book.getAuthor());
            preStm.setInt(4, book.getEdition());
            preStm.setInt(5, book.getPublishedYear());
            
            if(preStm.executeUpdate()>0){
                return book.getIsbn();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //test thu main o day
    public static void main(String[] args) {
        BookDAO dao = BookDAO.getBookDAO();//khong duoc new vi singleton
        Book book= new Book("2436636288761","Trên Đường Băng","Tony Buổi Sáng",2,2017);
        System.out.println( dao.insertBook(book));

    }

}
