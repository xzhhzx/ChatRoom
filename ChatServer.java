import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class ChatServer{

    public int port;
    public List<String> users;
    private ServerSocket serverSocket;

    // Individual
    private Socket server;
    private DataInputStream in;
    private DataOutputStream out;


    public void start(int port) throws Exception{
        users = new ArrayList<String>();
        serverSocket = new ServerSocket(port);      // 1. Create a socket
        System.out.println("Server started at port " + port +" ...Waiting for clients...");
    }


    public void accept() throws Exception{
        
        server = serverSocket.accept();                             // 2. Listen (block)
        in = new DataInputStream(server.getInputStream());          // 3. IO
        out = new DataOutputStream(server.getOutputStream());
        String username = receive();        // Get new user's name
        this.users.add(username);           // Add to list

        System.out.println("Server reached client " + username +"!");
        

        // Create new thread for new user
        // Thread thr = new SCThread(server, in, out);
        Thread thr = new SCThread(server, in, out);
        thr.start();
        
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
            // cc.port = 3000;
            // if(args.length == 1){
            //     System.out.println("Set port = " + args[0]);
            //     port = Integer.parseInt(args[0]);
            // }


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