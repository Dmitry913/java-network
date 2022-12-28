package org.school.java.lesson3;

import com.sun.net.httpserver.HttpServer;
import org.school.java.lesson3.constants.HeaderName;
import org.school.java.lesson3.exceptions.InvalidLogin;
import org.school.java.lesson3.handlers.MyHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Lesson3 {
    private static final String HOST = "localhost";
    private static final Integer PORT = 8081;
    private static final String CONTEXT = "/login";
    private static final Integer THREAD_COUNT = 2;

    public static void main(String[] args) {
        runServer();
        HttpClient httpClient = upClient();
        System.out.println("Input: ");
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!"end".equals(scanner.nextLine())) {
            try {
                sendRequest(httpClient);
                System.out.println("I am here" + LocalTime.now());
            } catch (InvalidLogin invalidLogin) {
                System.out.println("Not valid login.Try again.");
            }
            catch (Exception e) {
                System.out.println("Can't send request. Try again.");
            }
        }
    }

    public static void sendRequest(HttpClient httpClient) throws URISyntaxException, ExecutionException, InterruptedException {
        System.out.println("sendRequest");
        String body = "login=java&password=Dima";
        System.out.println(body);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8081/login"))
                        .header(HeaderName.CONTENT_TYPE.getKey(), HeaderName.CONTENT_TYPE.getValue())
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
//        HttpResponse<String> httpResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).get();
//        System.out.println("Response = " + httpResponse.body());
    }

    public static HttpClient upClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .executor(Executors.newFixedThreadPool(THREAD_COUNT))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public static void runServer() {
        System.out.println("Run server");
        HttpServer httpServer;
        try {
            // Слушает хост и порт на запушенной машине.
            // Последний параметр указывает на количество соединений, которые могут ожидать ответа в очереди
            httpServer = HttpServer.create(new InetSocketAddress(HOST, PORT), 2);
            // УРЛ доступа и обработчик запросов по данному урлу
            httpServer.createContext(CONTEXT, new MyHttpHandler());
            // потоки исполняющие запрос
            httpServer.setExecutor(Executors.newFixedThreadPool(THREAD_COUNT));
            // создаёт background поток, который ждёт запросов
            httpServer.start();
        } catch (IOException e) {
            System.out.println("Can't create server");
        }
    }
}