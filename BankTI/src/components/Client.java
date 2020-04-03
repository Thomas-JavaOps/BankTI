package components;

public class Client {

///////////////////////Initialisation des variables///////////////////////
	private String name = "Invalide";
	private String firstName = "Invalide";
	private int num = 0;
	private static int numClient;

///////////////////////////Les constructeurs///////////////////////////////	
	public Client() {
	}

	public Client(String name, String firstName) {
		this.name = name;
		this.firstName = firstName;
		++numClient;
		this.num = numClient;
		
	}

////////////////////////On définit les Getters////////////////////
	public String getName() {
		return this.name;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public int getNumClient() {
		return this.num;
	}

//On définit les Setters
	public void setName(String name) {
		this.name = name.trim();
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}
	
	public void setNum(int num) {
		this.num = num;
	}

///////////////////////////////////Méthodes////////////////////

	public String toString() {
		return "Le client n°" + this.num + " s'appelle " + this.firstName.toUpperCase() + " " + this.name + ".";
	}

}
