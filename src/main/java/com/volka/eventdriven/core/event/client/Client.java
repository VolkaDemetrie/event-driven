package com.volka.eventdriven.core.event.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {

        String sentence;
        String modifiedSentence;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 8990);

        DataOutputStream outToServerStream = new DataOutputStream(clientSocket.getOutputStream());


        outToServerStream.writeBytes("NIO Server !!!\n");
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = fromServer.readLine();
        System.out.println("Response form server :: " + sentence);
        clientSocket.close();
    }
}
