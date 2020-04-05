//Exo 2 -> JSON et XML
package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

		/*List <Client> clients = new ArrayList <Client> ();
		clients.add(new Client("Thomas", "ISOARDO"));
		clients.add(new Client("Julien", "POTIER"));
		clients.add(new Client("Jean-Louis", "Aubert"));*/
		List <Client> clients = parseXML();
		displayClient(clients);
		List <Account> accounts = addAccount(clients);
		Hashtable <Integer, Account> ht = createHashtable(accounts);
		List <Flow> flow = readJSON();
		//List <Flow> flow = createFlowArray(ht);
		updateBalance(flow, ht);
		displayHashtable(ht);	
		
}		
	
	  public static List <Client> parseXML() {
		     
		      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		      List <Client> list = new ArrayList <Client>(); 
		       Path pathXML = Paths.get("client.xml");
		       
		      try (InputStream is = Files.newInputStream(pathXML)) {
		         
		         DocumentBuilder builder = factory.newDocumentBuilder();
		         factory.setIgnoringElementContentWhitespace(true);

		         Document xml = builder.parse(is);
		         
		         NodeList names = xml.getElementsByTagName("name");
		         NodeList firstNames = xml.getElementsByTagName("firstName");
		         
		         for (int i=0; i<names.getLength();i++)
		         {
		        	String name = names.item(i).getTextContent();
		        	String firstName = firstNames.item(i).getTextContent();
		        	Client client = new Client(name, firstName);
		        	list.add(client);
		         }

		      } catch (ParserConfigurationException e) {
		         e.printStackTrace();
		      } catch (SAXException e) {
		         e.printStackTrace();
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		      
		      return list;
	}
	
/////////////////////////////////////////////////JSON////////////////////////////////////////////////////	
///////////////////////////// Méthode pour remplir une Liste<Flow> à partir d'un JSON//////////////////////////////
	@SuppressWarnings("unchecked")
	public  static List <Flow> readJSON() {
		
		JSONParser jsonParser = new JSONParser();
		List<Flow> list = new ArrayList<Flow>();
		Path pathJSON = Paths.get("flow.json");
		
		try(InputStream is = Files.newInputStream(pathJSON)) {
			
			Object obj = jsonParser.parse(new InputStreamReader(is, "UTF-8"));
			JSONArray flowArray = (JSONArray) obj;
		    
            //Iterate over employee array
            flowArray.forEach(emp -> parseFlow(list,(JSONObject) emp));
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		return list;
}	
	
//Méthode pour incrémenter la liste pour chaque JSONObject
private static void parseFlow(List <Flow> list, JSONObject flow)
	    {
			Flow fl;
			
			JSONObject flowObj = (JSONObject) flow.get("Flow");
	        
	        String id = (String) flowObj.get("id");

	        Long ltNumAccount = (long) flowObj.get("tNumAccount");   
	        Integer tNumAccount = ltNumAccount.intValue(); 
	         
	        Double amount = (Double) flowObj.get("amount");  

	        String comment = (String) flowObj.get("comment");    
	        
	        if (id.indexOf("credit") != -1){
	        	fl = new Credit(id, tNumAccount, amount, comment);
				list.add(fl);
	        } else if (id.indexOf("debit") !=-1) {
	        	fl = new Debit(id, tNumAccount, amount, comment);
				list.add(fl);	
	        } else if (id.indexOf("transfert") !=-1) {
	        	Long lfNumAccount = (long) flowObj.get("tNumAccount");   
		        Integer fNumAccount = lfNumAccount.intValue(); 
	        	fl = new Transfert (id, fNumAccount, tNumAccount, amount, comment);
	        	list.add(fl);
	        }
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
		Optional <Long> opt = Optional.of(value);
			
		if (opt != null)
		{
			if (value != 0)
			{
				System.out.println("Il existe au moins un compte avec une balance négative!");
			}
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
