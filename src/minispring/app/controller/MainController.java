package minispring.app.controller;

import minispring.annotations.Controller;
import minispring.annotations.Get;

@Controller
public class MainController {

	@Get("/")
	public String mainPage() {
		
		return "index";
	}
}
