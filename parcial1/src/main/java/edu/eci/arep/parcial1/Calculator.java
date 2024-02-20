package edu.eci.arep.parcial1;

import java.net.*;

import com.google.gson.JsonArray;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Calculator {

    public static Calculator _instance = getInstance();

    private static Class math;

    public static void starCalcukator() throws IOException {
        math = Math.class;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(37000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 37000.");
            System.exit(1);
        }

        String petition = "";
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Calculadora Funcionando ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            if(petition.contains("compreflex ")){
                System.out.println(petition);
            }
            outputLine = "HTTP/1.1 200 OK\r\n" +
                    "Content-type:text/html\r\n" + "\r\n";
                    

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }

        serverSocket.close();

    }


    public static Calculator getInstance(){
        return _instance;
    }

    public static JsonArray quickSort(Double[]ArrayToSort, Double start, Double end){
        
        //not implement yed
        
        return null;
    }

    public static Double getAndIvokeTheMethod(String operation, String[] Parameters) {
        try {
            Method method = math.getMethod(operation, Double.TYPE);
            if(method.getParameters().length == 1){
                return (Double) method.invoke(null,  Double.parseDouble("2.3"));
            } else {
                return (Double) method.invoke(null, Double.parseDouble("2.3"), Double.parseDouble("2.3"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("an Error ocurred: " +e.getMessage());
        }
       
        return null;
    }
}
