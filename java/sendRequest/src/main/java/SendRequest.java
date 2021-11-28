import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class SendRequest {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ParseException {

        String APPWRITE_FUNCTION_DATA = System.getenv("APPWRITE_FUNCTION_DATA");
        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(APPWRITE_FUNCTION_DATA);
        String url = (String) data.get("url");

        List<String> headers = new ArrayList<>();
        JSONObject headersJSON = (JSONObject) data.get("headers");
        if(!headersJSON.isEmpty()) {
            for (Object key : headersJSON.keySet()) {
                headers.add((String) key);
                headers.add((String) headersJSON.get(key));
            }
        }

        String method = (String) data.get("method");
        String body = (String) data.get("body");

        if(url.equals(null)) {
            System.out.println("ERROR: Please provide an URL.");
        }
        if(method.equals(null)) {
            method = "GET";
        }

        HttpResponse<String> response;

        if(method.equalsIgnoreCase("get")) {
            response = get(url, headers);
        } else if (method.equalsIgnoreCase("post")){
            response = post(url, body, headers);
        } else if (method.equalsIgnoreCase("put")) {
            response = put(url, body, headers);
        } else if (method.equalsIgnoreCase("delete")) {
            response = delete(url, headers);
        } else {
            System.out.println("ERROR: Make sure you specify a valid HTTP method!");
            return;
        }

        if(response!=null) {
            System.out.println(response.body());
        }

    }

    private static HttpResponse<String> get(String url, List<String> headers) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(headers.toArray(String[]::new))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> post(String url, String body, List<String> headers) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(headers.toArray(String[]::new))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> put(String url, String body, List<String> headers) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(headers.toArray(String[]::new))
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static HttpResponse<String> delete(String url, List<String> headers) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(headers.toArray(String[]::new))
                .DELETE()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
