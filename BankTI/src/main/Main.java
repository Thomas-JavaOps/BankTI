//1.3.5 Creation of the flowArray
package main;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import components.Account;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.SavingsAccount;
import components.Transfert;


public class Main {
///////////////////////////////////////Méthode Main ///////////////////////////////////////////////////////	
	public static void main (String[] args) {

		List <Client> clients = new ArrayList <Client> ();
		clients.add(new Client("Thomas", "ISOARDO"));
		clients.add(new Client("Julien", "POTIER"));
		clients.add(new Client("Jean-Louis", "Aubert"));

		List <Account> accounts = addAccount(clients);
		Hashtable <Integer, Account> ht = createHashtable(accounts);
		List <Flow> flow = createFlowArray(ht);
		
		System.out.println(accounts.get(0).getBalance());
		System.out.println(accounts.get(1).getBalance());
		
		updateBalance(flow, ht);
		displayHashtable(ht);
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Méthode pour mettre à jour les balances
	public static void updateBalance(List <Flow> list, Hashtable <Integer, Account> ht){
		
		Transfert transfert ;
		
		for (int i=0; i<list.size() ; i++)
		{
			int tNumAccount = list.get(i).getTargetNumAccount();
			int fNumAccount = 0;

			if (list.get(i).getClass().getName().contentEquals("components.Transfert"))
			{
				transfert = (Transfert)(list.get(i));
				fNumAccount = transfert.getFromNumAccount();
			}
				
			for (int j=1; j<=ht.size() ; j++)
			{
					if (ht.get(j).getNumAccount()== tNumAccount || ht.get(j).getNumAccount() == fNumAccount)
					{
							ht.get(j).setBalance(list.get(i));
					}			
			}
		}
		
		//On vérifie que toutes les balances sont positives 	
		long value = ht.entrySet().stream()
				.filter(x -> (x.getValue().getBalance())<0)
				.count();
			
		if (value != 0)
		{
			System.out.println("Il existe au moins un compte avec une balance négative!");
		}
	
	}
	
	//Méthode pour créer le flow d'Array voulu 
	public static List <Flow> createFlowArray(Hashtable<Integer,Account> ht){

		List <Flow> list = new ArrayList <Flow>();
		//Première opération
		Debit deb = new Debit("01", 1, 50.0, "Paiement Baby-Sitting");
		list.add(deb);
		
		//Deuxième opération
		for (int i =1; i<=ht.size();i++) {
			if ( ht.get(i).getClass().getName().contentEquals("components.CurrentAccount"))
			{
				Credit cred1 = new Credit("02", i, 100.50, "Courses Alimentaires");
				list.add(cred1);
			} 
		}
		
		//Troisième opération
		for (int i =1; i<=ht.size();i++) {
			if ( ht.get(i).getClass().getName().contentEquals("components.SavingsAccount"))
			{
				Credit cred2 = new Credit("03", i, 1500.0, "Achat Scooter");
				list.add(cred2);
			}
		}

		//Dernière opération
		Transfert trft = new Transfert("04", 1, 2, 50.0, "Remboursement Cadeau");
		list.add(trft);
		
		//On recule de deux jours la date de l'opération
		for (int i =0; i<list.size();i++)
			{
				list.get(i).setFlowDate(list.get(i).getFlowDate().plus(2,ChronoUnit.DAYS));
			}
		
		return list;
	}
	
	
	
	//Méthode pour lire le Hashtable trié par valeur de balance
	public static void displayHashtable(Hashtable <Integer, Account> ht) {
				
		ht.entrySet().stream()
		.sorted(Map.Entry.comparingByValue(Comparator.comparingDouble(Account::getBalance)))
		.forEach(System.out::println);

	}
	
	
	
	
	//Méthode pour ajouter les comptes dans une Hashtable
	public static Hashtable <Integer, Account> createHashtable(List<Account> list){
		
		Hashtable <Integer, Account> ht = new Hashtable <Integer, Account> ();
		
		for (int i =0; i<list.size(); i++)
			{
				ht.put(list.get(i).getNumAccount(), list.get(i));
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
