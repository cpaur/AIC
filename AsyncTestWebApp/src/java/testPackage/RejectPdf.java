/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.netbeans.enterprise.bpel.asynchronoussampleschemanamespace.TypeB;

/**
 *
 * @author Install
 */
public class RejectPdf extends HttpServlet {

    private static byte[] rejectData = null;

    private static byte[] getRejectData(ServletContext ctx) throws Exception {
        if (rejectData == null) {
            FileInputStream rdr = null;
            try {
                String fullpath = ctx.getRealPath("Rejected.pdf");
                File rejectedPdf = new File(fullpath);
                rejectData = new byte[(int) rejectedPdf.length()];
                rdr = new FileInputStream(rejectedPdf);
                rdr.read(rejectData);
            } finally {
                try {
                    if (rdr != null) rdr.close();
                } catch (IOException ex) {
                    Logger.getLogger(RejectPdf.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return rejectData;
    }

    private final static String STR_REJECTED = "The report was rejected.";

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("ID");
        TypeB item = HumanService.getItem(id);
        if (item != null) {
        try {
            byte[] data = getRejectData(getServletContext());
            if (data != null) {item.setPdfData(data);}
            else {
                item.setPdfData(null);
                item.setFaultInfo(STR_REJECTED);
            }
        } catch(Exception ex) {
            Logger.getLogger(RejectPdf.class.getName()).log(Level.SEVERE, null, ex);
            item.setPdfData(null);
            item.setFaultInfo(STR_REJECTED);
        }
        boolean didFinish =  HumanService.finishID(id);
        }
        try {
            while (HumanService.getItem(id) != null)
                Thread.sleep(100);
        } catch (Exception ex) {

        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
