package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.ProductItem;
import johnny.gamestore.servlet.beans.ProductReview;
import johnny.gamestore.servlet.dao.ProductDao;

/**
 *
 * @author Johnny
 */
public class Review extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request,pw);
        
        String productkey = request.getParameter("productkey");
        String rating = request.getParameter("rating");
        String reviewtext = request.getParameter("reviewtext");
        
        ProductItem product = ProductDao.getProduct(productkey);
        
        String content = "";
        ArrayList<ProductReview> reviews = helper.getReviews(productkey);
        if (rating!=null&&reviewtext!=null) {
            if(!helper.isLoggedin()){
                HttpSession session = request.getSession(true);
                session.setAttribute(helper.SESSION_LOGIN_MSG, "Please login first!");
                response.sendRedirect("Login");
                return;
            }
            ProductReview newreview = new ProductReview(helper.generateUniqueId(), helper.username(), Integer.parseInt(rating), new Date(), reviewtext);
            reviews.add(0, newreview);
        }
        if (reviews==null||reviews.size()==0) {
            content += "<h3>0 Comment</h3>";
            content += "<hr style=\"border-top: dotted 1px;\" />";
        } else {
            content += "<h3>"+reviews.size()+" Comments</h3>";
            content += "<hr style=\"border-top: dotted 1px;\" />";
            for(ProductReview review : reviews){
                content += "<table cellspacing='0'>";
                content += "<tr><td>"+review.getUserName()+"</td><td>"+helper.formateDate(review.getReviewDate())+"</td></tr>";
                content += "<tr><td>Rating:</td><td>"+review.getRating()+"</td></tr>";
                content += "<tr><td>Comment:</td><td>"+review.getReviewText()+"</td></tr>";
                content += "<tr><td colspan=\"2\"></td></tr>";
                content += "</table>";
                content += "<hr style=\"border-top: dotted 1px;\" />";
            }           
        }       
        
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_HOME);
        String template = helper.getTemplate("review.html");
        if (product!=null) {
            template = template.replace("$productkey$", productkey)
                               .replace("$productname$", product.getName())
                               .replace("$image$", product.getImage())
                               .replace("$reviewlist$", content);
        }
        helper.prepareContent(template);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }   
}
