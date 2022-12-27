package org.school.java.lesson2;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Lesson2 {

    public static void main(String[] args) throws IOException {
        String httpAddress = null;
        if (args.length > 0) {
            httpAddress = args[0];
        }
        if (httpAddress == null) {
            System.out.println("Not input http address");
            return;
        }
        URLConnection urlConnection = new URL(httpAddress).openConnection();
        urlConnection.connect();
        System.out.println("\nResources type - " + urlConnection.getContentType());
        System.out.println("Resources size - " + urlConnection.getContentLength());
        System.out.println("\n\n\nResources:\n");
        Scanner scanner = new Scanner(urlConnection.getInputStream());
        while(scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }
}
