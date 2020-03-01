package org.mycode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontController {
    @GetMapping("/")
    public ModelAndView viewIndexPage() {
        return new ModelAndView("index");
    }

    @GetMapping("documentation")
    public ModelAndView viewDocPage() {
        return new ModelAndView("doc");
    }
}
