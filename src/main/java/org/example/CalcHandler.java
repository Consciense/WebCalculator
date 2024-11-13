package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CalcHandler implements HttpHandler, Calculator {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String request = exchange.getRequestURI().getQuery();

        Map<String, String> parameters = getParameters(request);
        String example = parameters.get("equation");
        System.out.println("equation" + example);
        String message = "Answer is: %s".formatted(Calculator.calculateEquation(new StringBuilder(example)));

        exchange.sendResponseHeaders(200, message.length());
        exchange.getResponseBody().write(message.getBytes());
        exchange.getResponseBody().close();
    }

    public Map<String, String> getParameters(String query) {
        Map<String, String> equation = new HashMap<>();
        String[] split = query.split("=");
        equation.put(split[0], split[1]);
        return equation;
    }
}
