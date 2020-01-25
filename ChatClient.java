import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class ChatClient{

    public String username;
    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;

    public void connect(String IP, int port) throws Exception{
        client = new Socket(IP, port);
        // send(username);
        out = new DataOutputStream(client.getOutputStream());      // Write
        in = new DataInputStream(client.getInputStream());          // Read
        // System.out.println("Client started connection...");
    }

    public void send(String s) throws Exception{
        // System.out.println("Client send: "+s);
        out.writeUTF(s);       // Send message
    }


    public void stop() throws Exception{
        client.close(); 
        in.close(); 
        out.close();
        System.out.println("Client closed.");
    }


    public String receive() throws Exception{
            
        String line = in.readUTF();
        System.out.println("Client received: "+line);
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
        


        // Transmit tx = new Transmit(me);       // Start Transmit thread
        // tx.start();
        // Receive rx = new Receive();         // Start Receive thread
        // rx.start();

        // try{
		// 	tx.join();
		// } catch(InterruptedException e){
		// 	System.out.println("InterruptedException!");
		// }



        while(true){
            // 4.Enter message
            String m = "";
            try{
                System.out.print("Please enter your message: ");
                m = br.readLine();
            }
            catch(Exception e){}


            // 5.Send message to Server
            try{
                // Message message = new Message(m)
                me.send(m);

                if(m.equals("Exit")){
                    break;
                }
            }
            catch(Exception e){}



            // 6.Receive message (at the same time)
            // ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			// Message m=(Message)ois.readObject();
            try{
                me.receive();
            }
            catch(Exception e){System.out.println("RECEIVE ERROR!");}


        }



        try{
            me.stop();
            System.out.println(me.username + " disconnected!");
        }
        catch(Exception e){System.out.println("DISCONNECTION ERROR!");}
        
        
        
    }
}