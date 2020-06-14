import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient implements Runnable {

  // why is the ChatClient Multi-threaded?

	private Socket link;
	private PrintWriter outputStream;
	private Scanner inputStream;
	private int port = 3822;
	private String ClientName;

	public ChatClient() throws IOException {
		//initialize();
	//

	//private void initialize() throws IOException {
		// get server address
		Scanner keyboard = new Scanner(System.in);
	//	System.out.println("What is the chat server's ip address?");
//		String str = "";

		// get user name
                System.out.println("WELCOME ♥ ");
                System.out.println("You are now connected with Server =) ");
		System.out.println("What is your name?");
		ClientName = keyboard.next();

	        //   System.out.println("THANK YOU FOR JOINNING WITH US ! ");
		
            System.out.println("HI ! "+"\""+ClientName.toUpperCase() + "\""+"  THANK YOU FOR JOINNING WITH US !"+ " \n now you can start chatting :) \n and write bye to exit.:( ");
		link = null;
		try {
			link = new Socket("192.168.86.21", port);
			link.setReuseAddress(true);
		} catch (IOException e) {
			
			System.out.println("not found");
		}
		inputStream = new Scanner(link.getInputStream()); // ip and port number 
		outputStream = new PrintWriter(link.getOutputStream());// ip and port number 

	
		Thread t = new Thread(this);
		t.start();

		// take input from client 
		while (keyboard.hasNextLine()) {
			String msg = keyboard.nextLine();
                        if(msg.equalsIgnoreCase("bye")){
                              System.out.println("Good bye :( \n AND stay home stay safe ♥♥ ");
                              outputStream.write(ClientName +" " + "left the chat" );
                              outputStream.flush();
                              System.exit(0);
                        }
			outputStream.println(ClientName + " says : " + msg);
			outputStream.flush();
		}
                
	}

	public static void main(String[] args) throws Exception {
		new ChatClient();
	}

	@Override
	public void run() {
		while (true) {
			if (inputStream.hasNextLine())
				System.out.println(inputStream.nextLine()); // to read message from clients and send it 
		}
	}
}