/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinhh.bookclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author No
 */
@WebServlet(name = "SearchBookServlet", urlPatterns = {"/search.do"})
public class SearchBookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Book book=getABook(request);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>The book info</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h5>Debug info: This Servlet BookList is invoked from " + request.getContextPath() + "</h1>");
            if (book == null) {
                out.println("<h1>The book not found!!!</h1>");
            } else {
                out.println("<h1>The book you are looking for!!!</h1>");
                out.println("<table border='1px' style='border-collapse: collapse'>"
                        + "<tr>"
                        + "<th>ISBN</th>"
                        + "<th>Title</th>"
                        + "<th>Author</th>"
                        + "<th>Edition</th>"
                        + "<th>Year</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>" + book.getIsbn() + "</td>"
                        + "<td>" + book.getTitle() + "</td>"
                        + "<td>" + book.getAuthor() + "</td>"
                        + "<td>" + book.getEdition() + "</td>"
                        + "<td>" + book.getPublishedYear() + "</td>"
                        + "</tr>"
                        + "</table>");
            }

            out.println("<br><a href='search.html'>Return to the search page</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public Book getABook(HttpServletRequest request) {

        String baseURI = "http://localhost:6969/SE1417BookStore/api/books/";

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(baseURI);

        String isbn = request.getParameter("isbn");

        //gọi service để tìm 1 cuốn sách ta cần thêm tham số {isbn} đưa vào
        //do đó hàm gọi cần bổ sung thêm .path(mã-sách-đưa-vào)
        //tương đương gọi qua url .../api/books/mã-sách-đưa-vào
        Book book = target.path(isbn).request().accept(MediaType.APPLICATION_JSON).get(Book.class);
        return book;
    }
    
     public List<Book> getAllBooks() {

        String baseURI = "http://localhost:8080/BooksWS/api/books";

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(baseURI);

        String booksJson = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);

        List<Book> books = null;
        try {
            books = Arrays.asList(new ObjectMapper().readValue(booksJson, Book[].class));
            //gọi Jackson để convert mảng các JSON object thành List<Book>

        } catch (JsonProcessingException ex) {
            log(ex+"");
        }


        return books;
    }
    
    // ta lam rieng 1 ham de goi cai apt search Book  ..../api/books/(Ma-sach-can-tim)
    //API nay se tra ve cho ta 1 cuon sach neu thay, Book duoc return
    // ban chat ngam la api này nó sẽ trả về json {{"isbn":"2000         ","title":"Máº¯t Biáº¿c","author":"Nguyá»n Nháº­t Ãnh","edition":10,"publishedYear":2020}}
    // nhưng nhờ code của mình có thư viện jackson, nó sẽ tự convert chuỗi json thành new book()
    // va ta tu biet cach dung new book để đẩy vào trang jsp hay bất cứ front-end framework nào
    //y chang như bạn gọi DAO, DTO nhưng API đã lo hết rồi thay vì phải tự viết

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
