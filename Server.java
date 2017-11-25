package client;

/**
 *
 * @author Quinn
 */

import java.net.*;
import java.io.*;

/**
 *
 * @author Robel Alem & Quinn Conroy
 * @version 2017.11.20
 */
public class Server {

	public final static String FILE_TO_SEND = "/Users/Quinn/Desktop/hello.txt";


	public static void main(String[] args) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		ServerSocket servsock = null;
		Socket sock = null;
		try {
			int port = 63342 ;
			ServerSocket newSocket = new ServerSocket(port);
			System.out.println("server.");
			while (true) {
				Socket clientSocket = newSocket.accept();
				Connection c = new Connection(clientSocket);
				File myFile = new File(FILE_TO_SEND);
				byte [] mybytearray  = new byte [(int)myFile.length()];
				fis = new FileInputStream(myFile);
				bis = new BufferedInputStream(fis);
				bis.read(mybytearray,0,mybytearray.length);
				os = sock.getOutputStream();
				System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
				os.write(mybytearray,0,mybytearray.length);
				os.flush();
				System.out.println("Done.");

			}



		} catch (IOException e) {
			System.out.println("Listen :" + e.getMessage());
		}

	}

	static class Connection extends Thread {

		DataInputStream quinn;
		DataOutputStream roe;
		Socket clientSocket;

		public Connection(Socket firstClientSocket) {
			try {
				clientSocket = firstClientSocket;
				quinn = new DataInputStream(clientSocket.getInputStream());
				roe = new DataOutputStream(clientSocket.getOutputStream());
				this.start();
			} catch (IOException e) {
				System.out.println("Connection:" + e.getMessage());
			}
		}

		public void run() {
			try {
				String grab = quinn.readUTF();
				FileWriter pr = new FileWriter("test.txt");
				BufferedWriter bufWriter = new BufferedWriter(pr);
				//     BufferedReader f = new BufferedReader(new FileReader(.in));
				int run = quinn.readInt();
				System.out.println("Read Length" + run);
				byte[] init = new byte[run];
				System.out.println("Writing.......");
				for (int i = 0; i < run; i++)
					init[i] = quinn.readByte();



				String dig = new String (init);
				bufWriter.append(dig);
				bufWriter.close();
				System.out.println("receive from : " + clientSocket.getInetAddress() + ":"
						+ clientSocket.getPort() + " message - " + dig);
				roe.writeInt(dig.length());
				roe.writeBytes(dig);
				roe.writeUTF(grab);
			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO:" + e.getMessage());
			} finally {
				try {
					clientSocket.close();

				} catch (IOException e) {

				}
			}

		}

	}
}
