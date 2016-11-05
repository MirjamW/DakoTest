import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


// Server und Client so noch recht fehleranfällig

public class UDPServer {

	public static void main(String...strings) throws Exception{
		DatagramSocket serverSocket = new DatagramSocket(2109);
		
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		while(true){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // TODO missing part
					
			// kommentare einfügen --> wann passiert was?
			
					serverSocket.receive(receivePacket);
			
					String sentence = new String (receivePacket.getData());
							
					InetAddress IPAdress = receivePacket.getAddress();
					
					String capitalizedSentence = sentence.toUpperCase();
					
					int port = receivePacket.getPort();
					
					sendData = capitalizedSentence.getBytes();
					
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAdress, port); 
							
					
					serverSocket.send(sendPacket);
							
					
					
		}
		
	}
	
}
