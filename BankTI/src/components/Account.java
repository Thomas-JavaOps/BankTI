//1.2.1 Creation of Account class
package components;

public abstract class Account {
	
//////////////////////Variables////////////////////////	
	protected String label ="Rien";
	protected double balance = 0.0;
	protected static int num = 0;
	protected int numAccount = 0; 
	
//////////////////////Constructeur/////////////////////	
	protected Account() {
		
	}
	protected Account(String label, Client client) {
			this.label = label;
			++num;
			this.numAccount = num;
		}
	
/////////////////////Les Getters////////////////////////	
	public String getLabel() {
		return this.label;	
	}
	
	public double getBalance() {
		return this.balance;	
	}
		
	public int getNumAccount() {
		return this.numAccount;	
	}

/////////////////////Les Setters///////////////////////
	public void SetLabel(String label) {
		this.label = label;	
	}
	
	public void setBalance(Flow flow) {
		this.balance += flow.getAmount() ;	
	}
		
	public void setNumAccount(int numAccount) {
		this.numAccount = numAccount;	
	}
	
/////////////////////Méthode////////////////////
	public String toString() {
		return "Le compte n°" + this.numAccount + " : \"" + this.label + "\" est accrédité de " + this.balance + "€" ;
	}
	
}
