package org.mycode.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class FrontController {
    @Value("classpath:documentation/doc.json")
    private Resource docFile;

    @GetMapping("/")
    public ModelAndView viewIndexPage() {
        return new ModelAndView("index");
    }

    @GetMapping("documentation")
    public ModelAndView viewDocPage() {
        return new ModelAndView("doc");
    }

    @GetMapping("logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @GetMapping("health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("up", HttpStatus.OK);
    }

    @GetMapping("doc")
    public ResponseEntity<String> viewDocJSON() {
        try (InputStream fr = docFile.getInputStream()) {
            StringBuilder jsonStr = new StringBuilder();
            int c;
            while ((c = fr.read()) != -1) {
                jsonStr.append((char) c);
            }
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonStr.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("no-doc", HttpStatus.NOT_FOUND);
    }
}
