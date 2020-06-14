/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatServerHandler implements Runnable {


	
	private Socket client;
	private ChatServer server;
	private Scanner inputStream;
	
	public ChatServerHandler(Socket client, ChatServer server) {
		this.client = client;
		this.server = server;
	}
		
	@Override
	public void run() {
		try {
			inputStream = new Scanner(client.getInputStream());
                        inputStream.nextLine();
                        
			while(true)
			{
                         
				if(!inputStream.hasNext())
					return;
                                  
				String chatLine = inputStream.nextLine();
                            
                     
				System.out.println(chatLine);// client say......
                                
                           
                      
				server.sendChatMessageToAll(chatLine);
                                          
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}