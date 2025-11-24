package minispring;

import minispring.context.ApplicationContext;
import minispring.web.DispatcherServlet;
import minispring.web.HandlerMapping;
import minispring.web.MiniWebServer;

public class MiniSpringApp {
	public static void main(String[] args) throws Exception {
		
		ApplicationContext context = new ApplicationContext("minispring.app");
		
		HandlerMapping handlerMapping = new HandlerMapping(context.getAllBeans());
		
		DispatcherServlet dispatcher = new DispatcherServlet(handlerMapping);
		
		MiniWebServer server = new MiniWebServer(dispatcher);
		
		server.start(8080);
	}
}
