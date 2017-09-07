package com.pfowenli.springwebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public ModelAndView showView() {
		
		System.out.println("showView() has been called.");

		ModelAndView modelAndView = new ModelAndView("view");
		
		return modelAndView;
	}

}
