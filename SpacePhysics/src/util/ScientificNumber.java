package util;

public class ScientificNumber {
	
	private float numberValue;
	private int tensPower;
	
	public ScientificNumber(float value, int power) {
		numberValue = value;
		tensPower = power;
	}
	
	public ScientificNumber multiply(ScientificNumber number) {
		int newPower = power() + number.power();
		float newPrefix = prefix() * number.prefix();
		return new ScientificNumber(newPrefix, newPower);
	}
	
	public ScientificNumber devideBy(ScientificNumber number) {
		int newPower = power() - number.power();
		float newPrefix = prefix() / number.prefix();
		return new ScientificNumber(newPrefix, newPower);
	}
	
	public ScientificNumber add(ScientificNumber number) {
		return null;
	}
	
	public ScientificNumber subtract(ScientificNumber number) {
		return null;
	}
	
	public float prefix() {
		return numberValue;
	}
	
	public int power() {
		return tensPower;
	}
}