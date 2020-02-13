import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import exampler.console.MultiQueueController;
import exampler.console.SingleQueueController;
import queue.logic.Customer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Handler {
    HttpServer server;


    public Handler(HttpServer server, MultiQueueController multiQueueController) {
        this.server = server;
        server.createContext("/api/addNewQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());

            multiQueueController.addQueue(x.get("queueName"));

            String respText = "New queue " + x.get("queueName") + " has been created";
            endResponse(exchange, respText);
        }));

        server.createContext("/api/getAllQueues", (exchange -> {
            String respText = "";
            for (String name : multiQueueController.getQueuesNames()) respText = respText.concat(name.concat("\n"));
            endResponse(exchange, respText);
        }));

        server.createContext("/api/addToQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());

            SingleQueueController singleQueueController = multiQueueController.getQueueController(x.get("queueName"));

            String respText;
            try {
                singleQueueController.addCustomer(new Customer(x.get("phoneNumber")));
                respText = "Success";
            } catch (NullPointerException e) {
                respText = "No such queue";
            }
            endResponse(exchange, respText);
        }));

        server.createContext("/api/deleteFromQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());

            SingleQueueController singleQueueController = multiQueueController.getQueueController(x.get("queueName"));

            String respText;
            try {
                singleQueueController.removeCustomer(new Customer(x.get("phoneNumber")));
                respText = "Deleted";
            } catch (NullPointerException e) {
                respText = "No such queue";
            } catch (NoSuchElementException e) {
                respText = e.getMessage();
            }
            endResponse(exchange, respText);

        }));
        server.createContext("/api/getQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            SingleQueueController singleQueueController = multiQueueController.getQueueController(x.get("queueName"));
            String respText;
            try {
                respText = singleQueueController.printQueue();
            } catch (NullPointerException e) {
                respText = "No such queue";
            }
            endResponse(exchange, respText);
        }));
        server.setExecutor(null); // creates a default executor
        server.start();
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
