import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientHandler implements Runnable {


	
	private Socket client;
	
	private Scanner inputStream;
	
	public ChatClientHandler(Socket client ) {
		this.client = client;
	}
		
	@Override
	public void run() {
		try {
			inputStream = new Scanner(client.getInputStream());
			while(true)
			{
				if(!inputStream.hasNext())
					return;
				String chatLine = inputStream.nextLine();
                              //  System.out.println("before");
				System.out.println(chatLine); // client said ....
			                //         System.out.println("after");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}