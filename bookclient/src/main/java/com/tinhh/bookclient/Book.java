/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinhh.bookclient;

import java.io.Serializable;

/**
 *
 * @author No
 */
//interface ko ep minh phai viet code/implement duoc goi la MARKET INTERFACE
public class Book implements Serializable {

    private String isbn;        //ma sach chuan quoc te
    private String title;       //tua de sach
    private String author;      // ha dang chuan CSDL
    private int edition;        // lan xuat ban, an ban so
    private int publishedYear;  // nam xuat ban

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Book(String isbn, String title, String author, int edition, int publishedYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.publishedYear = publishedYear;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return String.format("|%13s|%-30s|%-20s|%4d|%4d|",isbn,title,author,edition,publishedYear);
    }
    public static void main(String[] args) {
        Book b1 = new Book("b1", "Đời Ngắn Đừng Ngủ Dài", "JP", 2018,1);
        Book b2 = b1;
        System.out.println(b1);
        System.out.println(b2);
        b2.setAuthor("ahihi ");
        System.out.println("1111111111");
        System.out.println(b1);
        b1=new Book("b2", "Con meo gia trong gioi sinh vien", "JP", 2018,1);
        System.out.println("22222222222222222222");
        System.out.println(b1);
        System.out.println(b2);
    }
    
}
