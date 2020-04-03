//1.2.2 Creation of CurrentAccount & SavingsAccount class
package components;

public class SavingsAccount extends Account {
	
	public SavingsAccount(){
		super();
	}

	public SavingsAccount(String label, Client client){
		super(label,client);
	}
	
}
