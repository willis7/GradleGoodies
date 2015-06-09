package org.acme.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloController {

	// welcome to reston
	@RequestMapping(value = "/helloWorld")
    public ModelAndView helloWorld() {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("helloWorld");
        mav.addObject("message", "Hello Spring 3 from Gradle World!");
        return mav;
    }
}
    