import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class SCThread extends Thread{

    public Socket server;
    public DataInputStream in;
    public DataOutputStream out;
    // public ChatServer cs;
    public String username;



    // public SCThread(Socket server){
    //     this.server = server;
    //     in = new DataInputStream(server.getInputStream());     
    //     out = new DataOutputStream(server.getOutputStream());
    // }


    public SCThread(Socket server, DataInputStream in, DataOutputStream out, String username){
        this.server = server;
        this.in = in;
        this.out = out;
        this.username = username;
    }

    // Send back to the same user
    public void send(String s) throws Exception{
        out.writeUTF(s);
        System.out.println("Server send to [ " + this.username + " ] : "+s);
    }

    // Send to other user
    public void send(String s, String other_user) throws Exception{
        // cs.user_connections.get(other_user).send(s);         // DOESN'T WORK, BUT WHY?
        SCThread receiverThread = ServerThreadManagement.user_connections.get(other_user);      // ABOVE PROBLEM SOLVED WITH A HELPER CLASS
        receiverThread.send(s);
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
        System.out.println("Server connection to [ " + this.username + " ] closed.");
    }


    @Override
    public void run(){
        try{
            while(true){

                // 1.Receive message
                String m = this.receive();        

                // 2.Resolve message and perform some operations          
                // (Could be resolved at client side i.o.t minimize server resource usage. And message is wrapped in a Message class)     
                
                if(m.matches("@.+\\s.+")){                              // [Message type 1]: Send to corresponding user
                    // System.out.println("       Matches!");
                    String receiver = m.split(" ")[0].substring(1);
                    String message = m.split(" ")[1];
                    // System.out.println(receiver + " " + message);
                    this.send(message, receiver);
                }

                else if(m.equals("WHOIS")){                             // [Message type 2]: WHOIS
                    this.send(ServerThreadManagement.user_connections.keySet().toString());     
                }


                else if(m.equals("LOGOUT")){
                    this.disconnect();
                }


                else if(m.equals("PENGU")){
                    // TODO
                }

                else{               // [Message type 5]: Broadcast
                    for(String username : ServerThreadManagement.user_connections.keySet()){
                        this.send(m, username);
                    }
                    
                }


                // else{
                //     this.send("Received!");           // 3.Send back
                // }
            }
        }
        catch(Exception e){
            System.out.println(e);
            // throw e;
        }
    }

}