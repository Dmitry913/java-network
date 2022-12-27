package org.school.java.lesson3.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.school.java.lesson3.constants.HeaderName;
import org.school.java.lesson3.exceptions.InvalidLogin;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.school.java.lesson3.constants.UrlParams.LOGIN;

public class MyHttpHandler implements HttpHandler {

    private static final String CORRECT_LOGIN = "java";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("handle common request");
        switch (httpExchange.getRequestMethod()) {
            case "POST":
                try {
                    handlePost(httpExchange);
                } catch (InterruptedException e) {
                    System.out.println("Can't sleep");
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown method request");
        }
        httpExchange.getResponseBody().write("HelloWorld".getBytes());
    }

    private void handlePost(HttpExchange httpExchange) throws IOException, InterruptedException {
        System.out.println("I sleep");
        Thread.sleep(5000);
        System.out.println("I get up");
        if (!httpExchange.getRequestHeaders().getFirst(HeaderName.CONTENT_TYPE.getKey()).equals(HeaderName.CONTENT_TYPE.getValue())) {
            throw new UnsupportedEncodingException();
        }
        Map<String, String> urlParams = parseBody(httpExchange);
        String bodyResponse;
        if (CORRECT_LOGIN.equals(urlParams.get(LOGIN.getValue()))) {
            bodyResponse = "Hello Java";
        } else {
            throw new InvalidLogin();
        }
        writeBody(httpExchange, bodyResponse);
    }

    private Map<String, String> parseBody(HttpExchange httpExchange) {
        Map<String, String> urlParams = new HashMap<>();
        Scanner scanner = new Scanner(httpExchange.getRequestBody());
        while (scanner.hasNextLine()) {
            Arrays.stream(scanner.nextLine().split("&")).forEach(string ->
            {
                String[] keyValue = string.split("=");
                urlParams.put(keyValue[0], keyValue[1]);
            });
        }
        return urlParams;
    }

    private void writeBody(HttpExchange httpExchange, String data) throws IOException {
        httpExchange.sendResponseHeaders(200, data.length());
        try (final OutputStream responseBodyStream = httpExchange.getResponseBody()){
            responseBodyStream.write(data.getBytes(StandardCharsets.UTF_8));
            // после вызова этого метода все накопленные в буфере байтики тут же отправляются на запись и буфер подчищается
            // (не гарантирует запись, только передаёт данные нужному процессу в ОС, если адресат - это абстракция ОС (например, файл))
            responseBodyStream.flush();
        }
    }
}