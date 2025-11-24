package minispring.context;

import java.lang.reflect.Field;
import java.util.Map;

import minispring.annotations.Autowired;

public class DIProcessor {

	public void doDI(Map<String, Object> beans) {
		
		// BeanFactory에 등록된 모든 Bean 순회
		for(Object bean : beans.values()) {
			injectDependencies(bean, beans);
		}
	}
	
	private void injectDependencies(Object bean, Map<String, Object> beans) {
		
		Class<?> clazz = bean.getClass();
		
		// Bean 내부 필드 전체 스캔
		for(Field field : clazz.getDeclaredFields()) {
			
			// @Autowired 붙은 필드만 처리
			if(field.isAnnotationPresent(Autowired.class)) {
				
				Class<?> dependencyType = field.getType();
				
				// BeanFactory에서 해당 타입의 Bean 찾기
				Object dependency = findBeanByType(beans, dependencyType);
				
				if(dependency == null) {
					throw new RuntimeException("DI 검색 실패 " + dependencyType.getName());
				}
				
				try {
					field.setAccessible(true); //private 필드도 검색 및 주입 허용
					field.set(bean, dependency);
				} catch(IllegalAccessException e) {
					throw new RuntimeException("DI 실패", e);
				}
			}
		}
	}
	
	private Object findBeanByType(Map<String, Object> beans, Class<?> type) {
		
		for(Object bean : beans.values()) {
			if(type.isAssignableFrom(bean.getClass())) {
				return bean;
			}
		}
		
		return null;
	}
}
