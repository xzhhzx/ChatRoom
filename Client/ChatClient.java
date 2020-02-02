import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalTime;

class ChatClient{

    public String username;
    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;
    public volatile boolean DISCONNECT = false;


    public void connect(String IP, int port) throws Exception{
        client = new Socket(IP, port);
        out = new DataOutputStream(client.getOutputStream());      // Write
        in = new DataInputStream(client.getInputStream());         // Read
    }

    public void send(String s) throws Exception{
        out.writeUTF(s);       // Send message
    }


    public void disconnect() throws Exception{
        client.close(); 
        in.close(); 
        out.close();
        System.out.println("Client closed.");
    }


    public String receive() throws Exception{
            
        String line = in.readUTF();
        System.out.println(LocalTime.now() + " Client received: "+line);
        return line;
    }


    public static void main(String[] args){

        // 1.Receive IP address and TCP port
        // if(args.length == 1){
        //     System.out.println("Set port = " + args[0]);
        //     port = Integer.parseInt(args[0]);

        // }
        // else{
        //     port = 3000;
        // }


        // 2.Enter username
        ChatClient me = new ChatClient();
        System.out.println("Please enter your name: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            me.username = br.readLine();
        }
        catch(Exception e){}


        // 3.Connect to server
        try{
            me.connect("127.0.0.1", 3000);
            System.out.println(me.username + " connected!");
            me.send(me.username);
        }
        catch(Exception e){System.out.println("CONNECTION ERROR!");}
        

        // 4.Send message
        Transmit tx = new Transmit(me);       // Start Transmit thread
        tx.start();

        // 5.Receive message
        Receive rx = new Receive(me);         // Start Receive thread
        rx.start();
        

        // 6.Wait until stop
        try{
            tx.join();
            rx.join();
        }
        catch(Exception e){System.out.println("join() ERROR!");}



        // 7.Disconnect
        try{
            me.disconnect();
        }
        catch(Exception e){System.out.println("DISCONNECTION ERROR!");}
        
    }
}