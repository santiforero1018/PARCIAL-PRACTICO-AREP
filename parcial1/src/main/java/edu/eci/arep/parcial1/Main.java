package edu.eci.arep.parcial1;

import edu.eci.arep.parcial1.*;

public class Main {
    public static void main(String[] args) {
        try {
            WebServer.getInstance().startServer();
            Calculator.getInstance().starCalcukator();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
