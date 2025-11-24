package minispring.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;


public class MiniWebServer {

	private final DispatcherServlet dispatcher;
	
	public MiniWebServer(DispatcherServlet dispatcher) {
		
		this.dispatcher = dispatcher;
	}
	

    public void start(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            // 모든 요청 처리
            server.createContext("/", this::handleRequest);

            server.setExecutor(null);
            server.start();

            System.out.println("🚀 MiniSpring WebServer started on port " + port);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleRequest(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();            
        String fullPath = exchange.getRequestURI().toString();     

        System.out.println("\n=== 요청 수신 ===");
        System.out.println(method + " " + fullPath);

        // ★ MiniRequest 생성 (GET 파라미터 파싱 포함)
        MiniRequest req = new MiniRequest(method, fullPath);

        // ★ DispatcherServlet에 전달
        Object result = dispatcher.handleRequest(req);

        // ViewResolver에서 받은 HTML 문자열 or plain text
        String response = (result != null) ? result.toString() : "";

        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes("UTF-8"));
        os.close();
    }
}
