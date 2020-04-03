//1.1.2 Creation of the main class for test
package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import components.Client;


public class Main {
	
	public static void main (String[] args) {
		
		List <Client> clients = new ArrayList <Client> ();
		clients.add(new Client("Thomas", "ISOARDO"));
		addClient(clients, 2);
		displayClient(clients);
		addClient(clients, 1);
		displayClient(clients);
		
	}
	//Méthode pour créer un taleau contenant n clients
	public static List <Client> addClient( List<Client> list, int number) {
		
		Scanner snum = new Scanner(System.in);
		
		//On prend en charge le cas où l'utilisateur entre un nombre négatif
		while (number <0) 
		{
			
			try {
				System.out.println("Veuillez entrer un nombre entier positif :");
				number = snum.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("Procédure Annulé");
				number = 0;
			}
			
		}
		
		Scanner sname = new Scanner(System.in);
		Scanner sfname = new Scanner(System.in);
		int i = 1;
		
		while (i<=number)
		{
			System.out.println("Veuillez écrire le prénom du client :");
			String name = sname.nextLine();
			System.out.println("Veuillez maintenant écrire son nom :");
			String fname = sfname.nextLine();
			Client client = new Client(name, fname);
			list.add(client);
			i++;
		}
		
		return list;
	}
	
	//Méthode pour lire notre tableau avec un stream
	public static void displayClient(List <Client> list) 
	{
		Stream<Client> sc = list.stream();
		sc.forEach(System.out::println);		
	}
	
}
