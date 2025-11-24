package minispring.context;

import java.util.List;
import java.util.Map;

import minispring.web.HandlerMapping;

public class ApplicationContext {

	private final ComponentScanner scanner = new ComponentScanner();
	private final AnnotationFilter filter = new AnnotationFilter();
	private final BeanFactory beanFactory = new BeanFactory();
	private final DIProcessor diProcessor = new DIProcessor();
	private final HandlerMapping handlerMapping;
	
    public ApplicationContext(String basePackage) {

        // 1) 스캔
        List<Class<?>> allClasses = scanner.scan(basePackage);

        // 2) 어노테이션 붙은 클래스만 필터링
        List<Class<?>> beanCandidates = filter.filterForComponents(allClasses);

        System.out.println("=== Bean 후보 ===");
        beanCandidates.forEach(c -> System.out.println("- " + c.getName()));

        // 3) BeanFactory 로 Bean 생성 + 등록
        beanFactory.registerBeans(beanCandidates);

        // 4) DI처리
        diProcessor.doDI(beanFactory.getAllBeans());
        
        // 5) HandlerMapping 초기화
        handlerMapping = new HandlerMapping(beanFactory.getAllBeans());
    }
    
    public Map<String, Object> getAllBeans() {
        return beanFactory.getAllBeans();
    }
    
    public HandlerMapping getHandlerMapping() {
        return handlerMapping;
    }
}
