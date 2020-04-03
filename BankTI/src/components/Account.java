package components;

abstract class Account {
	
//////////////////////Variables////////////////////////	
	protected String label;
	protected double balance = 0.0;
	protected int numAccount; 
	
//////////////////////Constructeur/////////////////////	
	protected Account(String label, Client client) {
			this.label = label;
			numAccount = client.getNumClient();
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
	
	public void setBalance(Double balance) {
		this.balance= balance;	
	}
		
	public void setNumAccount(int numAccount) {
		this.numAccount = numAccount;	
	}
	
}
