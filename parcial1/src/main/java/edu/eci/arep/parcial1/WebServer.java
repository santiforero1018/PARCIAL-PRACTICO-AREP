package edu.eci.arep.parcial1;

import java.net.*;
import java.io.*;

public class WebServer {

    private static Facade facade;

    private static WebServer _instance = getInstance();

    public static void startServer() throws IOException {

        facade = new Facade();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }

        boolean running = true, firtsline = true;
        String petition = ""; 
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
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
                if(firtsline){
                    petition = inputLine.split(" ")[1];
                    firtsline = false;
                    System.out.println(petition);
                }
                // System.out.println("Recibí: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            if(petition.startsWith("/") || petition.contains("calculator")){
                outputLine = "HTTP/1.1 200 OK\r\n" +
                    "Content-type:text/html\r\n" + "\r\n"
                    + getCalculatorPage();
            } else if(petition.startsWith("computar")){

                String aux1 = petition.split("=")[1];
                String operation = aux1.split("(")[0]; // sacamos la operacion
                String values = aux1.split("(")[1].replace(")", "");
                String[] array = values.split(","); //sacamos el arreglo de valores 
                outputLine = "HTTP/1.1 200 OK\r\n" +
                "Content-type:text/html\r\n" + "\r\n" + getResponseCalculator(operation, array);
            } else {
                outputLine = "HTTP/1.1 404 NOT FOUND\r\n"+
                "Content-type:text/html\r\n" + "\r\n" + getNotFounPage();
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }

        serverSocket.close();

    }


    private static String getCalculatorPage(){
        return "<!DOCTYPE html>\r\n" + //
                "<html>\r\n" + //
                "    <head>\r\n" + //
                "        <title>Form Example</title>\r\n" + //
                "        <meta charset=\"UTF-8\">\r\n" + //
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + //
                "    </head>\r\n" + //
                "    <body>\r\n" + //
                "        <h1>Calculator</h1>\r\n" + //
                "        <form action=\"/computar\">\r\n" + //
                "            <label for=\"name\">Operation:</label><br>\r\n" + //
                "            <input type=\"text\" id=\"operation\" name=\"operation\" value=\"sum\"><br><br>\r\n" + //
                "            <label for=\"name\">Number(s):</label><br>\r\n" + //
                "            <input type=\"text\" id=\"numbers\" name=\"numbers\" value=\"1.0,2.5\"><br><br>\r\n" + //
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\r\n" + //
                "        </form> \r\n" + //
                "        <div id=\"getrespmsg\"></div>\r\n" + //
                "\r\n" + //
                "        <script>\r\n" + //
                "            function loadGetMsg() {\r\n" + //
                "                let operation = document.getElementById(\"operation\").value;\r\n" + //
                "                let values = document.getElementById(\"numbers\").value;\r\n" + //
                "                const xhttp = new XMLHttpRequest();\r\n" + //
                "                xhttp.onload = function() {\r\n" + //
                "                    document.getElementById(\"getrespmsg\").innerHTML =\r\n" + //
                "                    this.responseText;\r\n" + //
                "                }\r\n" + //
                "                xhttp.open(\"GET\", \"/computar?comando=\"+operation+\"(\"+values+\")\");\r\n" + //
                "                xhttp.send();\r\n" + //
                "            }\r\n" + //
                "        </script>\r\n" + //
                "    </body>\r\n" + //
                "</html>";
    };


    private static String getNotFounPage(){
        return "<!DOCTYPE html>\r\n" + //
                "<html lang=\"en\">\r\n" + //
                "<head>\r\n" + //
                "    <meta charset=\"UTF-8\">\r\n" + //
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + //
                "    <title>NOT FOUND</title>\r\n" + //
                "</head>\r\n" + //
                "<body>\r\n" + //
                "    <h1>NOT FOUND</h1>\r\n" + //
                "</body>\r\n" + //
                "</html>";
    }

    private static String getResponseCalculator(String operation, String[] values){
        return "<!DOCTYPE html>\r\n" + //
                "<html lang=\"en\">\r\n" + //
                "<head>\r\n" + //
                "    <meta charset=\"UTF-8\">\r\n" + //
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n" + //
                "    <title>Response calculator</title>\r\n" + //
                "</head>\r\n" + //
                "<body>\r\n" + //
                "    <h3>operacion:{operacion}</h3>\r\n" + //
                "    <h3>resultado:{resp}</h3>\r\n" + //
                "</body>\r\n" + //
                "</html>";
    }


    public static WebServer getInstance(){
        return _instance;
    }
}
