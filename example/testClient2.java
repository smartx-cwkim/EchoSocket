import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Timer;
import java.io.*;

public class testClient2 {
	public static void main(String[] args) {
		Socket socket = null;
		GetData gd = null;
		OutData od = null;
		int port;
		String address = null;
		String select = null;
		
		try {
			address = args[0]; 
			port = Integer.parseInt(args[1]);  
			socket = new Socket(address, port);

			
			
			gd = new GetData(socket);
			od = new OutData(socket);
			
			gd.start();
			od.start();
			
		} catch(IOException e) {
		}
	}
}


class GetData extends Thread {
	private BufferedReader br = null;
	private Socket socket = null;
	private PrintWriter pw = null;
	
	public GetData(Socket socket) {
		try {
			InputStream in = socket.getInputStream();
			this.br = new BufferedReader(new InputStreamReader(in));
			
			OutputStreamWriter osw = new OutputStreamWriter(System.out);
			this.pw = new PrintWriter(osw);
		} catch (Exception e) {
		}
	}
	
	public void run() {
		try {
			String str = null;
			
			while((str = this.br.readLine())!=null) {
				this.pw.println("string from server : " + str);
				this.pw.flush();
			}
			
			this.interrupt();
		} catch(Exception e) {
		}
	}
}

class OutData extends Thread {
	private int length = 100;
	private PrintWriter pw = null;
	private BufferedReader key = null;
	
	public OutData(Socket socket) {
		try {
			OutputStream os = socket.getOutputStream();
			this.pw = new PrintWriter(new OutputStreamWriter(os));
						
		} catch(Exception e) {
		}
	}
	
	public void run() {
		try {
			String str = null;
			Random rand = new Random();
			int min=200, max=250, count=0;
			int selectmin=1, selectmax=2;
			int randomNUM = rand.nextInt(max - min +1) + min;
			int selectcount = rand.nextInt(selectmax - selectmin + 1) + selectmin;
			
			if(selectcount == 1) {
				Queue<String> queueStr = new LinkedList<String>(); 
				
				while(true) {
					
					getRandomString input = new getRandomString(length);
					str = input.get();
					
					if(count>randomNUM) {
						str = "quit";
					}
					
					if(str.equals("quit")) {
						this.pw.println("Connection closed");
						break;	
					}
					
					this.pw.println(str);
					this.pw.flush();
					count++;
								
					queueStr.add(str);
					
					
					Thread.sleep((long)2000);
				}
									
				while(!queueStr.isEmpty()) {
					System.out.println("string to send server(Queue): " + queueStr.poll());
				}
				
			} else if(selectcount == 2) {
				
				Stack<String> stackStr = new Stack<String>(); 
							
				while(true) {
					
					getRandomString input = new getRandomString(length);
					str = input.get();
					
					if(count>randomNUM) {
						str = "quit";
					}
					
					if(str.equals("quit")) {
						break;
					}
					
					this.pw.println(str);
					this.pw.flush();
					count++;
								
					stackStr.push(str);
					
					
					Thread.sleep((long)2000);
				}
							
				while(!stackStr.isEmpty()) {
					System.out.println("string to send server(Stack): " + stackStr.pop());
				}
	
			}
		} catch(Exception e) {
		} finally {
			try {
				pw.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
