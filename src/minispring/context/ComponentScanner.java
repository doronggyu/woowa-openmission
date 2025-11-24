package minispring.context;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ComponentScanner {

	public List<Class<?>> scan(String basePackage){

		List<Class<?>> result = new ArrayList<>();
		
		// 1) 패키지 명을 파일 경로로 변환
		String path = basePackage.replace('.', '/');
		
		// 2) ClassLoader로 경로 찾기
		URL url = Thread.currentThread()
				.getContextClassLoader()
				.getResource(path);
		
		if(url == null) {
			System.out.println("스캔 경로 오류");
			return result;
		}
		
		// 3) 실제 디렉토리
		File directory = new File(url.getFile());
		
		if(!directory.exists()) {
			return result;
		}
		
		// 4) 내부 파일들 탐색 시작
		scanDirectory(basePackage, directory, result);
		
		return result;
	}
	
	private void scanDirectory(String basePackage, File directory, List<Class<?>> result) {
		
		for(File file : directory.listFiles()) {
			
			if(file.isDirectory()) {
				// 하위 패키지 재귀 탐색
				scanDirectory(basePackage + "." + file.getName(), file, result);
			} 
			else if(file.getName().endsWith(".class")) {
				//.class 파일 -> 클래스명으로 변환
				String className = basePackage + "." + file.getName().replace(".class", "");
			
				try {
					
					Class<?> clazz = Class.forName(className);
					result.add(clazz);
					
				} catch (ClassNotFoundException e) {
					
					throw new RuntimeException("클래스 로딩 실패 : " + className, e);
				}
			}
		}
		
		
	}
}
