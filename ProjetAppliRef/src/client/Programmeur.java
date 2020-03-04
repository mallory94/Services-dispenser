package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Programmeur {
	private final static int PORT = 2700;
	private final static String HOST = "localhost"; 

	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket(HOST, PORT);
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Connect� au serveur " + socket.getInetAddress() + ":"+ socket.getPort());

			String line;
			System.out.println(sin.readLine()); //on �crit ce que le serveur veut
			line = clavier.readLine(); //on lit la r�ponse au clavier
			// envoie au serveur
			sout.println(line); 
			// lit la r�ponse provenant du serveur
			line = sin.readLine();
			// Ecrit la ligne envoyee par le serveur
			System.out.println(line);
			
			line = clavier.readLine();
			sout.println(line);
			
			System.out.println(sin.readLine());
			//fin du test de connexion
			socket.close();
		}
		catch (IOException e) { System.err.println(e); }
		// Refermer dans tous les cas la socket
		try { if (socket != null) socket.close(); } 
		catch (IOException e2) { ; }		
	}
}