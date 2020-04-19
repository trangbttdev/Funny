/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangbtt.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trangbtt.daos.ArticleDAO;
import trangbtt.dtos.ArticleDTO;
import trangbtt.dtos.ArticleErrorObject;
import trangbtt.dtos.LoadPage;

/**
 *
 * @author trang
 */
public class SearchTitleStatusController extends HttpServlet {

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
        try {
            String title = request.getParameter("txtSearchTitle").trim();
            String status = request.getParameter("txtStatus").trim();
            String sCurrentPage = request.getParameter("txtPage");
            int pageTotal = 0;
            ArticleErrorObject err = new ArticleErrorObject();
            ArticleDAO dao = new ArticleDAO();
            LoadPage loadPage = new LoadPage();
            int iCurrentPage = Integer.parseInt(sCurrentPage);

            if (!title.equals("")) {
                if (status.equals("ALL")) {
                    List<ArticleDTO> result = dao.searchActicleByTitleStatusAll(title, iCurrentPage, 20);
                    if (result == null) {
                        request.setAttribute("NLIST", "No content list");
                    }
                    loadPage.setListActicle(result);
                    int count = dao.countSearchTotalPageAdminAll(title); 
                    if (count % 20 == 0) { 
                        pageTotal = count / 20;
                        loadPage.setTotalPage(pageTotal);
                    } else { 
                        pageTotal = (count / 20) + 1;
                        loadPage.setTotalPage(pageTotal);
                    }
                    request.setAttribute("LOADPAGE", loadPage);
                }
                if (status.equals("New") || status.equals("Delete") || status.equals("Active")) {
                    List<ArticleDTO> result = dao.searchActicleByTitleStatus(title, status, iCurrentPage, 20);
                    if (result == null) {
                        request.setAttribute("NLIST", "No content list");
                    }
                    loadPage.setListActicle(result);
                    int count = dao.countSearchTotalPageAdmin(title, status); 
                    if (count % 20 == 0) {
                        pageTotal = count / 20;
                        loadPage.setTotalPage(pageTotal);
                    } else { 
                        pageTotal = (count / 20) + 1;
                        loadPage.setTotalPage(pageTotal);
                    }
                    request.setAttribute("LOADPAGE", loadPage);
                }

            } else {
                err.setTitleError("Title can't be blank");
                request.setAttribute("INVALID", err);
            }

        } catch (Exception e) {
            log("ERROR at SearchTitleStatusController" + e.getMessage());
        } finally {
            request.getRequestDispatcher("searchByTS.jsp").forward(request, response);
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
