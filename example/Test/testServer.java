import java.io.*;
import java.net.*;

public class testServer {
	public static void main(String[] args) {
		try{
			int port = Integer.parseInt(args[0]);  
			ServerSocket server = new ServerSocket(port);
			
			System.out.println("waiting...");
			
			while(true) {	

				Socket socket = server.accept();
				
			
				CorrespondClient client = new CorrespondClient(socket);
				
				client.start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class CorrespondClient extends Thread {
	Socket socket;
	
	
	CorrespondClient(Socket socket) {
		this.socket = socket;
	}
	
	
	public void run() {
		try {
			System.out.println("okay!");
			
			OutputStream out = socket.getOutputStream();
			InputStream in = socket.getInputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String str = null;
			
			while(true) {
				
				if((str = br.readLine()) == null) {
					System.out.println("connection closed");
					this.interrupt();
					break;
				}
				
				byte[] sendData = str.getBytes();
				
				
				pw.println(str);
				pw.flush();
				
				
				System.out.println("string from client: " + str);
				
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
