package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Client {
        public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Input your math equation:");
        String mathEquation = new Scanner(System.in).nextLine();

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(
                "http://localhost:8080/calculate?equation=%s".formatted(mathEquation))).GET().build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        System.out.println(body);
    }

}
