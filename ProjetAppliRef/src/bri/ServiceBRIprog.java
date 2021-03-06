package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import metaServices.ServiceArreterDemarrer;
import services.ServiceMettreAJourService;
import services.ServiceNouveauService;
import services.ServiceNouvelleUrl;
import utilisateur.Programmeur;

public class ServiceBRIprog implements Runnable {
	private Programmeur programmeur;
	private Socket client;
	
	public ServiceBRIprog(Socket socket, Programmeur programmeur) {
		client = socket;
		this.programmeur = programmeur;
	}
	

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			 in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			 out = new PrintWriter (client.getOutputStream ( ), true);
			
			String line = "";
			out.println("Que souhaitez vous faire ?##1 : Fournir un nouveau service##"
					+ "2 : Mettre-e-jour un service##"
					+ "3 : Declarer un changement d'adresse de votre serveur ftp##"
					+ "4 : Demarrer / Arreter un service##"
					+ "5 : Quitter l'application##"
					+ "Saisissez le numero de l'option voulue");
			
			Integer choix = null;
			try {
				choix = Integer.parseInt(in.readLine());
				System.out.println("choix = " + choix);
				switch (choix) {
				case 1:
					new Thread(new ServiceNouveauService(client, programmeur)).start();
					break;
				case 2:
					new Thread(new ServiceMettreAJourService(client, programmeur)).start();
					break;
				case 3:
					new Thread(new ServiceNouvelleUrl(client, programmeur)).start();
					break;
				case 4:
					new Thread(new ServiceArreterDemarrer(client)).start();
					break;
				case 5:
					break;
				}
			}
			catch (NumberFormatException e1) {
				e1.printStackTrace();
				out.println("Erreur de saisie.## Veuillez saisir un chiffre parmi ceux proposes ");
			}
			
			
			System.out.println(line);

		}
		catch (IOException e) {
			e.printStackTrace();
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out.close();
		}
		finally {
		}


	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
