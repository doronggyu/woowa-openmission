package minispring.context;

import minispring.annotations.Component;
import minispring.annotations.Controller;
import minispring.annotations.Service;

import java.util.List;
import java.util.stream.Collectors;


public class AnnotationFilter {
	
	public List<Class<?>> filterForComponents(List<Class<?>> classes){
		
		return classes.stream()
				.filter(clazz -> 
						clazz.isAnnotationPresent(Component.class) ||
						clazz.isAnnotationPresent(Controller.class) ||
						clazz.isAnnotationPresent(Service.class)
				)
				.collect(Collectors.toList());
	}
}
