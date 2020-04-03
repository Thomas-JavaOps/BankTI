//1.1.2 Creation of the main class for test
package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import components.Account;
import components.Client;
import components.CurrentAccount;
import components.SavingsAccount;


public class Main {
	
	public static void main (String[] args) {
		
		List <Client> clients = new ArrayList <Client> ();
		clients.add(new Client("Thomas", "ISOARDO"));
		clients.add(new Client("Julien", "POTIER"));
		clients.add(new Client("Jean-Louis", "Aubert"));

		
		List <Account> accounts = addAccount(clients);
		displayAccount(accounts);
		
		
	}
	
	//Méthode pour créer un tableau contenant des comptes
	public static List <Account> addAccount(List <Client> list){
		
		Scanner slabel1 = new Scanner(System.in);
		Scanner slabel2 = new Scanner(System.in);
		List <Account> accountList = new ArrayList <Account>();
		
		for (int i = 0 ; i < list.size(); i++) {
			
			System.out.println("Veuillez entrer un libélé pour le compte courant :");
			String label1 = slabel1.nextLine();
			accountList.add(new CurrentAccount(label1, list.get(i)));
			
			System.out.println("Veuillez entrer un libélé pour le compte épargne :");
			String label2 = slabel2.nextLine();
			accountList.add(new SavingsAccount(label2, list.get(i)));
		}
		return accountList;
	}
	
	//Méthode pour lire notre tableau de comptes avec un stream
		public static void displayAccount(List <Account> list) 
		{
			Stream<Account> sc = list.stream();
			sc.forEach(System.out::println);		
		}
	
	
	//Méthode pour créer un taleau contenant n clients
	@SuppressWarnings("resource")
	public static List <Client> addClient(List<Client> list, int number) {
		
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
	
	//Méthode pour lire notre tableau de clients avec un stream
	public static void displayClient(List <Client> list) 
	{
		Stream<Client> sc = list.stream();
		sc.forEach(System.out::println);		
	}
	
}
