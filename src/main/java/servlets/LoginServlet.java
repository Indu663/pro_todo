package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ToDoDAO;
import dao.ToDoDAOImpl;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        ServletContext ctxt = getServletContext();
        HttpSession session = request.getSession();

        // Get parameters
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        // Validate input
        if (email == null || email.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            request.setAttribute("loginError", "Email/Password cannot be empty");
            ctxt.getRequestDispatcher("/Login.jsp").forward(request, response);
            return; // Exit after forwarding
        }

        // Authenticate user
        ToDoDAO dao = ToDoDAOImpl.getInstance();
        int regId = dao.login(email.trim(), pass.trim());

        if (regId > 0) {
            session.setAttribute("regId", regId);
            ctxt.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
        } else {
            request.setAttribute("loginError", "Invalid Email/Password");
            ctxt.getRequestDispatcher("/Login.jsp").forward(request, response);
        }

        
    }
}

