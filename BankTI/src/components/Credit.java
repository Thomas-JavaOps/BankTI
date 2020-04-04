package components;

public class Credit extends Flow {
	
	public Credit(){
		super();
	}
	
	public Credit (String id, int tNumAccount, double amount, Boolean effect, String comment){
		super(id, tNumAccount, amount, effect, comment);
	}

}
