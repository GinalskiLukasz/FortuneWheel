package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

/**
 * Klasa uruchamia po³¹czenie wykorzystuj¹c serwerSocket
 */
public class SocketServer {

	
	private static final int PORT = 6057;

	private static ConcurrentHashMap<Integer, PrintWriter> clients = new ConcurrentHashMap<Integer, PrintWriter>();

	/**
	 * Metoda g³ówna. Tworzy serwerSocket oraz requestHandler  
	 * @param args tablica stringów 
	 * @throws Exception obs³uga wyj¹tku
	 */
	public static void main(String[] args) throws Exception {
	

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("The server is running on port: " + PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				RequestHandler requestHandler = new RequestHandler(socket);
				requestHandler.start();
			}
		}
	}

	/**
	 * Klasa obs³uguj¹ca odbierane i wysy³ane dane 
	 * @version 1.0 2019-06-04
	 * @author Ginalski £ukasz
	 *
	 */
	private static class RequestHandler extends Thread {
		private int id;
		private String name;
		private Socket socket;
		private BufferedReader inputBufferedReader;
		private PrintWriter outputPrintWriter;

		public RequestHandler(Socket socket) {
			this.socket = socket;
			id = new Random().nextInt(Integer.MAX_VALUE);
		}

	
		/**
		 * Metoda buduje obiekty BufferedReader oraz PrintWriter s³u¿¹ce do odbierania i wysy³ania danych 
		 */
		public void run() {
			try {
				inputBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outputPrintWriter = new PrintWriter(socket.getOutputStream(), true);
				outputPrintWriter.println("RDY");
				
				name = inputBufferedReader.readLine();
				clients.putIfAbsent(id, outputPrintWriter);
				System.out.println("SERWER: Name: " + name);

				outputPrintWriter.println("UID" + String.valueOf(id));
				System.out.println("SERWER: UID" + String.valueOf(id));

				while (true) {
					String clientMsg = inputBufferedReader.readLine();
					if (clientMsg == null) {
						return;
					}
					
					
					
					else{
					for (ConcurrentHashMap.Entry<Integer, PrintWriter> entry : clients.entrySet()) {
						
						PrintWriter printWriter = entry.getValue();
						if(clientMsg.startsWith("AAA") ||
						clientMsg.startsWith("XAXRoundPoints")||
						clientMsg.startsWith("XAXTotalPoints")||
						clientMsg.startsWith("XBXRoundPoints")||
						clientMsg.startsWith("XAXChangeTurn")||
						clientMsg.startsWith("XBXChangeTurn")||
						clientMsg.startsWith("XAXRefreshBoard")||
						clientMsg.startsWith("XBXRefreshBoard")||
						clientMsg.startsWith("XAXSynchronizeWheel")||
						clientMsg.startsWith("XBXSynchronizeWheel")||
						clientMsg.startsWith("XAXSynchronizeDraggedWheel")||
						clientMsg.startsWith("XBXSynchronizeDraggedWheel")||
						clientMsg.startsWith("XAXRequestPassword")||
						clientMsg.startsWith("XBXRequestPassword")||
						clientMsg.startsWith("XAXShowPassword")||
						clientMsg.startsWith("XBXShowPassword")||
						clientMsg.startsWith("XAXSynchronizePassword")||
						clientMsg.startsWith("XBXSynchronizePassword")||
						clientMsg.startsWith("XAXSynchronizeUsedPasswords")||
						clientMsg.startsWith("XBXSynchronizeUsedPasswords")||
						clientMsg.startsWith("XAXRound")||
						clientMsg.startsWith("XBXRound")||
						clientMsg.startsWith("XAXChangeRound")||
						clientMsg.startsWith("XBXChangeRound")||
						clientMsg.startsWith("XBXRound")||
						clientMsg.startsWith("XAXINFO")||
						clientMsg.startsWith("XBXINFO")||
						clientMsg.startsWith("XBXTotalPoints")) 
						{
								
						printWriter.println(clientMsg);
						
						}
						else {
						
						String serverMsg = "MSG" + id + "\t" + name + "\t" + name + "\t" + ": " + clientMsg;
						
						
						printWriter.println(serverMsg); // wysyÅ‚anie komunikatu do klienta
						System.out.println("SERWER wiadomoœæ: " + serverMsg);
						}
						}
					}
				}
			} catch (IOException e) {
				System.out.println("Client reset connection");
			} finally {
				clients.remove(id);
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
