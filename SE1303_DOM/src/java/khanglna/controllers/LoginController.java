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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Novixous
 */
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String xmlFile = "/WEB-INF/studentAccounts.xml";
    private String fullname;
    private boolean found;
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");

            String realpath = getServletContext().getRealPath("/");
            String filePath = realpath + xmlFile;
            fullname = "";
            found = false;
            Document doc = XMLUtils.parseFileToDom(filePath);
            checkLogin(doc, username, password);
            if (found) {
                url = SUCCESS;
            }
            System.out.println("fullname = " + fullname);
            System.out.println("found = " + found);
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

    private void checkLogin(Node node, String username, String password) {
        if (node == null || found) {
            return;
        }

        if (node.getNodeName().equals("student")) {
            String id = node.getAttributes().getNamedItem("id").getNodeValue();
            if (username.equals(id)) {
                NodeList childrenStudent = node.getChildNodes();
                for (int i = 0; i < childrenStudent.getLength(); i++) {
                    Node tmp = childrenStudent.item(i);
                    if (tmp.getNodeName().equals("lastname")) {
                        fullname = tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("middlename")) {
                        fullname = fullname + " " + tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("firstname")) {
                        fullname = fullname + " " + tmp.getTextContent().trim();
                    } else if (tmp.getNodeName().equals("password")) {
                        String pword = tmp.getTextContent().trim();
                        if (!pword.equals(password)) {
                            break;
                        }
                    } else if (tmp.getNodeName().equals("status")) {
                        String status = tmp.getTextContent().trim();
                        if (!status.equals("dropout")) {
                            found = true;
                            return;
                        }
                    }
                }
            }
        }

        NodeList children = node.getChildNodes();
        int count = 0;
        while (count < children.getLength()) {
            checkLogin(children.item(count++), username, password);
        }
    }

}
