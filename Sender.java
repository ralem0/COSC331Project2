/*
 *This class sends and receive datagram packets
 */
package localchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * @author Antoinette Addo
 * @version 2017.10.12
 */

public class Sender implements Runnable {
	
        DatagramSocket socket;
	DatagramPacket packet;
	BufferedReader reader;
	InetAddress addr;
	SocketAddress sockAddr;
	Scanner scan;
	
	public Sender(DatagramSocket s, InetAddress a) {
		socket = s;
		scan = new Scanner(System.in);
		addr = a;
	}
	
	public Sender(DatagramSocket s, SocketAddress sA) {
		socket = s;
		scan = new Scanner(System.in);
		sockAddr = sA;
		addr = null;
	}
        
	public void run() {
		try{
			System.out.println("Enter message: ");
			String temp = scan.nextLine();
			if (addr == null) packet = new DatagramPacket(temp.getBytes(),temp.length(),sockAddr);
			else packet = new DatagramPacket(temp.getBytes(),temp.length(),addr, 5000);
			socket.send(packet);
		}
		catch(IOException e) {
			
		}
	}
	
}