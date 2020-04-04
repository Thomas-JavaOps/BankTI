package components;

public class Transfert extends Flow {
	
	private int fNumAccount; 
	
	public Transfert(){
		super();
	}
	
	public Transfert (String id, int fNumAccount, int tNumAccount, double amount, String comment){
		super(id, tNumAccount, amount, comment);
		this.fNumAccount = fNumAccount;
	}
	
	public int getFromNumAccount() {
		return this.fNumAccount;
	}
	
	public void setFromNumAccount(int fNumAccount) {
		this.fNumAccount = fNumAccount;
	}

}
