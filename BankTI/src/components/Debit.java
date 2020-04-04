package components;

public class Debit extends Flow{
	
	public Debit(){
		super();
	}
	
	public Debit (String id, int tNumAccount, double amount, Boolean effect, String comment){
		super(id, tNumAccount, amount, effect, comment);
	}

}
