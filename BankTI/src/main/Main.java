//1.3.1 Adaptation of the table of accounts
package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
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
		for (int i=0; i<6; i++)
		accounts.get(i).setBalance((double) 6-i);
		
		Hashtable <String, Account> ht = createHashtable(accounts);
		displayHashtable(ht);

		
	}
	//Méthod pour lire le Hashtable trié par valeur de balance
	public static void displayHashtable(Hashtable <String, Account> ht) {
				
		ht.entrySet().stream()
		.sorted(Map.Entry.comparingByValue(Comparator.comparingDouble(Account::getBalance)))
		.forEach(System.out::println);

	}
	
	
	//Méthode pour ajouter les comptes dans une Hashtable
	public static Hashtable <String, Account> createHashtable(List<Account> list){
		
		Hashtable <String, Account> ht = new Hashtable <String, Account> ();
		
		for (int i =0; i<list.size(); i++)
			{
				ht.put(list.get(i).getLabel(), list.get(i));
			}		
		return ht;
	}
	
	//Méthode pour créer un tableau contenant des comptes
	public static List <Account> addAccount(List <Client> list){
		

		List <Account> accountList = new ArrayList <Account>();
		
		for (int i = 0 ; i < list.size(); i++) {

			accountList.add(new CurrentAccount("CCourant"+list.get(i).getNumClient(), list.get(i)));
			accountList.add(new SavingsAccount("CEpargne"+list.get(i).getNumClient(), list.get(i)));
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
