package org.mycode.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.exceptions.RepoStorageException;
import org.mycode.model.Account;
import org.mycode.model.Developer;
import org.mycode.model.Skill;
import org.mycode.service.AccountService;
import org.mycode.service.DeveloperService;
import org.mycode.service.Service;
import org.mycode.service.SkillService;
import org.mycode.service.visitors.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "MainServlet", urlPatterns = {"/api/v1/skills", "/api/v1/accounts", "/api/v1/developers"})
public class MainServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(MainServlet.class);
    private Service service;
    private Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("POST request to create");
        Class typeForJson = recognizeUrl(req);
        try {
            service.doService(new CreateVisitor(Optional.of(gson.fromJson(req.getReader(), typeForJson))));
            resp.setStatus(201);
        } catch (Exception e) {
            log.error("Cannot create entry", e);
            e.printStackTrace();
            if(e instanceof JsonSyntaxException){
                resp.sendError(400);
            } else if(e instanceof RepoStorageException){
                resp.sendError(409, "Cannot create entry, maybe this entry has already created");
            }
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("GET request to read");
        recognizeUrl(req);
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        try {
            if(req.getParameter("id")==null || !req.getParameter("id").matches("\\d+")){
                log.debug("Request to get all");
                ServiceVisitor visitor = new GetAllVisitor(Optional.empty());
                service.doService(visitor);
                visitor.getResultData().ifPresent(el -> writer.println(gson.toJson(el)));
                log.debug("Sand JSON response");
            } else {
                log.debug("Request to get by ID");
                ServiceVisitor visitor = new GetByIdVisitor(Optional.of(Long.parseLong(req.getParameter("id"))));
                service.doService(visitor);
                visitor.getResultData().ifPresent(el -> writer.println(gson.toJson(el)));
                log.debug("Sand JSON response");
            }
        } catch (Exception e) {
            log.error("Cannot read entry", e);
            e.printStackTrace();
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("PUT request to update");
        Class typeForJson = recognizeUrl(req);
        try {
            service.doService(new UpdateVisitor(Optional.of(gson.fromJson(req.getReader(), typeForJson))));
            resp.setStatus(204);
        } catch (Exception e) {
            log.error("Cannot update entry", e);
            e.printStackTrace();
            if(e instanceof JsonSyntaxException){
                resp.sendError(400);
            } else if(e instanceof NoSuchEntryException) {
                resp.sendError(409, "Cannot find entry");
            }
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("DELETE request to delete");
        recognizeUrl(req);
        try {
            if(req.getParameter("id")==null || !req.getParameter("id").matches("\\d+")){
                log.warn("DELETE request gives invalid id parameter");
                resp.sendError(400, "Invalid parameter id");
            } else {
                service.doService(new DeleteVisitor(Optional.of(Long.parseLong(req.getParameter("id")))));
                resp.setStatus(204);
            }
        } catch (Exception e) {
            log.error("Cannot delete entry", e);
            e.printStackTrace();
            if (e instanceof NoSuchEntryException){
                resp.sendError(409, "Cannot find entry");
            }
        }
    }
    private Class recognizeUrl(HttpServletRequest req){
        switch (req.getServletPath()){
            case "/api/v1/skills":
                service = new SkillService();
                return Skill.class;
            case "/api/v1/accounts":
                service = new AccountService();
                return Account.class;
            case "/api/v1/developers":
                service = new DeveloperService();
                return Developer.class;
        }
        return Object.class;
    }
}
