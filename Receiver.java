/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Antoinette Addo
 * @version 2017.10.12
 */

public class Receiver implements Runnable {

	DatagramPacket packet;
	DatagramSocket socket;
	
	public Receiver(DatagramSocket s) {
		socket = s;
		byte[] buffer = new byte[1024];
		packet = new DatagramPacket(buffer,buffer.length);
	}
	
        @Override
	public void run() {
		try{
                    socket.receive(packet);
                    String in = new String(packet.getData());
                    System.out.println(packet.getAddress() +": " + in);
		}
		catch(IOException error) {
                    System.out.println("Cannot get packet");
		}
	}
}
