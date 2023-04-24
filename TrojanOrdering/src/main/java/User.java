

public class User {
	private int UserID;
	private double balance;
	
	public User(int UserID, double balance) {
		this.UserID = UserID;
		this.balance = balance;
	}
	
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
