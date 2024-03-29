package edu.eci.arep.parcial1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.*;

public class Facade {
     private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://localhost:37000/compreflex=";


    public Facade(){
        
    }

    public static JsonObject connect(String operation, String[] values) throws IOException {

        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
            
        }
        System.out.println("GET DONE");

        return perfomOperation(operation, values);
    }

    private static JsonObject perfomOperation(String operation, String[] values){
        
        Double respCalc = Calculator.getAndIvokeTheMethod(operation, values);

        JsonObject resp = new JsonObject();

        resp.addProperty("operation", operation);

        resp.addProperty("result", respCalc);
        return resp;
    }
}
