import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import queue.logic.Customer;
import queue.logic.EngineeredQueue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

class Application {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        List<EngineeredQueue> queueList = new LinkedList<>();
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/api/addNewQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());

            queueList.add(new EngineeredQueue(x.get("queueName")));

            String respText = "New queue " + x.get("queueName") + " has been created";
            endResponse(exchange, respText);
        }));

        server.createContext("/api/getAllQueues", (exchange -> {
            String respText = "";
            for (EngineeredQueue queue : queueList) respText = respText.concat(queue.getQueueName().concat("\n"));
            endResponse(exchange, respText);
        }));

        server.createContext("/api/addToQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            EngineeredQueue queue = findQueue(x.get("queueName"), queueList);
            String respText;
            try {
                queue.addCustomer(new Customer(x.get("phoneNumber")));
                respText = "Success";
            } catch (NullPointerException e) {
                respText = "No such queue";
            }
            endResponse(exchange, respText);
        }));
        server.createContext("/api/deleteFromQueue", (exchange -> {
            Map<String, String> x = splitQuery(exchange.getRequestURI().getRawQuery());
            EngineeredQueue queue = findQueue(x.get("queueName"), queueList);
            String respText;
            try {
                queue.deleteCustomer(x.get("phoneNumber"));
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
            EngineeredQueue queue = findQueue(x.get("queueName"), queueList);
            String respText;
            try {
                respText = queue.printQueue();
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

    public static EngineeredQueue findQueue(String name, List<EngineeredQueue> queues) {
        for (EngineeredQueue queue : queues) {
            if (queue.getQueueName().equals(name)) return queue;
        }

        return null;
    }
}