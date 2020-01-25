import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class SCThread extends Thread{

    public Socket server;
    public DataInputStream in;
    public DataOutputStream out;
    public ChatServer cs;



    // public SCThread(Socket server){
    //     this.server = server;
    //     in = new DataInputStream(server.getInputStream());     
    //     out = new DataOutputStream(server.getOutputStream());
    // }


    public SCThread(ChatServer cs, Socket server, DataInputStream in, DataOutputStream out){
        this.server = server;
        this.in = in;
        this.out = out;
        this.cs = cs;
    }

    // Send back to the same user
    public void send(String s) throws Exception{
        out.writeUTF(s);
        System.out.println("Server send: "+s);
    }

    // Send to other user
    public void send(String s, String other_user) throws Exception{
        // System.out.println(cs.user_connections);
        // cs.user_connections.get(other_user).send(s);
        SCThread receiverThread = ServerThreadManagement.user_connections.get(other_user);
        receiverThread.out.writeUTF(s);
        System.out.println("Server send (to other user): "+s);
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


                // Resolve message and send to corresponding user
                if(m.matches("@.+\\s.+")){
                    // System.out.println("       Matches!");
                    String receiver = m.split(" ")[0].substring(1);
                    String message = m.split(" ")[1];

                    System.out.println(receiver + " " + message);
                    this.send(message, receiver);

                }


                // else{
                //     this.send("Received!");           // 3.Send back
                // }
            }
        }
        catch(Exception e){
            System.out.println(e);
            // disconnect();
        }
    }

}