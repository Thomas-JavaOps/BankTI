package components;

public class Debit extends Flow{
	
	public Debit(){
		super();
	}
	
	public Debit (String id, int tNumAccount, double amount, String comment){
		super(id, tNumAccount, amount, comment);
	}

}
