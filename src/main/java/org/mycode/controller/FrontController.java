package org.mycode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontController {
    @GetMapping(value = {"/"})
    public ModelAndView viewIndexPage(){
        return new ModelAndView("index");
    }
    @GetMapping(value = "/documentation")
    public ModelAndView viewDocPage(){
        return new ModelAndView("doc");
    }
}
