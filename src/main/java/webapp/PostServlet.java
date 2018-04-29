package webapp;

import model.PostManager;
import entity.Book;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import entity.Post;

import helpers.DatabaseConnect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet(urlPatterns = "/post")

public class PostServlet extends HttpServlet {
	
	PostManager pm = new PostManager();
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String logout = request.getParameter("logout");
		HttpSession session = request.getSession();
		session.setAttribute("login","true");
		session.setAttribute("cur", request.getRequestURI());
		if(logout!=null || session.getAttribute("username")==null) {
			session.invalidate();
			response.sendRedirect("/signup");
			return;
		}								
		ArrayList<Post> post = new ArrayList<Post>();
		ArrayList<Book> book = new ArrayList<Book>();
		post = pm.getPost("12");
		for(Post p: post) {
			book.add(p.getB());
		}		
		request.setAttribute("bookData", book);
		request.setAttribute("postData", post);		
		request.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(
				request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, 
			IOException {
		String pid = "RE13";
		String uid = "12";
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		pm.createPost(pid,isbn,title,author,uid,description,price,true);
		//request.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(request, response);
		response.sendRedirect("/post");
	}
	
	
}
