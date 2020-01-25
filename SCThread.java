import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class SCThread extends Thread{

    private Socket server;
    private DataInputStream in;
    private DataOutputStream out;



    public SCThread(Socket server){
        this.server = server;
        in = new DataInputStream(server.getInputStream());          // 3. IO
        out = new DataOutputStream(server.getOutputStream());
    }


    public SCThread(Socket server, DataInputStream in, DataOutputStream out){
        this.server = server;
        this.in = in;
        this.out = out;
    }

    public void send(String s) throws Exception{
        out.writeUTF(s);
        System.out.println("Server send: "+s);
    }

    public String receive() throws Exception{
        String line = "";       // Read     
        line = in.readUTF();
        System.out.println("Server received: "+line);
        return line;
    }

    public void disconnect() throws Exception{
        // serverSocket.close();
        server.close(); 
        out.close();
        in.close(); 
        System.out.println("Server closed.");
    }


    @Override
    public void run(){
        try{
            while(true){
                String m = this.receive();        // 2.Receive message
                this.send("Received!");           // 3.Send back
            }
        }
        catch(Exception e){
            System.out.println(e);
            disconnect();
        }
    }

}