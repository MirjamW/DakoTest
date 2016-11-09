package Timeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

//This code is only for demonstration purposes, please use for your own implementations a proper coding style
//as you learned in your Software Development class

public class UDPClientTimeout {
	public static void main(String... strings) {
		System.out.println("Define variables.");
		DatagramSocket clientSocket = null;
		DatagramPacket sendPacket;
		DatagramPacket receivePacket;
		BufferedReader inFromUser;
		InetAddress IPAddress = null;
		// a 1024-byte-array is a bit of an overkill for sending a single line
		// adjust the size depending on the data you want to send
		byte[] sendData = new byte[1024];
		// here we know we only want to send 3 bytes of ASCII-Code ("ACK")
		// therefore we only need a 3-byte-array
		byte[] receiveData = new byte[3];
		String sentence = "";
		String returnString;
		boolean gotACK = false;

		inFromUser = new BufferedReader(new InputStreamReader(System.in));

		// Initialize the DatagramSocket, get the IPAddress and read the line
		// for sending
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("localhost");
			System.out.println("Please enter a sentence.");
			sentence = inFromUser.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Prepare both packets for sending and receiving.");
		sendData = sentence.getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 2109);
		receivePacket = new DatagramPacket(receiveData, receiveData.length);

		// set the socket-timeout to 5 seconds
		try {
			clientSocket.setSoTimeout(5000);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		// Send the packet and wait for an ACK, if an ACK is not received
		// retransmit the packet
		while (!gotACK) {
			try {
				clientSocket.send(sendPacket);
				System.out.println("Packet sent, waiting for ACK.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {

				clientSocket.receive(receivePacket);
				gotACK = true;
			} catch (IOException e) {
				System.err.println("!!!receive-method had a timeout!!! \nRESTART TRANSMISSION");
			}
		}

		returnString = new String(receivePacket.getData());
		System.out.println("FROM SERVER: " + returnString);

		// always close everything you opened
		// if you do not close the socket java will show you a "recource leak"
		// warning
		clientSocket.close();
	}
}
