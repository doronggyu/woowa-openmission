package minispring.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewResolver {


    private static final String BASE_PATH = "src/templates/";

    public String resolveView(String viewName) {

        if (viewName.endsWith(".html") == false) {
            viewName = viewName + ".html";
        }

        String fullPath = BASE_PATH + viewName;

        try {
            return new String(Files.readAllBytes(Paths.get(fullPath)), "UTF-8");
        } catch (Exception e) {
            return "<h1>View Not Found: " + fullPath + "</h1>";
        }
    }
}
