//1.3.2 Creation de la classe Flow 
package components;
import java.time.LocalDateTime;

public abstract class Flow {
	
	/////////////////////////////////Attributs//////////////////////////
	private String comment = "Aucun";
	private String id ="Inconnu";
	private double amount =0.0;
	private int tNumAccount =0;
	private Boolean effect= true;
	private LocalDateTime flowDate = LocalDateTime.now();
	
	////////////////////////////////Constructeurs////////////////////
	protected Flow () {
	}
	
	protected Flow(String id, int tNumAccount, double amount, Boolean effect, String comment)
	{
		this.id = id;
		this.tNumAccount = tNumAccount;
		this.amount = amount;
		this.effect = effect;
		this.flowDate = LocalDateTime.now();
		this.comment = comment;
	}
	
	///////////////////////////////Getters////////////////////////////////////
	public String getId(){
		return this.id;
	}
	
	public int getTargetNumAccount(){
		return this.tNumAccount;
	}
	
	public double getAmount() {
		return this.amount;
	}
	
	public Boolean getEffect() {
		return this.effect;
	}
	
	public LocalDateTime getFlowDate() {
		return this.flowDate;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	////////////////////////////////////////////Setters//////////////////////
	public void setId(String id){
		this.id = id;
	}
	
	public void setTargetNumAccount(int tNumAccount){
		this.tNumAccount = tNumAccount;
	}
	
	public void setAmount(double amount) {
		this.amount=amount;
	}
	
	public void setEffect(Boolean effect) {
		this.effect = effect;
	}
	
	public void getFlowDate(LocalDateTime flowDate) {
		this.flowDate = flowDate;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

}
