package minispring.web;

import java.lang.reflect.Method;

public class DispatcherServlet {

    private final HandlerMapping handlerMapping;
    private final ViewResolver viewResolver = new ViewResolver();

    public DispatcherServlet(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public Object handleRequest(MiniRequest req) {

        String url = req.getPath();
        System.out.println("→ 요청 URL 매핑: " + url);

        HandlerMethod handler = handlerMapping.getHandler(url);

        if (handler == null) {
            return "<h1>404 Not Found</h1>";
        }

        try {
            Object controller = handler.getBean();
            Object result = handler.getMethod().invoke(controller);

            // view 이름이면 html 파일 찾아서 반환
            if (result instanceof String) {
                return viewResolver.resolveView((String) result);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "<h1>500 Internal Server Error</h1>";
        }
    }
}
