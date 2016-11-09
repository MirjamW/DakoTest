package Timeout;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//This code is only for demonstration purposes, please use for your own implementations a proper coding style
//as you learned in your Software Development class

public class UDPServerTimeout {
	public static void main(String... strings) throws Exception {

		System.out.println("Define Variables.");
		DatagramSocket serverSocket;
		DatagramPacket receivePacket1;
		DatagramPacket receivePacket2;
		DatagramPacket sendPacket;
		InetAddress IPAddress;
		String sentence1;
		String sentence2;
		String ack;
		int port;
		// a 1024-byte-array is a bit of an overkill for sending a single line
		// adjust the size depending on the data you want to send
		byte[] receiveData1 = new byte[1024];
		byte[] receiveData2 = new byte[1024];
		// here we know we only want to send 3 bytes of ASCII-Code ("ACK")
		// therefore we only need a 3-byte-array
		byte[] sendData = new byte[3];

		System.out.println("Initialize socket and packets.");
		serverSocket = new DatagramSocket(2109);
		receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
		receivePacket2 = new DatagramPacket(receiveData2, receiveData2.length);

		System.out.println("Waiting for packet 1.");
		serverSocket.receive(receivePacket1);
		sentence1 = new String(receivePacket1.getData());
		System.out.println("Content of receivePaket1: " + sentence1);

		System.out.println("Do not send an ACK-packet but wait for the retransmission.");
		serverSocket.receive(receivePacket2);
		sentence2 = new String(receivePacket2.getData());
		System.out.println("Content of receivePacket2: " + sentence2);

		// To send the ACK-Packet we have to extract the IP-Address and Port to
		// know where to send the ACK-Packet
		IPAddress = receivePacket2.getAddress();
		port = receivePacket2.getPort();
		System.out.println("IP: " + IPAddress + "    Port: " + port);

		System.out.println("Create ACK-Packet and send it to the Client.");
		ack = "ACK";
		sendData = ack.getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		serverSocket.send(sendPacket);

		// always close everything you opened
		// if you do not close the socket java will show you a "recource leak"
		// warning
		serverSocket.close();
	}
}
