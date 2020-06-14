/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServer {


	
	private final int port = 3822;
	private ServerSocket serverSocket;
	private ArrayList<Socket> clientList;	
        private int counterClients = 0 ; 
       //  DataInputStream datin; 

	public ChatServer() {// constuctor 
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);// ///////////////////
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		clientList = new ArrayList<Socket>(); //create array list 
	}

	public void startServer() throws IOException {
		System.out.println("establishing connection ... please wait ... ");
		while(true)
		{
			// wait for a client
			Socket client = serverSocket.accept();
			clientList.add(client);
			System.out.println("New client accepted ! " );
                        counterClients++;
			System.out.println("Total users: " + clientList.size());//number of users 
                        
			ChatServerHandler handler = new ChatServerHandler(client,this);
			Thread t = new Thread(handler);// one thread for multi socket ch3 slide 14 
                      
			t.start();
                          
		}
	}
	
	public synchronized void sendChatMessageToAll(String msg) throws IOException {// تزامن اكثر من كلاينت يرسلون بنفس الوقت  synchronized 
       
            try {
                   for(Iterator<Socket> it=clientList.iterator(); it.hasNext();)//for loop to send the message to all client 
                    
		{
                    
			Socket client = it.next();// send msg 
                       
		

			if( !client.isClosed() )
			{
				PrintWriter pw = new PrintWriter(client.getOutputStream());
                               // System.out.println(msg);
                                 int firstName = msg.indexOf(':');// index
                                   String n = msg.substring(firstName+1); // name
//                                    int sizeName = n.length();
//                                            String a = msg.substring(sizeName);
                                       //     System.out.println(n);
                                             int second = msg.indexOf(' ');
                                     String name = msg.substring(0,second);
                                	if(n.contains("bye")){
                                            pw.println( name + " has left the chat :("  );
                                        }
                                        else 
				pw.println(msg);
                               
				pw.flush();// like buffer :)
                                
				
			}
		}
	}
            
             
          catch (IOException e){
          }
             
            }
             
        
     
    private void inform(String ClientName) throws IOException {
         
         try {
            DataOutputStream dos =null ;
         
            System.out.println(":"+ClientName+": Has joined the chat" );
         if(counterClients!=0){
            // System.out.println("after");
               for(Iterator<Socket> it=clientList.iterator(); it.hasNext();) {
            Socket client = it.next();
          if(client!=null){
               dos = new DataOutputStream(client.getOutputStream());
               dos.writeUTF(":"+ClientName+": Has joined the chat" );
               dos.flush();
                     
            }
               }   
         }
         } catch (IOException e) {
         
                
                
         } 

     }
	public static void main(String[] args) throws IOException {
		new ChatServer().startServer();
	}

}