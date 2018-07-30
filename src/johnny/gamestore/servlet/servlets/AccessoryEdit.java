package johnny.gamestore.servlet.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import johnny.gamestore.servlet.beans.Accessory;
import johnny.gamestore.servlet.beans.Console;
import johnny.gamestore.servlet.data.UserHashMap;

@WebServlet("/AccessoryEdit")
public class AccessoryEdit extends HttpServlet {
    private String error_msg = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        error_msg = "";
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayAccessory(request, response, pw, false);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        error_msg = "";
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Helper helper = new Helper(request, pw);

        String manufacturer = request.getParameter("manufacturer");
        String console = request.getParameter("console");
        String accessory = request.getParameter("accessory");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String image = request.getParameter("image");
        String retailer = request.getParameter("retailer");
        String condition = request.getParameter("condition");
        String discount = request.getParameter("discount");

        if(manufacturer == null || console == null || accessory == null){
            error_msg = "Console can't be empty!";
        }else if(name == null){
            error_msg = "Name can't be empty!";
        }else if(price == null){
            error_msg = "Price can't be empty!";
        }else if(image == null){
            error_msg = "Image can't be empty!";
        }else if(retailer == null){
            error_msg = "Retailer can't be empty!";
        }else if(condition == null){
            error_msg = "Condition can't be empty!";
        }else if(discount == null){
            error_msg = "Discount can't be empty!";
        }

        double dprice = 0.0;
        if (error_msg.isEmpty()) {
            try {
                dprice = Double.parseDouble(price);
            } catch (NumberFormatException nfe) {
                error_msg = "Price must be number!";
            }
        }
        double ddiscount = 0.0;
        if (error_msg.isEmpty()) {
            try {
                ddiscount = Double.parseDouble(discount);
            } catch (NumberFormatException nfe) {
                error_msg = "Diccount must be number!";
            }
            if (error_msg.isEmpty() && (ddiscount < 0 || ddiscount > 100)) {
                error_msg = "Diccount must be between 0 and 100!";
            }
        }

        System.out.println(0);
        if (!error_msg.isEmpty()) {
            System.out.println(10);
            displayAccessory(request, response, pw, true);
            return;
        }

        Console conobj= helper.getConsole(manufacturer, console);
        if (conobj == null) {
            error_msg = "No such console ["+console+"] found!";
        } else{
            HashMap<String, Accessory> hm = conobj.getAccessories();
            if(hm==null||!hm.containsKey(accessory)) {
                error_msg = "Accessory ["+name+"] does not exist!";
            } else {
                Accessory accobj = hm.get(accessory);
                accobj.setPrice(dprice);
                accobj.setImage(image);
                accobj.setRetailer(retailer);
                accobj.setCondition(condition);
                accobj.setDiscount(ddiscount);
                error_msg = "Accessory ["+name+"] is updated!";
            }
        }

        displayAccessory(request, response, pw, true);
    }

    protected void displayAccessory(HttpServletRequest request,
                    HttpServletResponse response, PrintWriter pw, boolean error)
                    throws ServletException, IOException {

        Helper helper = new Helper(request,pw);
        if(!helper.isLoggedin()){
            HttpSession session = request.getSession(true);
            session.setAttribute(helper.SESSION_LOGIN_MSG, "Please login first!");
            response.sendRedirect("Login");
            return;
        }
        String usertype = helper.usertype();
        String errmsg = "";
        if (usertype==null || !usertype.equals(UserHashMap.CONST_TYPE_STOREMANAGER_LOWER)) {
            errmsg = "You have no authorization to manage accessary!";
        }
        String manufacturer = request.getParameter("manufacturer");
        String console = request.getParameter("console");
        String accessory = request.getParameter("accessory");
        String facconsole = "";

        if (manufacturer==null||console==null||accessory==null) {
            errmsg = "<h3 style='color:red'>Invalid Paramters!</h3>";
        } else {
            facconsole = manufacturer+console;
        }
        String[][] arr = new String[6][2];
        arr[0][0] = "microsoftxboxone";
        arr[0][1] = "Microsoft-Xbox One";
        arr[1][0] = "microsoftxbox360";
        arr[1][1] = "Microsoft-Xbox 360";
        arr[2][0] = "sonyps3";
        arr[2][1] = "Sony-PS3";
        arr[3][0] = "sonyps4";
        arr[3][1] = "Sony-PS4";
        arr[4][0] = "nintendowii";
        arr[4][1] = "Nintendo-Wii";
        arr[5][0] = "nintendowiiu";
        arr[5][1] = "Nintendo-WiiU";
        String strOptionlist = "<tr><td><h5>Console:</h5></td><td><select name='facconsole' class='input' disabled>";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0].equals(facconsole.toLowerCase())) {
                strOptionlist += "<option value='"+arr[i][0]+"' selected>"+arr[i][1]+"</option>";
            } else {
                strOptionlist += "<option value='"+arr[i][0]+"'>"+arr[i][1]+"</option>";
            }
        }
        strOptionlist += "</select></td></tr>";

        Accessory assobj = helper.getAccessory(manufacturer, console, accessory);
        helper.prepareLayout();
        helper.prepareHeader();
        helper.prepareMenu(helper.CURRENT_PAGE_ACCMNG);
        if (error) {
            errmsg = "<h3 style='color:red'>"+error_msg+"</h3>";
        }
        String template = helper.getTemplate("accessory_edit.html");
        template = template.replace("$errmsg$", errmsg);
        template = template.replace("$option$", strOptionlist);
        if (assobj != null) {
            template = template.replace("$manufacturer$", manufacturer)
                               .replace("$console$", console)
                               .replace("$accessory$", accessory)
                               .replace("$name$", assobj.getName())
                               .replace("$price$", String.valueOf(assobj.getPrice()))
                               .replace("$image$", assobj.getImage())
                               .replace("$retailer$", assobj.getRetailer())
                               .replace("$condition$", assobj.getCondition())
                               .replace("$discount$", String.valueOf(assobj.getDiscount()));
        }
        helper.prepareContent(template);
        helper.prepareSideBar();
        helper.prepareFooter();
        helper.printHtml();
    }
}
