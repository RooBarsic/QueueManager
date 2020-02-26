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
            int respCode;

            if (exchange.getRequestMethod().equals("POST")) {
                if (queuesBox.addQueue(x.get("queueName"))) {
                    respCode = 201;
                    respText = "New queue " + x.get("queueName") + " has been created";
                } else {
                    respCode = 409;
                    respText = "Queue " + x.get("queueName") + " already exist.";
                }
            } else {
                respCode = 405;
                respText = "Use another method";
            }

            endResponse(exchange, respText, respCode);
        }));
//TODO Переписать на StringBuilder

        server.createContext("/api/deleteQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            String respText;
            int respCode;
            if (exchange.getRequestMethod().equals("DELETE")) {
                if (queuesBox.removeQueue(x.get("queueName"))) {
                    respText = "Queue " + x.get("queueName") + " deleted";
                    respCode = 200;
                } else {
                    respText = "Queue " + x.get("queueName") + " does not exist.";
                    respCode = 404;
                }
            } else {
                respCode = 405;
                respText = "Use another method";
            }
            endResponse(exchange, respText, respCode);
        }));


    }

    public static void endResponse(HttpExchange exchange, String response, int respCode) throws IOException {
        String encoding = "UTF-8";

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=" + encoding);

        exchange.sendResponseHeaders(respCode, response.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        exchange.close();
    }

    public static Map<String, String> splitQuery(String query) {
        Map<String, String> params = new HashMap<>();
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
