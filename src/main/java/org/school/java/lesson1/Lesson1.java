package org.school.java.lesson1;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;

public class Lesson1 {
    public static void main(String[] args) {
        try {
            getAllInterfaceInfo();
        } catch (IOException e) {
            System.out.println("Can't read info");
        }
    }

    public static void getAllInterfaceInfo() throws SocketException {
        System.out.println("Interface on this machine:");
        NetworkInterface.networkInterfaces().forEach(networkInterface -> {
            System.out.println("name - " + networkInterface.getName());
            try {
                System.out.println("Is up - " + networkInterface.isUp());
                System.out.println("Is virtual - " + networkInterface.isVirtual());
                System.out.println("Is loopback - " + networkInterface.isLoopback());
            } catch (IOException e) {
                System.out.println("Can't read info");
            }
        });
    }
}
