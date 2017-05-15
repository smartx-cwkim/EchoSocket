import java.io.*;
import java.net.*;

//public class testServer {
//	
//	public static void main(String[] args) {
//		try{
//			int port = Integer.parseInt(args[0]);  // main함수 실행과 동시에 받는 인자(문자열을 int형으로 파싱.)
//			ServerSocket server = new ServerSocket(port);
//			int i = 1;
//			
//			System.out.println("클라이언트 연결 대기중...");
//			
//			while(true) {
//				Socket socket = server.accept();
//		
//				// new 연산자를 통해 객체를 저장할 메모리 할당. 이후 생성자 호출을 통해 초기화
//				// 생성자 종료되면 접근 가능하게 reference 반환 (생성자 : 초기화 + heap에 저장, 종료시 리턴값 없음)
//				CorrespondClient client = new CorrespondClient(socket, i);
//				// 스레드 시작
//				client.start();
//				
//				i++;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//class CorrespondClient extends Thread {
//	Socket socket;
//	private int i;
//	
//
//	// 생성자를 통해 해당 객체를 사용는데 필요한 socket값 설정 강제.
//	CorrespondClient(Socket socket, int i) {
//		this.socket = socket;
//		this.i = i;
//	}
//	
//	// Override
//	public void run() {
//		try {
//			System.out.println(i + "번째 클라이언트 연결 성공!");
//			
//			OutputStream out = socket.getOutputStream();
//			InputStream in = socket.getInputStream();
//			
//			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
//			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//			
//			String str = null;
//			
//			while(true) {
//				
//				if(((str = br.readLine()) == null) || str.equals("Connection closed")) {
//					System.out.println(i + "번째 클라이언트 연결 종료");
//					this.interrupt();
//					break;
//				}
//				
//				byte[] sendData = str.getBytes();
//				
//				// pw.println(sendData);
//				pw.println(str);
//				pw.flush();
//				
//				// System.out.println("받은 문자열: " + sendData);
//				System.out.println(i + "번째 클라이언트에게 받은 문자열: " + str);
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//}


public class testServer {
	// Using global variable, we can check number of thread, and know how many client connection.
	private static int threadCount = 1;
	
	public static void main(String[] args) {
		try{
			int port = Integer.parseInt(args[0]);  // main함수 실행과 동시에 받는 인자(문자열을 int형으로 파싱.)
			ServerSocket server = new ServerSocket(port);
			
			System.out.println("클라이언트 연결 대기중...");
			
			while(true) {
				Socket socket = server.accept();
		
				// new 연산자를 통해 객체를 저장할 메모리 할당. 이후 생성자 호출을 통해 초기화
				// 생성자 종료되면 접근 가능하게 reference 반환 (생성자 : 초기화 + heap에 저장, 종료시 리턴값 없음)
				CorrespondClient client = new CorrespondClient(socket, threadCount);
				// 스레드 시작
				client.start();
				
				threadCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class CorrespondClient extends Thread {
		Socket socket;
		private int i;

		// 생성자를 통해 해당 객체를 사용는데 필요한 socket값 설정 강제.
		CorrespondClient(Socket socket, int i) {
			this.socket = socket;
			this.i = i;
		}
		
		// Override
		public void run() {
			try {
				System.out.println(i + "번째 클라이언트 연결 성공!");
				
				OutputStream out = socket.getOutputStream();
				InputStream in = socket.getInputStream();
				
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				String str = null;
				
				while(true) {
					if(((str = br.readLine()) == null) || str.equals("Connection closed")) {
						System.out.println(i + "번째 클라이언트 연결 종료");
						this.interrupt();
						break;
					}
					
					byte[] sendData = str.getBytes();
					
					// pw.println(sendData);
					pw.println(str);
					pw.flush();
					
					// System.out.println("받은 문자열: " + sendData);
					System.out.println(i + "번째 클라이언트에게 받은 문자열: " + str);
				}
				
				threadCount--;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}