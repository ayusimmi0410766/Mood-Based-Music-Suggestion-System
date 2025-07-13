import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class MoodServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Serve static files
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/"))
                path = "/index.html";
            File file = new File("public" + path);
            if (file.exists()) {
                byte[] data = Files.readAllBytes(file.toPath());
                exchange.sendResponseHeaders(200, data.length);
                exchange.getResponseBody().write(data);
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
            exchange.close();
        });

        // Handle mood suggestion
        server.createContext("/suggest", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String form = new String(exchange.getRequestBody().readAllBytes(), "UTF-8");
                Map<String, String> data = parseForm(form);

                String moodParam = data.get("mood");
                System.out.println("Mood selected: " + moodParam); // Debug print

                Mood mood = new Mood(moodParam);

                String response = "<html><body style='text-align:center; font-family:sans-serif'>" +
                        mood.toHTML() +
                        "<br><br><a href='/'>Try Another Mood</a></body></html>";

                byte[] bytes = response.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);
            }
            exchange.close();
        });

        System.out.println("🚀 Mood Server started at http://localhost:8080");
        server.setExecutor(null);
        server.start();
    }

    private static Map<String, String> parseForm(String form) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        for (String pair : form.split("&")) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                map.put(URLDecoder.decode(parts[0], "UTF-8"),
                        URLDecoder.decode(parts[1], "UTF-8"));
            }
        }
        return map;
    }
}
