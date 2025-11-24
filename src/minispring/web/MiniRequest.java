package minispring.web;

import java.util.HashMap;
import java.util.Map;

public class MiniRequest {

    private String method;
    private String path;
    private Map<String, String> queryParams = new HashMap<>();

    public MiniRequest(String method, String fullPath) {
        
    	this.method = method;
        parsePathAndQuery(fullPath);
    }

    private void parsePathAndQuery(String fullPath) {

        if (!fullPath.contains("?")) {
            this.path = fullPath;
            return;
        }


        String[] parts = fullPath.split("\\?", 2);

        this.path = parts[0];   
        String query = parts[1];   

        for (String pair : query.split("&")) {
            if (!pair.contains("=")) continue;
            String[] kv = pair.split("=", 2);
            queryParams.put(kv[0], kv[1]);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getParam(String key) {
        return queryParams.get(key);
    }
}
