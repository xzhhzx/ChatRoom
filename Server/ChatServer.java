import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class ChatServer{

    public int port;
    public ServerSocket serverSocket;

    // Individual
    private Socket server;
    private DataInputStream in;
    private DataOutputStream out;


    // Open Server
    public void start(int port) throws Exception{
        // user_connections = new HashMap<String, SCThread>();
        serverSocket = new ServerSocket(port);              // 1. Create a socket
        System.out.println("Server started at port " + port +" ...Waiting for clients...");
    }


    // Accept user
    public void accept() throws Exception{
        
        server = serverSocket.accept();                             // 2. Listen (block)
        in = new DataInputStream(server.getInputStream());          // 3. IO
        out = new DataOutputStream(server.getOutputStream());


        String username = receive();        // Get new user's name
        System.out.println("Server reached client " + username +"!");
        

        // Create new thread for new user
        SCThread thr = new SCThread(server, in, out, username);
        thr.start(); 
        // user_connections.put(username, thr);         // ERROR
        ServerThreadManagement.user_connections.put(username, thr);
    }


    public String receive() throws Exception{
        String line = "";       // Read     
        line = in.readUTF();
        System.out.println("Server received: "+line);
        return line;
    }



    public static void main(String[] args) throws Exception{

        try{
            ChatServer cs = new ChatServer();

            // 1.Start Server
            cs.start(3000);

            // 2.Accept users
            while(true){
                cs.accept();
            }
        }
        catch(Exception e){throw e;}
    }
}