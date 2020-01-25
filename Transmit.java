import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Transmit extends Thread{
    
    public ChatClient me;

    public Transmit(ChatClient cc){
        this.me = cc;
    }

    public Transmit(){

    }

    @Override
    public void run()
	{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){

            // 1.Enter message
            String m = "";
            try{
                System.out.println("Please enter your message: ");
                m = br.readLine();
            }
            catch(Exception e){}


            // 2.Send message to Server
            try{
                // Message message = new Message(m)
                me.send(m);

                if(m.equals("LOGOUT")){
                    me.disconnect();
                    break;
                }
            }
            catch(Exception e){}
            
        }
    }
}