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


	public static void main(String [] args) {
		String file;
		System.out.println("File name : ");
		Scanner scan = new Scanner(System.in);
		file = scan.nextLine();
		scan.close();

		while (true) {
			int port = 5000;
			ServerSocket newSocket = new ServerSocket(port);
			System.out.println("Processing : "); //print out statement processing
			Socket clientSocket = newSocket.accept(); //accepts connection
			System.out.println("Connect with" + clientSocket.getInetAddress().toString());
			DataInputStream quinn = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream roe = new DataOutputStream(clientSocket.getOutputStream());

			try {
				String f = " ";
				f = quinn.readUTF();
				System.out.println("File sent: "); //print out statement file sent
				if (!f.equals("stop")) {
					System.out.println("Sending files" + file); //print out statement sending files
					roe.writeUTF(file);
					roe.flush();

					File ff = new File(file);
					FileInputStream fis = new FileInputStream(ff);
					long sz = (int) ff.length();
					byte mybytearray[] = new byte[1024];
					int run;
					roe.writeUTF(Long.toString(sz));
					roe.flush();
					System.out.println("size :" + sz);
					System.out.println("Buff size :" + newSocket.getReceiveBufferSize());
					while ((run = fis.read(mybytearray)) != -1) {
						roe.write(mybytearray, 0, run);
						roe.flush();
					}
					fis.close();
					System.out.println("Ready :");
					roe.flush(); //flushes (spacing)
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
}

