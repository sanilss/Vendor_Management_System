package model;

public class Coin {
	private int value;
	private int quantity;

	public Coin(int value, int quantity) {
		this.value = value;
		this.quantity = quantity;
	}

	public int getValue() {
		return value;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
