package com.guiji.fsagent.entity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

public class RunCommand {

    public static void main(String[] args) throws Exception {
        boolean run ;
        try {
            Socket socket = new Socket("localhost" , 18021);
            //socket.isConnected();
            run = socket.isConnected();
            System.out.println(run);

        } catch (ConnectException e) {
            System.out.println("port1 has stop");
        }

//        if (run) {
////            System.out.println("port is running");
////        } else {
////            System.out.println("port has stop");
////        }
    }
}