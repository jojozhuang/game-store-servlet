package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Iterator;
import com.google.gson.Gson;

import johnny.gamestore.servlet.dao.ProductDao;

@WebServlet("/AutoComplete")
public class AutoComplete extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        //http://www.simplecodestuffs.com/autocomplete-in-java-web-application-using-jquery-and-json/
        response.setContentType("application/json");
        try {
            String term = request.getParameter("term");
            System.out.println("Data from ajax call " + term);

            ArrayList<String> list = ProductDao.autoCompleteProducts(term);

            String searchList = new Gson().toJson(list);
            response.getWriter().write(searchList);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
