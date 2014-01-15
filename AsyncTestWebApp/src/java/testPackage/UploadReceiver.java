/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testPackage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.netbeans.enterprise.bpel.asynchronoussampleschemanamespace.TypeB;

/**
 *
 * @author Install
 */
public class UploadReceiver extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                String id = null;

                DiskFileItemFactory fif = new DiskFileItemFactory();
                fif.setSizeThreshold(Integer.MAX_VALUE);
                
                ServletFileUpload upload = new ServletFileUpload(fif);
                upload.setFileSizeMax(1024 * 4000); //4MB
                List<FileItem> fileItems;

                fileItems = upload.parseRequest(request);

                byte[] pdfData = null;
                for (FileItem fItem : fileItems) {
                    if (fItem.isFormField()) {
                        Logger.getLogger(UploadReceiver.class.getName()).log(Level.SEVERE, fItem.getFieldName() + ": " + fItem.getString());

                        if (fItem.getFieldName().equalsIgnoreCase("ID"))
                            id = fItem.getString();
                    } else {

                    pdfData = fItem.get();
                    }
                }
                if (id == null) throw new ServletException("ID missing in Upload Data!");
                if (pdfData == null) throw new ServletException("Pdf missing in Upload Data!");
                TypeB item = HumanService.getItem(id);
                if (item != null) item.setPdfData(pdfData);
                HumanService.finishID(id);
                try {
                   while (HumanService.getItem(id) != null)
                        Thread.sleep(100);
                } catch (Exception ex) {

                }
            }
        } catch (FileUploadException ex) {
            Logger.getLogger(UploadReceiver.class.getName()).log(Level.SEVERE, null, ex);
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
