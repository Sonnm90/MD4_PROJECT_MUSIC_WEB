package md4.md4_project.controller;

import md4.md4_project.model.Singer;
import md4.md4_project.service.singer.ISingerService;
import md4.md4_project.service.singer.SingerServiceIMPL;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/singers")
public class SingerController extends HttpServlet {
    ISingerService singerService = new SingerServiceIMPL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        System.out.println("action o DoGet--> "+ action);
        switch (action){

            case "singer":
                showListSinger(request, response);
                break;
            case "create":
                showFormCreate(request, response);
                break;
            case "delete":
                deleteSinger(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action){
            case "create":
                createSinger(request, response);
                break;
            case "update":
                updateSinger(request, response);
                break;
        }
    }

    public void showListSinger(HttpServletRequest request, HttpServletResponse response) {
        List<Singer> singerList = singerService.findAll();
        request.setAttribute("singerList", singerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/admin/singerAdmin.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSinger(HttpServletRequest request, HttpServletResponse response) {

        int id = Integer.parseInt(request.getParameter("id"));
        singerService.deleteById(id);
        showListSinger(request, response);
    }

    public void showFormCreate(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/content/singer/create.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSinger(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String avatar = request.getParameter("avatar");
        singerService.save(name, avatar);
        showListSinger(request, response);
    }
    public void updateSinger(HttpServletRequest request, HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String avatar = request.getParameter("avatar");
        singerService.edit(id, name, avatar);
        showListSinger(request, response);
    }
}
