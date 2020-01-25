import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Receive extends Thread{
    
    public ChatClient me;

    public Receive(ChatClient cc){
        this.me = cc;
    }

    public Receive(){

    }

    @Override
    public void run()
	{
        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            try{
                me.receive();
            }
            catch(Exception e){System.out.println("RECEIVE ERROR!");}
        }
    }
}