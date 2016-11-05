import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

	public static void main(String...strings) throws Exception{
		
		System.out.println("Bitte eingabe t�tigen.");
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		DatagramSocket clientSocket = new DatagramSocket();
		// hier wird kein port �bergeben -> jvm sucht sich hier einen aus
		// port �bergeben ist nur auf der Serverseite wichtig
		
		InetAddress IPAddress = InetAddress.getByName("localhost");
		//127.0.0.1 -> auf die IP-Adresse wird das geschickt    (= "loopback interface")
		//Wenn alles auf einem pc ist kommt alles �ber diese adresse rein und geht auch raus
		
		
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		String sentence = inFromUser.readLine();
		
		sendData = sentence.getBytes();
		
		// Erst mal fehlerfreies Protokoll schreiben -> erst wenn das alles funktioniert mit Fehlerbehandlung 
		// anfangen (verschiedene Szenarien daf�r ausdenken)
		
		System.out.println("Sende Daten.");
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 2109);
		
		clientSocket.send(sendPacket);
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		
		clientSocket.receive(receivePacket);
		
		System.out.println("Empfange mod. Daten.");
		
		String modifiedSentence = new String(receivePacket.getData());
		
		System.out.println("FROM SERVER. " + modifiedSentence);
		
		clientSocket.close();
		// rein theoretisch schlie�t die JVM den selbst.. aber man sollte sich selbst darum k�mmern dass 
		// sockets auch immer wieder geschlossen werden

	}

}
