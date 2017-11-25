package client;


import java.net.*;
import java.io.*;

/**
 * @author Robel Alem & Quinn Conroy
 * @version 2017.11.20
 */
public class Client {

	public final static int SOCKET_PORT = 13267;      // you may change this
	public final static String SERVER = "127.0.0.1";  // localhost
	public final static String FILE_TO_RECEIVED = "/Users/Quinn/Desktop/hello.txt";

	public final static int FILE_SIZE = 6022386;

	public static void main(String[] args) {
		Socket x = null;
		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;
		try {

			sock = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Connecting...");


			byte [] mybytearray  = new byte [FILE_SIZE];
			InputStream is = sock.getInputStream();
			fos = new FileOutputStream(FILE_TO_RECEIVED);
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray,0,mybytearray.length);
			current = bytesRead;

			do {
				bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
				if(bytesRead >= 0) current += bytesRead;
			} while(bytesRead > -1);

			bos.write(mybytearray, 0 , current);
			bos.flush();
			System.out.println("File " + FILE_TO_RECEIVED
					+ " downloaded (" + current + " bytes read)");


			String local = "localhost";
			String greet = "Hi";
			int port = 63342;
			x = new Socket(local, port);
			DataInputStream quinn = new DataInputStream(x.getInputStream());
			DataOutputStream roe = new DataOutputStream(x.getOutputStream());
			System.out.println("Length" + greet.length());
			roe.writeInt(greet.length());

			System.out.println("Establish..");
			roe.writeBytes(greet);

			int r = quinn.readInt();
			byte[] p = new byte[r];
			for (int i = 0; i < r; i++)
				p[i] = quinn.readByte();

			String str = new String(p);
			System.out.println("Received: " + str);
		} catch (UnknownHostException e) {
			System.out.println("sock:" + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if(x!=null)
				try {
					x.close();
				} catch (IOException e) {

				}
		}

	}
}
