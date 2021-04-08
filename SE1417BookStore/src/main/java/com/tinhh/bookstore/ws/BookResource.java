/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tinhh.bookstore.ws;

import com.tinhh.bookstore.dao.BookDAO;
import com.tinhh.bookstore.dto.Book;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author No
 */
@Path("/books") // url: localhost:6969/contextpath/api/books
// url den theo cach nao: get hay post hay ....

//quy uoc giang ho:
//...../api/books -> select all
//         /books/ma-so-sach -> lay 1 cuon theo isbn
public class BookResource {

    //xin tomcat ti context
    @Context
    UriInfo ui; // day la khai niem Injecttion chich cai moi truong tomcat vao trong jersey
                // nho ui nay minh moi lay duoc url de minh noi them voi /api/,,,, de tra ve
    
    //can DAO
    private BookDAO dao = BookDAO.getBookDAO();
    //singleton de khong can new nhieu dao
    //ko noi them gi thi vao ham nay qua nga~ get
    //...../api/books
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return dao.getAllBook();
    }
    
    //tui muon lay 1 cuon sach thi sao
    // phai du ma sach vao
    //tao 1 ham moi tra ve 1 sach
    //phai phan biet voi ham get o tren
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{isbn}") // duong dan phu, khong phai hard code ma phai la ma sach
            // ma sach thi phai thay doi theo cuon minh muon tim - parameter
    // url:...../api/books - lay het cac sach
    // ........./api/books/isbn - can
    //                    map vao bien dau vao cua ham
    public Book getABook(@PathParam("isbn")String isbn){
        return dao.getABook(isbn);
    }
    
    
    //ham chen vao 1 cuon sach vao CSDL
    //DUA CHUOI JSON QUA URL, SERVICE  SE TU CONVERT THANH OBJECT BOOK
    // VA DUA CHO DAO
    // goi tu phia nguoi dung cham vao ham nay, goi json ve cuon sach
    // muon chen cho ham nay . Ham chi nhan Book, Json duoc convert ngam
    @POST
    @Consumes(MediaType.APPLICATION_JSON) //JSON gui len qua method POST
    // lam sao cham em :  xai chung url voi get nhung phai la POST
    // lam sao lam nhieu nay qua trinh duyet : thua
    // vi trinh duyet gi URL la get 
    // can co tool de gia lap (POSTMAN , SOAPUI,...) 
    public Response addABook(Book book) throws URISyntaxException{
        // goi dao luon vi da co cuon sach dua vao
        String newIsbn = dao.insertBook(book);
        // khi chen thanh cong chuan API hay quy uoc tra ve URL tro den cai cuon sach vua chen
        //...../api/books/ma-sach-vua-chen
        //muc tieu de select lai ngay xem co dung khong
        // muon tra ve duoc url ta phai xin tomcat cai context de lay duoc  may thu lien quan den url do ta dang dung o day la thang Jersey no lo, Tomcat bao ben ngoai
        // neu cham chen thanh cong thi ta co ma sach, tra ve url dep
        // neu chen khong thanh cong thi bao loi
        URI url= new URI(ui.getBaseUri() + "books/"+newIsbn);
        // url: ...... /api/books/ma-sach
        //minh da build duoc url roi , search sach theo isbn da lam
        if(newIsbn!=null){
            return Response.created(url).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            // mac dinh neu post thanh cong , ma tra ve 201
            // get thanh cong , ma tra ve 200
            // that bai tuy tinh huong ma tra ve se khac nhau(4xx,5xx)
        }
        
    }
}
//    @GET //ham nay ung voi GET goi qua URL
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getHelloMessage(){
//        return "this is 1st message comes from 1st API develop by my own - tinhh";
//    }
//    
//    @GET //ham nay ung voi GET heng , ham tren cung GET
//    @Path("/get-a-book") // url ...contextpath/api/books/get-a-book
//    @Produces(MediaType.APPLICATION_JSON)
//    public Book getABook(){
//        return new Book("b1", "Đời Ngắn Đừng Ngủ Dài", "JP", 2018,1);
//        //Book b2 = new Book("b2", "Tonny Buổi Sáng", "VN", 2017,14);
//        
//    }
//    
//    @GET //ham nay ung voi GET heng , ham tren cung GET
//    @Path("/get-all") // url ...contextpath/api/books/get-all
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Book> getListBook(){
//        ArrayList<Book> list=new ArrayList<>();
//        list.add(new Book("b1", "Đời Ngắn Đừng Ngủ Dài", "JP", 2018,1));
//        list.add(new Book("b2", "Tonny Buổi Sáng", "VN", 2017,14));
//        list.add(new Book("b3", "Đừng đi ăn trưa một mình", "JP", 2020, 3));
//        return list;
//    }
//}
