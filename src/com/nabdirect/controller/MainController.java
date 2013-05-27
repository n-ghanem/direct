package com.nabdirect.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@RequestMapping("/integration")
	public ModelAndView helloAppDirect() {
		return new ModelAndView("integration", "neb", "Welcome to nabdirect");
	}
}
