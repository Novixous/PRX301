/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khanglna.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khanglna.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Novixous
 */
public class RegisterController extends HttpServlet {

    private static final String xmlFile = "/WEB-INF/studentAccounts.xml";
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "index.html";

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
        String url = ERROR;
        try {
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + xmlFile;

            String id = request.getParameter("txtId");
            String txtClass = request.getParameter("txtClass");
            String lastname = request.getParameter("txtLastname");
            String firstname = request.getParameter("txtFirstname");
            String middleName = request.getParameter("txtMiddlename");
            String password = request.getParameter("txtPassword");
            String address = request.getParameter("txtAddress");
            String txtSex = request.getParameter("cboSex");
            Document doc = XMLUtils.parseFileToDom(filePath);
            if (doc != null) {
                Element student = doc.createElement("student");
                student.setAttribute("id", id);
                student.setAttribute("class", txtClass);

                Element lastnameE = doc.createElement("lastname");
                lastnameE.setTextContent(lastname);
                Element middlenameE = doc.createElement("middlename");
                middlenameE.setTextContent(middleName);
                Element firstnameE = doc.createElement("firstname");
                firstnameE.setTextContent(firstname);
                Element sexE = doc.createElement("sex");
                sexE.setTextContent(txtSex);
                Element passwordE = doc.createElement("password");
                passwordE.setTextContent(password);
                Element addressE = doc.createElement("address");
                addressE.setTextContent(address);
                Element statusE = doc.createElement("status");
                statusE.setTextContent("break");

                student.appendChild(lastnameE);
                student.appendChild(middlenameE);
                student.appendChild(firstnameE);
                student.appendChild(sexE);
                student.appendChild(passwordE);
                student.appendChild(addressE);
                student.appendChild(statusE);

                NodeList listStudent = doc.getElementsByTagName("students");
                if (listStudent != null) {
                    if (listStudent.getLength() > 0) {
                        listStudent.item(0).appendChild(student);
                        boolean check = XMLUtils.transformXMLToStreamResult(doc, filePath);
                        if (check) {
                            url = SUCCESS;
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

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
