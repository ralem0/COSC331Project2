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

	//public final static String FILE_TO_SEND = "C"..\\Desktop\\files";


	public static void main(String[] args) {
		String file;
		System.out.println("File name : ");
		Scanner scan = new Scanner (System.in);
		file = scan.nextLine();
		scan.close();

		while(true){
			int port = 5000;
			ServerSocket newSocket = new ServerSocket(port);
					System.out.println("Processing : ");
			Socket clientSocket = newSocket.accept(); //accepts connection
			System.out.println("Connect with" + clientSocket.getInetAddress().toString());
			DataInputStream quinn = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream roe = new DataOutputStream(clientSocket.getOutputStream());

			try {
				String f = " ";
				f = quinn.readUTF();
				System.out.println("File sent: ");
				if(!f.equals("stop")){
					System.out.println("Sending files" + file);
					roe.writeUTF(file);
					roe.flush();

					File ff = new File(file);
					FileInputStream fis = new FileInputStream(ff);
					long sz = (int) ff.length();
					byte mybytearray [] = new byte [1024];
					int run;
					roe.writeUTF(Long.toString(sz));
					roe.flush();
					System.out.println("size :" + sz);
					System.out.println("Buff size :" + newSocket.getReceiveBufferSize());
					while((run = fis.read(mybytearray)) != -1){
						roe.write(mybytearray, 0 , run);
						roe.flush();
					}
					fis.close();
					System.out.println("Ready :");
					roe.flush();
				}
					roe.writeUTF("Done");
					System.out.println("Finished");
					roe.flush();

				} catch (Exception e) {
				e.printStackTrace();
				System.out.print("Error");
			}
			quinn.close();
			clientSocket.close();
			newSocket.close();
			}
		}

		BufferedInputStream bis = null;
		OutputStream os = null;
		ServerSocket servsock = null;
		Socket sock = null;
		try {
			//int port = 63342;
			 //create new socket
			System.out.println("server.");
			while (true) {
				//Socket clientSocket = newSocket.accept();
				Connection c = new Connection(clientSocket);
				File myFile = new File(FILE_TO_SEND); //create new file object
				byte[] mybytearray = new byte[(int) myFile.length()]; //converts file into bytes
				fis = new FileInputStream(myFile);
				bis = new BufferedInputStream(fis);
				//bis.read(mybytearray,0,mybytearray.length);
				os = sock.getOutputStream();
				int count;
				while ((count = fis.read(mybytearray)) > 0) {
					os.write(mybytearray);
				}
				System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
				os.write(mybytearray, 0, mybytearray.length);
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
				clientSocket = firstClientSocket; //socket equals new socket
				quinn = new DataInputStream(clientSocket.getInputStream());
				roe = new DataOutputStream(clientSocket.getOutputStream());
				this.start();
			} catch (IOException e) {
				System.out.println("Connection:" + e.getMessage());
			}


		public void run() {
			try {
				String grab = quinn.readUTF();
				//FileWriter pr = new FileWriter("test.txt");
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
