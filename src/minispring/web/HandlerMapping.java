package minispring.web;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import minispring.annotations.Controller;
import minispring.annotations.Get;
import minispring.annotations.Post;

public class HandlerMapping {

	private final Map<String, HandlerMethod> handlerMap = new HashMap<>();
	
	public HandlerMapping(Map<String, Object> beans) {
		initHandlerMapping(beans);
	}
	
	public HandlerMethod getHandler(String url) {
		return handlerMap.get(url);
	}
	
	private void initHandlerMapping(Map<String, Object> beans) {
		
		for(Object bean : beans.values()) {
			
			Class<?> clazz = bean.getClass();
			
			if(!clazz.isAnnotationPresent(Controller.class)) {
				continue;
			}
			
			for(Method method : clazz.getDeclaredMethods()) {
				
				if(method.isAnnotationPresent(Get.class)) {
					String url = method.getAnnotation(Get.class).value();
					handlerMap.put(url, new HandlerMethod(bean, method));
				}
				
				if(method.isAnnotationPresent(Post.class)) {
					String url = method.getAnnotation(Post.class).value();
					handlerMap.put(url, new HandlerMethod(bean, method));
				}
			}
		}
		
	   System.out.println("=== HandlerMapping 등록 완료 ===");
        handlerMap.forEach((url, hm) ->
            System.out.println(url + " → " 
            + hm.getMethod().getDeclaringClass().getSimpleName() 
            + "." + hm.getMethod().getName()));
	}
}
