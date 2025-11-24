package minispring.app.controller;

import minispring.annotations.Autowired;
import minispring.annotations.Controller;
import minispring.annotations.Get;
import minispring.annotations.Post;
import minispring.app.service.MemberService;
import minispring.web.MiniRequest;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Post("/signup")
	public String singup() {
		
		return "signup";
	}
	
	@Get("/login")
	public String login() {
		
		memberService.login();
		
		return "login";
	}
	
	@Get("/loginRequest")
	public String loginRquest(MiniRequest req) {
		
		System.out.println(req.getParam("username"));
		System.out.println(req.getParam("password"));
		
		return "index";
	}
}
