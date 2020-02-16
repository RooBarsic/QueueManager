package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import logic.queue.QueuesBox;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class QueueHandler {
    HttpServer server;


    public QueueHandler(HttpServer server, QueuesBox queuesBox) {
        this.server = server;


        server.createContext("/api/addNewQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            String respText;
            if (queuesBox.addQueue(x.get("queueName"))) {
                respText = "New queue " + x.get("queueName") + " has been created";
            } else {
                respText = "Queue " + x.get("queueName") + " already exist.";
            }


            endResponse(exchange, respText);
        }));


        server.createContext("/api/deleteQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            String respText;
            if (queuesBox.removeQueue(x.get("queueName"))) {
                respText = "Queue " + x.get("queueName") + " deleted";
            } else {
                respText = "Queue " + x.get("queueName") + " does not exist.";
            }

            endResponse(exchange, respText);
        }));


    }

    public static void endResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        exchange.close();

    }

    public static Map<String, String> splitQuery(String query) {
        Map<String, String> params = new HashMap<>();
        System.out.println(query);
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }
        String[] keyval = query.split("&");
        for (String s : keyval
        ) {
            String[] kv = s.split("=");
            params.put(kv[0], kv[1]);
        }
        return params;
    }
}
