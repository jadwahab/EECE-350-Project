import java.io.*;
import java.net.*;

public class TcpServer {


	// The constructor of the class: Left blank since not needed //
	public TcpServer() {
	}

	// The main function: always has to be inside a class //
	public static void main(String[] args) {
		 TcpServer server = null;
	        server = new TcpServer();
	        server.acceptConnection();

	}
	public void acceptConnection(){
		// Create a new server socket, it is uninitialized now //
		ServerSocket welcomeSocket = null;
		System.out.println("Server Now Running");
		// Always try catch block for errors and exceptions //
		try {
			// construct the socket on the TCP port 10000 //
			welcomeSocket = new ServerSocket(10000);
		} catch (IOException ex) {// catch block to catch IO exceptions and
			// print them if any //
			ex.printStackTrace();
		}

		/*
		 * This is the bulk of the server, it should always run in an infinite
		 * loop
		 */
		while (true) {
			// connection socket //
			Socket connectionSocket;

			try {
				connectionSocket = welcomeSocket.accept();//this is the multithreading part of the server
				myThread st = new myThread(connectionSocket);
				new Thread(st).start();// this creates and startes a thread

			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}
}