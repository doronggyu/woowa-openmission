package minispring.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

	private final Map<String, Object> beans = new HashMap<>();
	
	// Bean 후보(Class 목록들)을 받아 실제 Bean 생성
	public void registerBeans(List<Class<?>> candidates) {
		
		for(Class<?> clazz : candidates) {
			registerBean(clazz);
		}
	}
	
	// 단일 Bean 등록
	public void registerBean(Class<?> clazz) {
		
		try {
			// 1) Bean 생성규칙에 따라 이름 만들기
			String beanName = toLowerCamel(clazz.getSimpleName());
			
			// 2) 객체 생성
			Object instance = clazz.getDeclaredConstructor().newInstance();
			
			// 3) 리스트에 저장
			beans.put(beanName, instance);
		} catch ( Exception e) {
			throw new RuntimeException("Bean 생성 실패" + clazz.getName(), e);
		}
	}

	// Bean생성 규칙에 따라 이름 변환
	public String toLowerCamel(String name) {
		
		return Character.toLowerCase(name.charAt(0)) + name.substring(1);
	}
	
	// 이름으로 Bean 가져오기
	public Object getBean(String name) {
		
		return beans.get(name);
	}
	
	// 타입으로 Bean 가져오기
	public <T> T getBean(Class<T> type) {
		 
		 return (T) beans.values().stream()
	                .filter(obj -> type.isAssignableFrom(obj.getClass()))
	                .findFirst()
	                .orElseThrow(() ->
	                        new RuntimeException("해당 타입의 Bean이 존재하지 않습니다: " + type.getName())
	                );
	}
	
	public Map<String, Object> getAllBeans() {
		
		return beans;
	}
	
	
}
