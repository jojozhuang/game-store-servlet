package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Checkout extends HttpServlet {    
    String err_msg = "";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        displayLogin(request, response, pw, false);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        err_msg = "";
        PrintWriter pw = response.getWriter();
        String address = request.getParameter("address");
        String creditcard = request.getParameter("creditcard");
        if (address == null || address.isEmpty()) {
            err_msg = "Address can't be empty!";
        } else if (creditcard == null || creditcard.length() != 16) {
            err_msg = "Credit card can't be empty and must be 16 numbers length!";
        }
        
        if (err_msg.isEmpty()) {
            request.setAttribute("address", address);
            request.setAttribute("creditcard", creditcard);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/PlaceOrder");
            dispatcher.forward(request, response);
            
        } else {
            displayLogin(request, response, pw, true);
        }
    }
    
    protected void displayLogin(HttpServletRequest request,
                    HttpServletResponse response, PrintWriter pw, boolean error)
                    throws ServletException, IOException {

        Helper helper = new Helper(request,pw);
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_CART);
        String errmsg = "";
        if (error) {
            errmsg = "<h3 style='color:red'>"+err_msg+"</h3>";
        }
        String template = helper.getTemplate("checkout.html");
        template = template.replace("$errmsg$", errmsg)
                           .replace("$username$", helper.username());
        helper.prepareContent(template);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
